package com.obeast.security.resource;

import com.obeast.core.constant.CacheConstant;
import com.obeast.core.constant.SysConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.lang.Nullable;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthorizationCode;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.OAuth2TokenType;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author wxl
 * Date 2022/10/31 15:50
 * @version 1.0
 * Description: OAuth2Authorization redis缓存实现
 */
@RequiredArgsConstructor
public class BendanOAuth2AuthorizationServiceImpl implements OAuth2AuthorizationService {

    private static final Long TIMEOUT = 10L;

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void save(OAuth2Authorization authorization) {
        Assert.notNull(authorization, "OAuth2Authorization cannot be null");

        if (isStateNonNull(authorization)) {
            String state = authorization.getAttribute("state");
            // TODO: 2022/11/23 注释掉可以在redis看见
            redisTemplate.setValueSerializer(RedisSerializer.java());
            redisTemplate.opsForValue().set(
                    CacheConstant.createRedisKey(OAuth2ParameterNames.STATE, state),
                    authorization,
                    TIMEOUT,
                    SysConstant.REDIS_UNIT
            );
        }
//        授权码token
        if (isCodeNonNull(authorization)) {
            OAuth2Authorization.Token<OAuth2AuthorizationCode> authorizationCode = authorization
                    .getToken(OAuth2AuthorizationCode.class);
            OAuth2AuthorizationCode authorizationCodeToken = authorizationCode.getToken();
            long between = SysConstant.TOKEN_UNIT.between(authorizationCodeToken.getIssuedAt(),
                    authorizationCodeToken.getExpiresAt());
            redisTemplate.setValueSerializer(RedisSerializer.java());
            redisTemplate.opsForValue().set(
                    CacheConstant.createRedisKey(OAuth2ParameterNames.CODE, authorizationCodeToken.getTokenValue()),
                    authorization,
                    between,
                    SysConstant.REDIS_UNIT
            );

        }
//        刷新token
        if (isRefreshTokenNonNull(authorization)) {
            OAuth2RefreshToken refreshToken = authorization.getRefreshToken().getToken();
            long between = SysConstant.TOKEN_UNIT.between(refreshToken.getIssuedAt(), refreshToken.getExpiresAt());
            redisTemplate.setValueSerializer(RedisSerializer.java());
            redisTemplate.opsForValue().set(
                    CacheConstant.createRedisKey(OAuth2ParameterNames.REFRESH_TOKEN, refreshToken.getTokenValue()),
                    authorization
                    ,
                    between,
                    SysConstant.REDIS_UNIT
            );
        }
//      access token
        if (isAccessTokenNonNull(authorization)) {
            OAuth2AccessToken accessToken = authorization.getAccessToken().getToken();
            long between = SysConstant.TOKEN_UNIT.between(accessToken.getIssuedAt(), accessToken.getExpiresAt());

            redisTemplate.setValueSerializer(RedisSerializer.java());
            redisTemplate.opsForValue().set(
                    CacheConstant.createRedisKey(OAuth2ParameterNames.ACCESS_TOKEN, accessToken.getTokenValue()),
                    authorization
                    ,
                    between,
                    SysConstant.REDIS_UNIT
            );
        }
    }
    @Override
    public void remove(OAuth2Authorization authorization) {
        Assert.notNull(authorization, "authorization cannot be null");

        List<String> keys =  new ArrayList<>();
        if (isStateNonNull(authorization)) {
            String state = authorization.getAttribute("state");
            keys.add(CacheConstant.createRedisKey(OAuth2ParameterNames.STATE, state));

        }
        if (isCodeNonNull(authorization)) {
            OAuth2Authorization.Token<OAuth2AuthorizationCode> authorizationCode = authorization
                    .getToken(OAuth2AuthorizationCode.class);
            OAuth2AuthorizationCode authorizationCodeToken = authorizationCode.getToken();
            keys.add(CacheConstant.createRedisKey(OAuth2ParameterNames.CODE, authorizationCodeToken.getTokenValue()));


        }
        if (isRefreshTokenNonNull(authorization)) {
            OAuth2RefreshToken refreshToken = authorization.getRefreshToken().getToken();
            keys.add(CacheConstant.createRedisKey(OAuth2ParameterNames.REFRESH_TOKEN, refreshToken.getTokenValue()));
        }

        if (isAccessTokenNonNull(authorization)) {
            OAuth2AccessToken accessToken = authorization.getAccessToken().getToken();
            keys.add(CacheConstant.createRedisKey(OAuth2ParameterNames.ACCESS_TOKEN, accessToken.getTokenValue()));
        }
        redisTemplate.delete(keys);
    }

    @Override
    public OAuth2Authorization findById(String id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public OAuth2Authorization findByToken(String token,@Nullable OAuth2TokenType tokenType) {
        Assert.hasText(token, "token cannot be empty");
        Assert.notNull(tokenType, "tokenType cannot be empty");
        redisTemplate.setValueSerializer(RedisSerializer.java());
        return (OAuth2Authorization) redisTemplate.opsForValue().get(CacheConstant.createRedisKey(tokenType.getValue(), token));
    }



    /**
     * Description: 判断state是否为空
     *
     * @param authorization OAuth2Authorization
     * @return boolean
     * @author wxl
     * Date: 2022/10/31 17:00
     */
    private static boolean isStateNonNull(OAuth2Authorization authorization) {
        return Objects.nonNull(authorization.getAttribute("state"));
    }

    /**
     * Description: 判断授权码是否为空
     *
     * @param authorization OAuth2Authorization
     * @return boolean
     * @author wxl
     * Date: 2022/10/31 17:00
     */
    private static boolean isCodeNonNull(OAuth2Authorization authorization) {
        OAuth2Authorization.Token<OAuth2AuthorizationCode> authorizationCode = authorization
                .getToken(OAuth2AuthorizationCode.class);
        return Objects.nonNull(authorizationCode);
    }

    /**
     * Description: 判断refreshToken是否为控
     *
     * @param authorization OAuth2Authorization
     * @return boolean
     * @author wxl
     * Date: 2022/10/31 17:01
     */
    private static boolean isRefreshTokenNonNull(OAuth2Authorization authorization) {
        return Objects.nonNull(authorization.getRefreshToken());
    }

    /**
     * Description: 判断AccessToken是否为空
     *
     * @param authorization OAuth2Authorization
     * @return boolean
     * @author wxl
     * Date: 2022/10/31 17:02
     */
    private static boolean isAccessTokenNonNull(OAuth2Authorization authorization) {
        return Objects.nonNull(authorization.getAccessToken());
    }
}
