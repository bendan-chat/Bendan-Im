package com.obeast.common.three.utils;

import com.obeast.common.three.enumration.ShardFileStatusCode;
import com.obeast.core.exception.BendanException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wxl
 * Date 2022/12/26 21:00
 * @version 1.0
 * Description: 文件分片工具类
 */
@Slf4j
public class FileUploadUtil {

    private FileUploadUtil() {

    }


    /**
     * 大文件分片成 List<InputStream>
     *
     * @param file      文件路径；
     * @param splitSize = 5 * 1024 * 1024;//单片文件大小,5M
     * @return List<MultipartFile> 分片集合
     */
    @SneakyThrows
    public static List<InputStream> splitFileInputStreams(File file, long splitSize) {
        if (splitSize < (5 * 1024 * 1024)) {
            throw new Exception(ShardFileStatusCode.SHARD_MUST_MORE_THAN_5M.getMessage());
        }
        List<InputStream> inputStreams = new ArrayList<>();
        InputStream bis = null;//输入流用于读取文件数据
        OutputStream bos = null;//输出流用于输出分片文件至磁盘
        try {
            bis = new BufferedInputStream(new FileInputStream(file));
            long writeByte = 0;//已读取的字节数
            int len = 0;
            byte[] bt = new byte[5 * 1024 * 1024];
            while (-1 != (len = bis.read(bt))) {
                if (writeByte % splitSize == 0) {
                    bos = new ByteArrayOutputStream();
                }
                writeByte += len;
                bos.write(bt, 0, len);
                if (writeByte % splitSize == 0) {
                    InputStream inputStream = IOConvertUtils.oConvertI(bos);
                    inputStreams.add(inputStream);
                }
            }
            InputStream inputStream = IOConvertUtils.oConvertI(bos);
            inputStreams.add(inputStream);
            log.info("{} 文件分片成功！开始准备分片上传", file.getName());
        } catch (Exception e) {
            log.error("文件分片失败！原因：{}", e.getMessage());
            e.printStackTrace();
        }
        return inputStreams;
    }


    /**
     * Multipart文件分片成List<InputStream>
     *
     * @param multipartFile      MultipartFile
     * @param splitSize = 5 * 1024 * 1024;//单片文件大小,5M
     * @return List<MultipartFile> 分片集合
     */
    @SneakyThrows
    public static List<InputStream> splitMultipartFileInputStreams(MultipartFile multipartFile, String filename, long splitSize) {
        if (splitSize < (5 * 1024 * 1024)) {
            throw new Exception(ShardFileStatusCode.SHARD_MUST_MORE_THAN_5M.getMessage());
        }

        List<InputStream> inputStreams = new ArrayList<>();
        InputStream bis = null;//输入流用于读取文件数据
        OutputStream bos = null;//输出流用于输出分片文件至磁盘
        try {
            bis = new BufferedInputStream(multipartFile.getInputStream());
            long writeByte = 0;//已读取的字节数
            int len = 0;
            byte[] bt = new byte[5 * 1024 * 1024];
            while (-1 != (len = bis.read(bt))) {
                if (writeByte % splitSize == 0) {
                    bos = new ByteArrayOutputStream();
                }
                writeByte += len;
                bos.write(bt, 0, len);
                if (writeByte % splitSize == 0) {
                    InputStream inputStream = IOConvertUtils.oConvertI(bos);
                    inputStreams.add(inputStream);
                }
            }
            InputStream inputStream = IOConvertUtils.oConvertI(bos);
            inputStreams.add(inputStream);
            log.info("{} 文件分片成功！", filename);
        } catch (Exception e) {
            log.error("文件分片失败！原因：{}", e.getMessage());
            e.printStackTrace();
        }
        return inputStreams;
    }


    /**
     * Description: inputStream分片成List<InputStream>
     * @author wxl
     * Date: 2022/9/21 16:22
     * @param is inputStream
     * @param splitSize = 5 ;//单片文件大小,5M
     * @param filename filename
     * @return java.util.List<java.io.InputStream>
     */
    public static List<InputStream> splitToInputStreams(InputStream is, String filename ,long splitSize) {
        long splitSizeAfter = splitSize * 1024 * 1024;
        if (splitSizeAfter < (5 * 1024 * 1024)) {
            throw new BendanException(ShardFileStatusCode.SHARD_MUST_MORE_THAN_5M.getMessage());
        }
        List<InputStream> inputStreams = new ArrayList<>();
        OutputStream os = null;//输出流用于输出分片文件至磁盘
        try {
            long writeByte = 0;//已读取的字节数
            int len = 0;
            byte[] bt = new byte[5 * 1024 * 1024];
            while (-1 != (len = is.read(bt))) {
                if (writeByte % splitSize == 0) {
                    os = new ByteArrayOutputStream();
                }
                writeByte += len;
                os.write(bt, 0, len);
                if (writeByte % splitSize == 0) {
                    InputStream inputStream = IOConvertUtils.oConvertI(os);
                    inputStreams.add(inputStream);
                }
            }
            //最后一片
            InputStream inputStream = IOConvertUtils.oConvertI(os);
            inputStreams.add(inputStream);

            log.info("{} 流分片成功！", filename);
        } catch (Exception e) {
            log.error("流分片失败！原因：{}", e.getMessage());
            e.printStackTrace();
        }
        return inputStreams;
    }

}
