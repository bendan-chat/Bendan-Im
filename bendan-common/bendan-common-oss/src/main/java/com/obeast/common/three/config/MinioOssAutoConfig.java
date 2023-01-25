package com.obeast.common.three.config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.obeast.common.three.domain.FlyweightRes;
import com.obeast.common.three.template.MinioOssTemplate;
import com.obeast.common.three.properties.ThirdPartyMinioProperties;
import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;


public class MinioOssAutoConfig {

    /**
     * Description: mino 配置
     * @author wxl
     * Date: 2023/1/4 14:37
     * @return com.obeast.common.oss.properties.ThirdPartyMinioProperties
     */
    @Bean
    @Primary
    public ThirdPartyMinioProperties thirdPartyMinioProperties () {
        return new ThirdPartyMinioProperties();
    }


    /**
     * Description: 初始化一个MinIO客户端用来连接MinIO存储服务
     * @author wxl
     * Date: 2023/1/4 11:53
     * @return io.minio.MinioClient
     */
    @Bean(name = "minioClient")
    public MinioClient minioClient(ThirdPartyMinioProperties minioProperties) {
        return MinioClient.builder().endpoint(minioProperties.getEndpoint()).credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey()).build();
    }

    /**
     * Description: 模板方法
     * @author wxl
     * Date: 2023/1/4 14:36
     * @param minioClient  minioClient
     * @param minioProperties minioProperties
     * @return com.obeast.common.oss.template.MinioOssTemplate
     */
    @Bean
    public MinioOssTemplate MinioTemplate (MinioClient minioClient, ThirdPartyMinioProperties minioProperties) {
        return new MinioOssTemplate(minioClient, minioProperties);
    }

    /**
     * Description: 上传文件时的全局共享变量
     * @author wxl
     * Date: 2023/1/4 14:45
     * @return com.obeast.common.oss.domain.FlyweightRes
     */
    @Bean
    public FlyweightRes flyweightRes () {
        return new FlyweightRes();
    }


    /**
     * Description: 获取忽略的url
     * @author wxl
     * Date: 2023/1/4 11:52
     * @return java.lang.String
     */
    @JsonIgnore
    public String getPublicUrlPrefix(ThirdPartyMinioProperties minioProperties){
        return minioProperties.getExternalAccess() + "/" + minioProperties.getBucketName();
    }
}

