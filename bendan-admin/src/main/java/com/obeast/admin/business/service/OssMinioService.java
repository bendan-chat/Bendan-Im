package com.obeast.admin.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.obeast.business.entity.OssEntity;
import com.obeast.common.three.domain.FlyweightRes;
import com.obeast.common.three.domain.MergeShardArgs;
import com.obeast.core.base.CommonResult;
import org.springframework.web.multipart.MultipartFile;


/**
 * @author obeast-dragon
 * Date 2022-09-21 15:19:09
 * @version 1.0
 * Description: 
 */
public interface OssMinioService extends IService<OssEntity> {


    /**
     * 文件合并
     *
     * @param mergeShardArgs 合并参数
     */
    OssEntity mergeShard(MergeShardArgs mergeShardArgs, Long userId, FlyweightRes res);


    /**
     * 根据文件大小和文件的md5校验文件是否存在
     * 暂时使用Redis实现，后续需要存入数据库
     * 实现秒传接口
     *
     * @param md5 文件的md5
     * @return 操作是否成功
     */
    boolean isFileExists(String md5);


    /**
     * description: 0-6位取文件名
     * @author wxl
     * date 2022/7/22 22:50
     * @param oleName  oleName
     * @return String
     **/
    String rename(String oleName);


    /**
     * Description: 上传文件
     * @author wxl
     * Date: 2022/12/31 13:17
     * @param file  file
     * @param userId request
     * @return com.obeast.core.base.CommonResult<com.obeast.business.entity.OssEntity>
     */
    CommonResult<OssEntity> uploadFile(MultipartFile file, Long userId);
}

