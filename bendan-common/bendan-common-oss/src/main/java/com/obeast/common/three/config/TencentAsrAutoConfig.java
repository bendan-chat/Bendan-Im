package com.obeast.common.three.config;

import com.obeast.common.three.properties.ThirdPartyTencentProperties;
import com.obeast.common.three.template.TencentAsrTemplate;
import com.tencentcloudapi.asr.v20190614.AsrClient;
import com.tencentcloudapi.common.Credential;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;

/**
 * @author wxl
 * Date 2023/1/24 20:30
 * @version 1.0
 * Description: 腾讯asr  自动配置
 */
public class TencentAsrAutoConfig {

    /**
     * Description: 配置
     * @author wxl
     * Date: 2023/1/4 14:56
     * @return com.obeast.common.oss.properties.ThirdPartyTencentProperties
     */
    @Bean
    @ConditionalOnMissingBean(ThirdPartyTencentProperties.class)
    public ThirdPartyTencentProperties thirdPartyTencentProperties () {
        return new ThirdPartyTencentProperties();
    }



    /**
     * Description: asr 客户端
     * @author wxl
     * Date: 2023/1/24 20:34
     * @param thirdPartyTencentProperties thirdPartyTencentProperties
     * @return com.tencentcloudapi.asr.v20190614.AsrClient
     */
    @Bean
    public AsrClient asrClient (ThirdPartyTencentProperties thirdPartyTencentProperties) {
        Credential cred = new Credential(thirdPartyTencentProperties.getAccessKey(), thirdPartyTencentProperties.getSecretKey());
        return new AsrClient(cred, "");
    }


    @Bean
    public TencentAsrTemplate tencentAsrTemplate (AsrClient asrClient) {
        return new TencentAsrTemplate(asrClient);
    }
}
