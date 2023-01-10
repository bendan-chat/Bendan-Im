package com.obeast.chat;

import com.obeast.security.annotation.EnableBendanResourceServer;
import com.obeast.security.business.service.remote.OAuth2TokenEndpoint;
import com.obeast.swagger.annotation.EnableBendanSwagger;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author wxl
 * Date 2022/12/27 14:25
 * @version 1.0
 * Description:
 */
@EnableFeignClients(basePackageClasses = OAuth2TokenEndpoint.class, basePackages = "com.obeast.chat.business.service.feign")
@EnableBendanSwagger
@EnableDiscoveryClient
@MapperScans({
        @MapperScan("com.obeast.security.business.dao"),
        @MapperScan("com.obeast.chat.business.dao")
})
@EnableBendanResourceServer
@SpringBootApplication
public class BendanChatApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(BendanChatApplication.class, args);
//        String[] beanDefinitionNames = run.getBeanDefinitionNames();
//        for (String beanDefinitionName : beanDefinitionNames) {
//            System.out.println(beanDefinitionName);
//        }
    }
}
