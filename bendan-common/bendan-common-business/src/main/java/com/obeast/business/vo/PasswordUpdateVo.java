package com.obeast.business.vo;

import lombok.Data;

/**
 * @author wxl
 * Date 2023/2/8 13:00
 * @version 1.0
 * Description: 更新密码
 */
@Data
public class PasswordUpdateVo {
    /**
     * 用户Id
     * */
    private Long userId;

    /**
     * 用户邮箱
     * */
    private String email;

    /**
     * 修该的密码
     * */
    private String password;
}
