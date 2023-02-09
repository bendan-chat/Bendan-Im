package com.obeast.core.constant;

/**
 * @author wxl
 * Date 2022/12/9 13:32
 * @version 1.0
 * Description: L2 缓存常量
 */
public interface CacheConstant {

    /**
     * 菜单信息缓存
     */
    String MENU_DETAILS = "MENU_LIST";


    /**
     * 菜单信息缓存
     */
    String USERNAME_LIST = "USERNAME_LIST";


    /**
     * 二级缓存名字
     * */
    String USER_INFO = "USER_INFO";

    /**
     * token 名
     * */
    String TOKEN = "token";


    /**
     * 邮箱验证码
     * */
    String MAIL_VERIFICATION_CODE = "MAIL_VERIFICATION_CODE";

    /**
     * Description: 创建redis存储的key
     *
     * @param type  token type
     * @param value token value
     * @return java.lang.String
     * @author wxl
     * Date: 2022/10/31 17:02
     */
    static String createRedisKey(String type, String value) {
        return String.format("%s::%s::%s", CacheConstant.TOKEN, type, value);
    }
}
