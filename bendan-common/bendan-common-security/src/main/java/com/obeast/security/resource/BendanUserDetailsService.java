package com.obeast.security.resource;

import org.springframework.core.Ordered;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author wxl
 * Date 2022/11/30 11:55
 * @version 1.0
 * Description: UserDetailsService 扩展基类方便之后web、app分离
 */
public interface BendanUserDetailsService extends UserDetailsService, Ordered {

    /**
     * Description: 是否支持此客户端校验
     * @author wxl
     * Date: 2022/11/30 11:56
     * @param clientId 客户端ID
     * @param grantType  the type
     * @return boolean
     */
    default boolean support(String clientId, String grantType) {
        return true;
    }
    /**
     * 排序值 默认取最大的
     * @return 排序值
     */
    default int getOrder() {
        return 0;
    }


}
