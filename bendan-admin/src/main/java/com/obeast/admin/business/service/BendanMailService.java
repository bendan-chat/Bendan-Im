package com.obeast.admin.business.service;

import com.obeast.common.mail.config.BendanMailTemplate;
import com.obeast.core.constant.CacheConstant;
import com.obeast.core.constant.SysConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author wxl
 * Date 2023/1/9 14:14
 * @version 1.0
 * Description: 邮箱 方法类
 */
@Service("bendanMailService")
@RequiredArgsConstructor
public class BendanMailService {
    private final BendanMailTemplate mailTemplate;

    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * Description: 发送邮箱
     * @author wxl
     * Date: 2023/1/9 14:22
     * @param useId  用户id
     */
    public void sendMail (Long useId, String email) {
        String randomNum = mailTemplate.genVerificationCode();
        mailTemplate.sendVerificationCode(email, randomNum);

    }

    /**
     * Description: 异步存储验证码到redis
     * @author wxl
     * Date: 2023/1/9 14:30
     * @param useId useId
     * @param email email
     */
    @Async
    public void saveVerificationCode (Long useId, String email) {
        redisTemplate.opsForValue().set(
                formatKey(useId),
                email,
                60,
                TimeUnit.SECONDS
        );
    }

    /**
     * Description: 序列化key
     * @author wxl
     * Date: 2023/1/9 14:21
     * @param useId  用户id
     * @return java.lang.String
     */
    private String formatKey(Long useId) {
        return String.format("%s::%s::%s", CacheConstant.MAIL_VERIFICATION_CODE, useId);
    }
}
