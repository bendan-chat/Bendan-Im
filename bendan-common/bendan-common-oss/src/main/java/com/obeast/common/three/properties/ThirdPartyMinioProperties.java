package com.obeast.common.three.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * @author wxl
 * Date 2023/1/4 9:56
 * @version 1.0
 * Description: minio配置
 */
@Data
@RefreshScope
@ConfigurationProperties("third-party.minio")
public class ThirdPartyMinioProperties {

    /**
     * 对象存储服务的对外URL
     */
    private String externalAccess;

    /**
     * 对象存储服务的URL
     */
    private String endpoint;

    /**
     * Access key就像用户ID，可以唯一标识你的账户。
     */
    private String accessKey;

    /**
     * Secret key是你账户的密码。
     */
    private String secretKey;

    /**
     * bucketName是你设置的桶的名称
     */
    private String bucketName;
}
