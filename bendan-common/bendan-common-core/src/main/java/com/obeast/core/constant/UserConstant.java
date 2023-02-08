package com.obeast.core.constant;

/**
 * @author wxl
 * Date 2022/10/20 11:31
 * @version 1.0
 * Description: 用户管理的常量
 */
public interface UserConstant {


    /**
     * 用户不存在
     * */
    String USER_NOT_FOUND = "用户不存在";

    /**
     * 用户已经存在
     * */
    String USER_EXIST = "用户已经存在";
    
    /**
     * 用户锁定状态
     * */
    Integer NORMAL_STATUS = 2;


    /**
     *角色权限前缀
     * */
    String ROLE = "ROLE_";
    /**
     * 用户名为空
     * */
    String USERNAME_IS_NULL = "用户名为空";

    /**
     * 密码为空
     * */
    String PASSWORD_IS_NULL = "密码为空";


    enum Type {
        MIN_APP("min-app"),
        WEB_BROWSER("web-browser"),
        ;
        private final String name;

        Type(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
    enum RequestTokenParam {
        CLIENT_ID("client-id"),
        CLIENT_SECRET("client-secret"),
        GRANT_TYPE("grant-type"),
        USERNAME("username"),
        PASSWORD("password"),
        ;
        private final String name;
        RequestTokenParam(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    enum GrantType {
        AUTHORIZATION_CODE("authorization_code"),
        /** @deprecated */
        @Deprecated
        IMPLICIT("implicit"),
        REFRESH_TOKEN("refresh_token"),
        CLIENT_CREDENTIALS("client_credentials"),
        PASSWORD("password"),
        WT_BEARER("urn:ietf:params:oauth:grant-type:jwt-bearer")
        ;
        private final String name;


        GrantType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }


    /**
     * Description: 格式redis key
     * @author wxl
     * Date: 2023/1/10 9:55
     * @return java.lang.String
     */
    static String formatKey  (String username) {
        return String.format("%s::%s", CacheConstant.USER_INFO, username);
    }
}
