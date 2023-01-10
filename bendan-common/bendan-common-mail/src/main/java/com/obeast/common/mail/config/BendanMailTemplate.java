package com.obeast.common.mail.config;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;
import java.util.random.RandomGenerator;

/**
 * @author wxl
 * Date 2022/10/28 9:48
 * @version 1.0
 * Description: 邮箱发送
 */
@RequiredArgsConstructor
public class BendanMailTemplate {
    private final TemplateEngine templateEngine;

    private final JavaMailSenderImpl mailSender;

    private final BendanProjectProperties projectProperties;

    private final MailProperties mailProperties;



    /**
     * Description: 发送验证码
     * @author wxl
     * Date: 2022/10/28 9:53
     * @param to 发送给
     * @param randomNums 验证码
     */
    public boolean sendVerificationCode(String to, String randomNums) {
        return sendText(to, "验证码", randomNums, projectProperties.getName(), projectProperties.getAuthor());
    }

    /**
     * Description: 生成验证码
     * @author wxl
     * Date: 2023/1/9 14:10
     * @return java.lang.String
     */
    public String genVerificationCode () {
        return String.valueOf(RandomGenerator.getDefault().nextLong(100000L, 999999L));
    }



    /**
     *     //todo 发送的时候需要多线程；发送完的消息应该丢到MQ然后让消费者消费
     * Description: 发送邮件
     * @author wxl
     * Date: 2022/10/28 9:51
     * @param to 发送给谁的邮箱
     * @param subject 邮件标题
     * @param text 发送内容
     * @param project 项目名
     * @param author 作者
     */
    public boolean sendText(String to, String subject, String text, String project, String author) {

        Context context = new Context();
        context.setVariable("project", project);
        context.setVariable("author", author);
        context.setVariable("code", text);
        String emailContent = templateEngine.process("mail", context);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(mailProperties.getUsername());
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(emailContent, true);
            mailSender.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
