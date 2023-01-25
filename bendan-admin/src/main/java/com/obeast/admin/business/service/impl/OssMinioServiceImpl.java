package com.obeast.admin.business.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.obeast.admin.business.dao.OssEntityDao;
import com.obeast.admin.business.service.OssMinioService;
import com.obeast.admin.business.service.SseEmitterService;
import com.obeast.business.entity.OssEntity;
import com.obeast.common.three.domain.FlyweightRes;
import com.obeast.common.three.domain.MergeShardArgs;
import com.obeast.common.three.domain.MinioResult;
import com.obeast.common.three.enumration.FileUploadConstant;
import com.obeast.common.three.enumration.ShardFileStatusCode;
import com.obeast.common.three.template.MinioOssTemplate;
import com.obeast.common.three.utils.FileUploadUtil;
import com.obeast.core.base.CommonResult;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;
import java.util.concurrent.*;


/**
 * @author obeast-dragon
 * Date 2022-09-21 15:19:09
 * @version 1.0
 * Description:
 */
@Service("ossService")
@RequiredArgsConstructor
public class OssMinioServiceImpl extends ServiceImpl<OssEntityDao, OssEntity> implements OssMinioService {

    private static final Logger log = LoggerFactory.getLogger(OssMinioServiceImpl.class);

    private final MinioOssTemplate minioTemplate;

    private final OssEntityDao ossEntityDao;

    private final SseEmitterService sseEmitterService;

    private final FlyweightRes res;

    private static ExecutorService executorService;

