package com.obeast.auth;


import cn.hutool.crypto.digest.BCrypt;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.obeast.auth.business.BendanSecurityUser;
import com.obeast.auth.business.service.remote.BendanSysUserRemote;
import com.obeast.auth.support.resource.ResourcesProperties;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ClientSettings;

import java.util.HashSet;
import java.util.UUID;

/**
 * @author wxl
 * Date 2022/10/20 14:40
 * @version 1.0
 * Description:
 */
@SpringBootTest
public class JdbcTest {
    private static final PasswordEncoder encoder = new BCryptPasswordEncoder();

    //
    @Autowired
    private RegisteredClientRepository registeredClientRepository;

    @Autowired
    BendanSysUserRemote bendanSysUserRemote;

    public static void main(String[] args) {

    }

    @Test
    void test(){


    }


    /**
     * 创建clientId信息
     */
    @Test
    @Disabled
    void testSaveClient() {

        RegisteredClient cLIENT_SECRET_BASIC = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("messaging-client")
                .clientSecret("{noop}secret")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .redirectUri("http://127.0.0.1:18812/authorized")
                .scope(OidcScopes.OPENID)
                .scope("message.read")
                .scope("message.write")
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                .build();
        RegisteredClient cLIENT_SECRET_POST = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("messaging-client")
                .clientSecret("{noop}secret")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
                .authorizationGrantType(AuthorizationGrantType.PASSWORD)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .redirectUri("http://127.0.0.1:18812/authorized")
                .scope(OidcScopes.OPENID)
                .scope("message.read")
                .scope("message.write")
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                .build();

//        注意client_id的变化
        //请求头
//        http://127.0.0.1:18812/oauth2/authorize?response_type=code&client_id=messaging-client&scope=message.read&redirect_uri=http://127.0.0.1:18812/authorized
//        registeredClientRepository.save(cLIENT_SECRET_BASIC);

//         请求体
//        http://127.0.0.1:18812/oauth2/authorize?response_type=code&client_id=messaging-client2&scope=message.read&redirect_uri=http://127.0.0.1:18812/authorized
//        registeredClientRepository.save(cLIENT_SECRET_POST);


        registeredClientRepository.save(cLIENT_SECRET_POST);

    }

}