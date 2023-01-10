package com.obeast.admin;


import com.obeast.common.mail.EnableMail;
import com.obeast.common.oss.annotation.EnableMinioOss;
import com.obeast.common.oss.annotation.EnableTencentOss;
import com.obeast.common.stt.annotation.EnableBaiduStt;
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
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author wxl
 * Date 2022/10/4 0:04
 * @version 1.0
 * Description:
 */
@EnableFeignClients(basePackageClasses = OAuth2TokenEndpoint.class)
//开启swagger
@EnableBendanSwagger
@EnableDiscoveryClient
//开启资源服务器
@EnableBendanResourceServer
//开启minio oss
@EnableMinioOss
//开启tencent oss
@EnableTencentOss
//开启 baidu stt
@EnableBaiduStt
//开启 邮箱
@EnableMail
@MapperScans({
        @MapperScan("com.obeast.security.business.dao"),
        @MapperScan("com.obeast.admin.business.dao")
})
@SpringBootApplication
@EnableAsync
public class BendanAdminApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(BendanAdminApplication.class, args);
//        String[] beanDefinitionNames = run.getBeanDefinitionNames();
//        for (String beanDefinitionName : beanDefinitionNames) {
//            System.out.println(beanDefinitionName);
//        }
    }
}
