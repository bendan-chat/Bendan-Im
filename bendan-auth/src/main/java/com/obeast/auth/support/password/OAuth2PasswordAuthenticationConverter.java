package com.obeast.auth.support.password;

import com.obeast.auth.support.base.OAuth2BaseAuthenticationConverter;
import com.obeast.auth.support.base.OAuth2BaseAuthenticationToken;
import com.obeast.auth.utils.OAuth2EndpointUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Set;

/**
 * @author wxl
 * Date 2022/10/24 13:52
 * @version 1.0
 * Description: 密码模式的Converter
 */
public class OAuth2PasswordAuthenticationConverter extends OAuth2BaseAuthenticationConverter<OAuth2BaseAuthenticationToken> {
    @Override
    public boolean support(String grantType) {
        return AuthorizationGrantType.PASSWORD.getValue().equals(grantType);
    }

    /**
     * Description: 检查密码模式的参数
     * @author wxl
     * Date: 2022/10/24 18:09
     * @param request
     * @return void
     */
    @Override
    public void checkRequestParams(HttpServletRequest request) {
        MultiValueMap<String, String> parameters = OAuth2EndpointUtils.getParameters(request);
        String username = parameters.getFirst(OAuth2ParameterNames.USERNAME);
        // username
        if (!StringUtils.hasText(username) || parameters.get(OAuth2ParameterNames.USERNAME).size() != 1) {
            OAuth2EndpointUtils.throwError(OAuth2ErrorCodes.INVALID_REQUEST, OAuth2ParameterNames.USERNAME,
                    OAuth2EndpointUtils.ACCESS_TOKEN_REQUEST_ERROR_URI);
        }

        // password
        String password = parameters.getFirst(OAuth2ParameterNames.PASSWORD);
        if (!StringUtils.hasText(password) || parameters.get(OAuth2ParameterNames.PASSWORD).size() != 1) {
            OAuth2EndpointUtils.throwError(OAuth2ErrorCodes.INVALID_REQUEST, OAuth2ParameterNames.PASSWORD,
                    OAuth2EndpointUtils.ACCESS_TOKEN_REQUEST_ERROR_URI);
        }
    }

    @Override
    public OAuth2BaseAuthenticationToken buildConvertToken(
            Authentication clientPrincipal,
            Set<String> requestedScopes,
            Map<String, Object> additionalParameters) {
        return new OAuth2PasswordAuthenticationToken(
                AuthorizationGrantType.PASSWORD,
                clientPrincipal,
                requestedScopes,
                additionalParameters);
    }
}