    /**
     * @return ExecutorService 线程池
     * description:因此IO密集型的任务，可大致设为： N(threads) = 2N(cpu) ->我的cpu是4核
     * @author wxl
     * date 2022/7/20 23:41
     **/
    private static ExecutorService createThreadPool(int sizePool) {
        return new ThreadPoolExecutor(
                sizePool,
                sizePool,
                0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1024));
    }


    /**
     * @param file     multipartFile
     * @param res      全局共享消息体
     * @param fileType 文件类型
     *                 description: 大文件上传大于5MB分片文件上传
     * @author wxl
     * date 2022/7/20 23:51
     **/
    @SneakyThrows
    private OssEntity uploadShard(MultipartFile file, FlyweightRes res, String fileType, Long userId) {
        executorService = createThreadPool(8);
        // 上传过程中出现异常，状态码设置为50000
        boolean stopStatus = true;
        if (file == null) {
            res.get(userId).put("status", ShardFileStatusCode.FAILURE);
            throw new Exception(ShardFileStatusCode.FAILURE.getMessage());
        }
        Long fileSize = file.getSize();
        String md5BucketName = userId + "-" + StrUtil.uuid();
        String fileName = file.getOriginalFilename();
        res.get(userId).put("md5BucketName", md5BucketName);
        //分片大小
        long shardSize = 5 * 1024 * 1024L;
        //开始分片
        List<InputStream> inputStreams = FileUploadUtil.splitMultipartFileInputStreams(file, fileName, shardSize);
        long shardCount = inputStreams.size(); //总片数
        //封装合并参数
        MergeShardArgs mergeShardArgs = new MergeShardArgs((int) shardCount, fileName, md5BucketName, fileType, fileSize);
        boolean fileExists = isFileExists(md5BucketName);
        boolean bucketExists = minioTemplate.bucketExists(md5BucketName);
        //当前文件不存在DB和minio  可以正常分片上传
        if (!fileExists && !bucketExists) {
            try {
                //创建临时桶
                minioTemplate.makeBucket(md5BucketName);
                uploadJob(shardCount, inputStreams, res, stopStatus, md5BucketName, fileName, userId);
                //开始合并
                OssEntity OssEntity = mergeShard(mergeShardArgs, userId, res);
                //上传完成清空当前用户数据
                res.get(userId).clear();
                log.debug("文件上传成功 {} ", file.getOriginalFilename());
                res.get(userId).put("status", ShardFileStatusCode.ALL_CHUNK_SUCCESS.getCode());
                return OssEntity;
            } catch (Exception e) {
                log.error("分片合并失败：{}", e.getMessage());
                throw new Exception("分片合并失败");
            }
        }

        /*
          如果文件存在;
           1、存在DB minio == null 上传完成秒传
           2、存在minio  DB == null
          先看临时桶在不在
           1、在;断点上传
           2、在;没合并
          */
        else if (fileExists && !bucketExists) {
            //1、存在DB minio == null
            LambdaQueryWrapper<OssEntity> wrapper = Wrappers.<OssEntity>lambdaQuery()
                    .eq(OssEntity::getFileName, md5BucketName);
            ossEntityDao.delete(wrapper);
            throw new Exception("请重新上传文件");

        } else if (!fileExists) {
            //2、存在minio  DB == null
//         *  1、在;断点上传
//         *  2、在;没合并
            List<String> objectNames = minioTemplate.listObjectNames(md5BucketName);
            OssEntity OssEntity;
            //临时桶在; 断点上传
            if (objectNames.size() == shardCount) {
                //设置百分比
                res.get(userId).put("uploadPercent", 100);
                log.debug("uploadPercent:{}", 100);
                //设置上传文件大小
                res.get(userId).put("uploadSize", fileSize);
                //没有合并: 合并秒传
                OssEntity = mergeShard(mergeShardArgs, userId, res);
                log.debug("uploadSize：{}", fileSize);
                log.debug("{} 秒传成功", fileName);
                //上传完成清空当前用户数据
                res.get(userId).clear();
            } else {
                //断点上传
                log.debug("开始断点上传>>>>>>");
                List<String> containStr = containList(objectNames, shardCount);
                log.debug("上传过的分片:" + containStr);
                CountDownLatch countDownLatch = new CountDownLatch(containStr.size());
                try {
                    log.debug("开始断点分片上传:" + fileName);
                    for (String s : containStr) {
                        stopStatus = (boolean) res.get(userId).get("stopStatus");
                        if (stopStatus) {
                            int c = Integer.parseInt(s);
                            executorService.execute(new BranchThread(inputStreams.get(c - 1), md5BucketName, c, res, countDownLatch, shardCount, stopStatus, userId, minioTemplate, sseEmitterService));
                        } else {
                            executorService.shutdown();
                            break;
                        }
                    }
                } catch (Exception e) {
                    log.error("断点上传出现异常{}", e.getMessage());
                    throw new Exception("断点上传出现异常");
                } finally {
                    //关闭线程池
                    executorService.shutdown();
                }
                countDownLatch.await();
                log.debug("所有分片上传完成");
                res.get(userId).put("status", ShardFileStatusCode.ALL_CHUNK_UPLOAD_SUCCESS.getCode());

                OssEntity = mergeShard(mergeShardArgs, userId, res);
                log.debug("文件上传成功：{} ", fileName);
                //上传完成清空当前用户数据
                res.get(userId).clear();
            }
            res.get(userId).put("status", ShardFileStatusCode.ALL_CHUNK_MERGE_SUCCESS.getCode());
            return OssEntity;

        } else {
            //出现异常
            log.error("出现异常: {}", ShardFileStatusCode.FOUND.getMessage());
            throw new Exception("文件上传出现异常");
        }
    }

    @Transactional
    @Override
    public OssEntity mergeShard(MergeShardArgs mergeShardArgs, Long userId, FlyweightRes res) {
        Integer shardCount = mergeShardArgs.getShardCount();
        String fileName = (String) res.get(userId).get("fileName");
        String md5 = mergeShardArgs.getMd5();
        try {
            List<String> objectNameList = minioTemplate.listObjectNames(md5);
            //查询的服务器的分片和传入的分片不同
            if (shardCount != objectNameList.size()) {
                // 失败
                log.error("服务器的分片{}和传入的分片不同{}", shardCount, objectNameList.size());
                throw new Exception("服务器的分片和传入的分片不同");
            } else {
                // 开始合并请求
                String targetBucketName = minioTemplate.getBucketName();
                //拼接合并之后的文件名称
                String objectName = userId + "-" + fileName;
                //合并
                minioTemplate.composeObject(md5, targetBucketName, objectName);

                log.debug("桶：{} 中的分片文件，已经在桶：{},文件 {} 合并成功", md5, targetBucketName, objectName);
                String url = minioTemplate.getPresignedObjectUrl(targetBucketName, objectName);
                // 合并成功之后删除对应的临时桶
                minioTemplate.removeBucket(md5, true);
                log.debug("删除桶 {} 成功", md5);
                // 存入DB中
                OssEntity ossEntity = new OssEntity();
                ossEntity.setFileName(md5);
                ossEntity.setUrl(url);
                ossEntity.setUserId(userId);
                ossEntity.setCreateTime(new Date());
                ossEntity.setUpdateTime(new Date());
                ossEntityDao.insert(ossEntity);
                log.debug("文件合并成{}并存入DB", ossEntity);
                return ossEntity;
            }
        } catch (Exception e) {
            // 失败
            log.error("合并失败:{}", e.getMessage());
        }
        return null;

    }


    @SneakyThrows
    @Override
    public boolean isFileExists(String md5) {
        if (ObjectUtils.isEmpty(md5)) {
            log.error("参数md5为空");
            throw new Exception("参数md5为空");
        }
        LambdaQueryWrapper<OssEntity> wrapper = Wrappers.<OssEntity>lambdaQuery()
                .eq(OssEntity::getFileName, md5);
        // 查询
        OssEntity OssEntity = ossEntityDao.selectOne(wrapper);
        /*
          文件不存在 false
          文件存在 true
          */
        return OssEntity != null;
    }


    /**
     * @param multipartFile file
     * @param userId        user id
     * @param voiceTimeSize 声音的长度
     * @return OssEntity
     * description: 小文件上传 0-5MB
     * @author wxl
     * date 2022/7/22 22:48
     **/
    private OssEntity uploadMiniFile(MultipartFile multipartFile, Long userId, FlyweightRes res, int voiceTimeSize) throws Exception {
        if (multipartFile == null) {
            throw new Exception(ShardFileStatusCode.FAILURE.getMessage());
        }
        if (userId == null) {
            throw new Exception(ShardFileStatusCode.FILE_IS_NULL.getMessage());
        }
        log.debug(multipartFile.getName());
        String fileName = (String) res.get(userId).get("fileName");
        String targetBucketName = minioTemplate.getBucketName();
        String targetName;
        if (voiceTimeSize <= 0) {
            targetName = userId + "-" + fileName;
        } else {
            targetName = userId + "-" + voiceTimeSize + "-" + fileName;
        }
        if (isFileExists(targetName)) {
            log.warn("File is exist already");
            throw new Exception("File is exist already");
        } else {
            MinioResult result = minioTemplate.putSimpleObject(multipartFile.getInputStream(), targetBucketName, targetName);
            log.debug("小文件上传成功");
            String url = minioTemplate.getPresignedObjectUrl(targetBucketName, result.getOriginalFileName());
            OssEntity ossEntity = new OssEntity();
            ossEntity.setFileName(result.getOriginalFileName());
            ossEntity.setUrl(url);
            ossEntity.setUserId(userId);
            ossEntity.setCreateTime(new Date());
            ossEntity.setUpdateTime(new Date());
            ossEntityDao.insert(ossEntity);
            log.debug("小文件插入DB");
            return ossEntity;
        }

    }


    @Override
    public CommonResult<OssEntity> uploadFile(MultipartFile file, Long userId) {
        try {
            if (file != null && userId != null) {
                String fileName = file.getOriginalFilename() != null
                        ? file.getOriginalFilename()
                        : file.getName();
                String filenameExtension = StringUtils.getFilenameExtension(fileName);

                Map<String, Object> map = res.computeIfAbsent(userId, k -> new HashMap<>());
                map.put("stopStatus", true);
                map.put("fileName", fileName);
                map.put("fileSize", file.getSize());
                OssEntity ossEntity;
                log.debug("文件大小为{}", file.getSize());
                if (file.getSize() < FileUploadConstant.BOUNDARY_VALUE) {
                    ossEntity = this.uploadMiniFile(file, userId, res, 0);
                } else {
                    ossEntity = this.uploadShard(file, res, filenameExtension, userId);

                }
                return CommonResult.success(ossEntity, ShardFileStatusCode.ALL_CHUNK_SUCCESS.getMessage());

            } else {
                return CommonResult.error(ShardFileStatusCode.FILE_IS_NULL.getMessage() + "或者" + ShardFileStatusCode.UUID_IS_NULL);
            }
        } catch (Exception e) {
            return CommonResult.error(e.getMessage());
        }
    }

    /**
     * @param oleName oleName
     * @return String
     * description: 0-6位取文件名
     * @author wxl
     * date 2022/7/22 22:50
     **/
    @Override
    public String rename(String oleName) {
        return oleName.substring(0, 6);
    }

    /**
     * Description: 查询上传过的分片
     *
     * @param objNames   object names
     * @param shardCount shardCount
     * @return java.util.List<java.lang.String>
     * @author wxl
     * Date: 2022/9/21 15:46
     */
    private List<String> containList(List<String> objNames, long shardCount) {
        List<String> containList = new ArrayList<>();
        for (int i = 1; i <= shardCount; i++) {
            String str = String.valueOf(i);
            if (!objNames.contains(str)) {
                containList.add(str);
            }
        }
        return containList;
    }


    /**
     * @param shardCount    number of shards
     * @param inputStreams  files stream
     * @param res           全局共享消息体
     * @param stopStatus    暂停状态
     * @param md5BucketName md5 bucket name
     * @param fileName      file name
     *                      description:分片任务
     * @author wxl
     * date 2022/7/20 23:54
     **/
    private void uploadJob(long shardCount, List<InputStream> inputStreams, FlyweightRes res, Boolean stopStatus, String md5BucketName, String fileName, Long userId) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch((int) shardCount);
        if (shardCount > 10000) {
            throw new RuntimeException("Total parts count should not exceed 10000");
        }
        log.debug("文件分得总片数：" + shardCount);
        try {
            log.debug("开始分片上传:" + fileName);
            for (int i = 0; i < shardCount; i++) {

                stopStatus = (Boolean) res.get(userId).get("stopStatus");
                if (stopStatus) {
                    executorService.execute(new BranchThread(inputStreams.get(i), md5BucketName, i + 1, res, countDownLatch, shardCount, stopStatus, userId, minioTemplate, sseEmitterService));
                } else {
                    executorService.shutdown();
                    break;
                }
            }
        } catch (Exception e) {
            log.error("线程上传出现异常:{}", e.getMessage());
        } finally {
            //关闭线程池
            executorService.shutdown();
        }
        log.debug(">>>>>>>>>>等待分片上传");
        countDownLatch.await();

        log.debug(">>>>>>>>>>所有分片上传完成");
        res.get(userId).put("status", ShardFileStatusCode.ALL_CHUNK_UPLOAD_SUCCESS.getCode());
    }

    /**
     * @author wxl
     * date 2022/7/20 23:57
     * description: 分片上传内部类
     **/
    private static class BranchThread implements Runnable {
        /**
         * 文件流
         */
        private final InputStream inputStream;

        /**
         * md5名
         */
        private final String md5BucketName;

        /**
         * 当前片数
         */
        private final Integer curIndex;

        /**
         * 返回给前端的res
         */
        private final FlyweightRes res;

        /**
         * 计数等待线程执行完成
         */
        private final CountDownLatch countDownLatch;

        /**
         * 总片数
         */
        private final long shardCount;

        /**
         * 暂停状态
         */
        private final boolean stopStatus;

        /**
         * 用户id
         */
        private final Long userId;


        /**
         * template
         */
        private final MinioOssTemplate minioOssTemplate;

        /**
         * sse发给前端的服务
         */
        private final SseEmitterService sseEmitterService;

        public BranchThread(InputStream inputStream, String md5BucketName, Integer curIndex, FlyweightRes res, CountDownLatch countDownLatch, long shardCount, boolean stopStatus, Long userId, MinioOssTemplate minioOssTemplate, SseEmitterService sseEmitterService) {
            this.inputStream = inputStream;
            this.md5BucketName = md5BucketName;
            this.curIndex = curIndex;
            this.res = res;
            this.countDownLatch = countDownLatch;
            this.shardCount = shardCount;
            this.stopStatus = stopStatus;
            this.userId = userId;
            this.minioOssTemplate = minioOssTemplate;
            this.sseEmitterService = sseEmitterService;
        }


        @SneakyThrows
        @Override
        public void run() {
            try {
                if (stopStatus) {
                    Long uploadPercent = ((curIndex * 100) / shardCount);
                    String curIndexName = String.valueOf(curIndex);
                    //设置百分比
                    res.get(userId).put("uploadPercent", uploadPercent);
                    log.debug("uploadPercent:{}", uploadPercent);
                    //设置上传文件大小
                    res.get(userId).put("uploadSize", inputStream.available());
                    log.debug("uploadSize：{}", inputStream.available());
                    sseEmitterService.sendResMapToOneClient(userId, res);
                    MinioResult minioResult = minioOssTemplate.putChunkObject(inputStream, md5BucketName, curIndexName);
                    log.debug("分片上传成功： {}", minioResult);
                } else {
                    executorService.shutdown();
                }

            } catch (Exception e) {
                log.error("线程上传分片异常：{}", e.getMessage());
            } finally {
                countDownLatch.countDown();
            }
        }
    }


}
