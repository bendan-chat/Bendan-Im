package com.obeast.core.constant;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

/**
 * @author wxl
 * Date 2022/12/9 13:51
 * @version 1.0
 * Description: 系统常量
 */
public interface SysConstant {

    /**
     * 登录失败
     * */
    String LOGIN_FAILED = "登录失败！";

    /**
     * 登录成功！
     * */
    String LOGIN_SUCCESS = "登录成功！";


    /**
     * Bearer
     * */
    String BEARER = "Bearer ";

    /**
     * accessToken
     * */
    String ACCESSTOKEN = "accessToken";

    /**
     * refreshToken
     * */
    String REFRESHTOKEN = "refreshToken";


    /**
     * token 名
     * */
    String REFRESH_TOKEN = "refresh_token";


    /**
     * token 名
     * */
    String COOKIE = "/";

    /**
     * token 存储单位
     * */
    ChronoUnit TOKEN_UNIT = ChronoUnit.SECONDS;

    /**
     * redis 过期时间
     * */
    Duration REDIS_EXPIRED = Duration.ofMinutes(24 * 60 * 30);


    /**
     * redis 单位
     * */
    TimeUnit REDIS_UNIT = TimeUnit.HOURS;
}
