package com.obeast.common.three.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * @author wxl
 * Date 2023/1/4 9:56
 * @version 1.0
 * Description: 腾讯配置
 */
@Data
@RefreshScope
@ConfigurationProperties("third-party.tencent")
public class ThirdPartyTencentProperties {

    /**
     * 客户端Id
     */
    private String accessKey;

    /**
     * 客户端 密钥
     */
    private String secretKey;

    /**
     * 桶名
     */
    private String bucketName;

    /**
     * 地域名
     */
    private String region;

}
