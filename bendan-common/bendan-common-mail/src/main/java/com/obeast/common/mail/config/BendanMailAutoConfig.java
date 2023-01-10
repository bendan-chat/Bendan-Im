package com.obeast.common.mail.config;

import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.TemplateEngine;

/**
 * @author wxl
 * Date 2023/1/9 13:57
 * @version 1.0
 * Description: 邮箱自动配置
 */
public class BendanMailAutoConfig {

    /**
     * Description: BendanProjectProperties
     * @author wxl
     * Date: 2023/1/9 14:02
     * @return com.obeast.common.mail.config.BendanProjectProperties
     */
    @Bean
    public BendanProjectProperties bendanProjectProperties () {
        return new BendanProjectProperties();
    }


    /**
     * Description: MailService
     * @author wxl
     * Date: 2023/1/9 14:02
     * @param bendanProjectProperties  bendanProjectProperties
     * @return com.obeast.common.mail.service.MailService
     */
    @Bean
    public BendanMailTemplate bendanMailTemplate ( TemplateEngine templateEngine,JavaMailSenderImpl mailSender,BendanProjectProperties bendanProjectProperties, MailProperties mailProperties) {
        return new BendanMailTemplate(templateEngine, mailSender, bendanProjectProperties, mailProperties);
    }

}
