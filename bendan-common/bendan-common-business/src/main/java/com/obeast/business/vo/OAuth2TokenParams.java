package com.obeast.business.vo;

import lombok.Data;

/**
 * @author wxl
 * Date 2022/10/28 15:25
 * @version 1.0
 * Description: OAuth2TokenParams
 */
@Data
public class OAuth2TokenParams {

    String username;

    String password;

    String grant_type;

    String scope;

    String clientId;

    String clientSecret;
}
