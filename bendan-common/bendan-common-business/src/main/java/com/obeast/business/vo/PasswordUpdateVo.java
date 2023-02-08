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
    private Long userId;
    private String password;
}
