package com.obeast.common.three.config;

import com.obeast.common.three.properties.ThirdPartyTencentProperties;
import com.obeast.common.three.template.TencentOssTemplate;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.region.Region;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

/**
 * @author wxl
 * Date 2023/1/4 14:56
 * @version 1.0
 * Description: 腾讯 自动配置
 */
public class TencentOssAutoConfig {

    /**
     * Description: 配置
     * @author wxl
     * Date: 2023/1/4 14:56
     * @return com.obeast.common.oss.properties.ThirdPartyTencentProperties
     */
    @Bean
    @Primary
    public ThirdPartyTencentProperties thirdPartyTencentProperties () {
        return new ThirdPartyTencentProperties();
    }

    /**
     * Description: 客户端
     * @author wxl
     * Date: 2023/1/4 14:57
     * @param tencentProperties tencentProperties
     * @return com.qcloud.cos.COSClient
     */
    @Bean
    public  COSClient cosClient(ThirdPartyTencentProperties tencentProperties) {
        COSCredentials cred = new BasicCOSCredentials(tencentProperties.getAccessKey(), tencentProperties.getSecretKey());
        // 2 设置bucket的区域, COS地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        // clientConfig中包含了设置region, https(默认http), 超时, 代理等set方法, 使用可参见源码或者接口文档FAQ中说明
        ClientConfig clientConfig = new ClientConfig(new Region(tencentProperties.getRegion()));
        return new COSClient(cred, clientConfig);
    }


    @Bean
    public TencentOssTemplate tencentOssTemplate (COSClient cosClient, ThirdPartyTencentProperties tencentProperties) {
        return new TencentOssTemplate(cosClient, tencentProperties);
    }
}

