package com.obeast.common.three.template;

import com.obeast.common.three.properties.ThirdPartyTencentProperties;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author wxl
 * Date 2023/1/4 11:24
 * @version 1.0
 * Description: 腾讯 oss模板
 */
@RequiredArgsConstructor
public class TencentOssTemplate {

    private final COSClient cosClient;

    private final ThirdPartyTencentProperties tencentProperties;


    /**
     * Description: 获取object url key
     *
     * @param userId user id
     * @param key    key
     * @return java.lang.String
     * @author wxl
     * Date: 2023/1/4 17:15
     */
    public String getObjectUrlKey(Long userId, String key) {
        return userId + "/" + key;
    }


    /**
     * Description: 获取object url
     * @author wxl
     * Date: 2023/1/5 9:10
     * @param userId userId
     * @param key  key
     * @return java.lang.String
     */
    public String getMergeObjectUrl(Long userId, String key) {
        return getObjectUrlPrefix() + getObjectUrlKey(userId, key);
    }

    /**
     * Description: 获取object url 前置地址
     *
     * @return java.lang.String
     * @author wxl
     * Date: 2023/1/4 17:15
     */
    public String getObjectUrlPrefix() {
        return "https://" +
                tencentProperties.getBucketName() +
                ".cos." +
                tencentProperties.getRegion() +
                "." +
                "myqcloud.com" +
                "/";
    }


    /**
     * // 简单文件上传, 最大支持 5 GB, 适用于小文件上传, 建议 20M以下的文件使用该接口
     * // 大文件上传请参照 API 文档高级 API 上传
     * //file里面填写本地图片的位置 我这里是相对项目的位置，在项目下有src/test/demo.jpg这张图片
     * Description: 上传文件
     *
     * @param file file
     * @param key  key
     * @return java.lang.String
     * @author wxl
     * Date: 2023/1/4 14:10
     */
    public String upload(File file, String key, Long userId) {
        String newKey = "/" + userId + "/" + key;
        PutObjectRequest putObjectRequest = new PutObjectRequest(tencentProperties.getBucketName(), newKey, file);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        Assert.notNull(putObjectResult, "上传失败");
        return this.getMergeObjectUrl(userId, key);
    }


    /**
     * Description: 上传文件
     *
     * @param file file
     * @return java.lang.String
     * @author wxl
     * Date: 2023/1/4 14:17
     */
    public String upload(MultipartFile file, Long userId, String key) throws IOException {
        InputStream is = file.getInputStream();
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        return this.upload(is, key, userId, objectMetadata);
    }

    /**
     * Description: 上传文件
     *
     * @param is  is
     * @param key key
     * @return java.lang.String
     * @author wxl
     * Date: 2023/1/4 14:17
     */
    private String upload(InputStream is, String key, Long userId, ObjectMetadata objectMetadata) {
        String newKey = "/" + userId + "/" + key;
        PutObjectRequest putObjectRequest = new PutObjectRequest(tencentProperties.getBucketName(), newKey, is, objectMetadata);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        Assert.notNull(putObjectResult, "上传失败");
        return this.getMergeObjectUrl(userId, key);
    }

    /**
     * Description: 下载文件 流
     *
     * @param key 设置要下载的文件所在的 对象桶的名称 和对象键
     * @author wxl
     * Date: 2023/1/4 14:11
     */
    public InputStream download(String key) {
        GetObjectRequest getObjectRequest = new GetObjectRequest(tencentProperties.getBucketName(), key);
        return cosClient.getObject(getObjectRequest).getObjectContent();
    }

    /**
     * Description: 下载文件 流
     *
     * @param url 设置要下载的文件所在的 url
     * @author wxl
     * Date: 2023/1/4 14:11
     */
    public InputStream downloadByUrl(String url) {
        String key = getObjectKeyByUrl(url);
        GetObjectRequest getObjectRequest = new GetObjectRequest(tencentProperties.getBucketName(), key);
        return cosClient.getObject(getObjectRequest).getObjectContent();
    }

    /**
     * Description: TODO
     * @author wxl
     * Date: 2023/1/5 9:44
     * @param url url
     * @return java.lang.String
     */
    public String getObjectKeyByUrl(String url) {
        return url.split(getObjectUrlPrefix())[1];
    }

    /**
     * Description: 下载文件
     *
     * @param downFile 设置要下载到的本地路径
     * @param key      设置要下载的文件所在的 对象桶的名称 和对象键
     * @author wxl
     * Date: 2023/1/4 14:11
     */
    public ObjectMetadata download(File downFile, String key) {
        GetObjectRequest getObjectRequest = new GetObjectRequest(tencentProperties.getBucketName(), key);
        return cosClient.getObject(getObjectRequest, downFile);
    }

    /**
     * Description: 删除是对象
     *
     * @param key key
     * @author wxl
     * Date: 2023/1/4 14:08
     */
    public void delete(String key) {
        // 指定要删除的 bucket 和对象键
        cosClient.deleteObject(tencentProperties.getBucketName(), key);
    }


}
