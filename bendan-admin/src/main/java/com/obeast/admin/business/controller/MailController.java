package com.obeast.admin.business.controller;

import com.obeast.admin.business.service.BendanMailService;
import com.obeast.core.base.CommonResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wxl
 * Date 2023/2/3 21:52
 * @version 1.0
 * Description: 邮箱接口
 */
@Tag(name = "邮箱 接口")
@RequestMapping("/mail")
@RestController
@RequiredArgsConstructor
public class MailController {
    private final BendanMailService bendanMailService;

    @Operation(summary = "后台发送验证码")
    @GetMapping("/sendMailCodeByUserId")
    public String sendMailCode(@RequestParam("useId") Long useId, @RequestParam("email") String email) {
        return bendanMailService.sendMailCode(useId, email);
    }

    @Operation(summary = "前台发送验证码")
    @GetMapping("/sendMailCode")
    public Long sendMailCode(
            @RequestParam("email") String email,
            @RequestParam("randomNum") String randomNum
    ) {
       return bendanMailService.sendMailCode(email, randomNum);
    }



    @Operation(summary = "校验验证码")
    @GetMapping("/checkMailCode")
    public CommonResult<?> checkMailCode(
            @RequestParam("userId") Long userId,
            @RequestParam("mailCode") String mailCode) {
        return bendanMailService.checkVerificationCode(userId, mailCode);
    }
}
