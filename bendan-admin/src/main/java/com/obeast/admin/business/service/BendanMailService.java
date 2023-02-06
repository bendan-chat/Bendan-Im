package com.obeast.admin.business.service;

import cn.hutool.core.util.ObjectUtil;
import com.obeast.common.mail.config.BendanMailTemplate;
import com.obeast.core.base.CommonResult;
import com.obeast.core.constant.CacheConstant;
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
     *
     * @param useId 用户id
     * @author wxl
     * Date: 2023/1/9 14:22
     */
    public void sendMailCode(Long useId, String email) {
        String randomNum = mailTemplate.genVerificationCode();
        boolean sendVerificationCode = mailTemplate.sendVerificationCode(email, randomNum);
        if (sendVerificationCode) {
            this.saveVerificationCode(useId, randomNum);
        }
    }

    /**
     * Description: 校验验证码
     * @author wxl
     * Date: 2023/2/6 17:34
     * @param userId userId
     * @param mailCode  mailCode
     * @return com.obeast.core.base.CommonResult<?>
     */
    public CommonResult<?> checkVerificationCode(Long userId, String mailCode) {
        String key = this.formatKey(userId);
        Object o = redisTemplate.opsForValue().get(key);
        if (ObjectUtil.isNotNull(o)) {
            String str = (String) o;
            if (str.equals(mailCode)) {
                return CommonResult.success();
            }
        }
        return CommonResult.error();
    }

    /**
     * Description: 异步存储验证码到redis
     *
     * @param useId            useId
     * @param verificationCode verificationCode
     * @author wxl
     * Date: 2023/1/9 14:30
     */
    @Async
    public void saveVerificationCode(Long useId, String verificationCode) {
        redisTemplate.opsForValue().set(
                formatKey(useId),
                verificationCode,
                5,
                TimeUnit.MINUTES
        );
    }

    /**
     * Description: 序列化key
     *
     * @param userId 用户id
     * @return java.lang.String
     * @author wxl
     * Date: 2023/1/9 14:21
     */
    public String formatKey(Long userId) {
        return String.format("%s::%s", CacheConstant.MAIL_VERIFICATION_CODE, userId);
    }
}
