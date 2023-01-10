package com.obeast.core.config.fegin;

import com.obeast.core.constant.BendanResHeaderConstant;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

/**
 * @author wxl
 * Date 2022/11/22 15:40
 * @version 1.0
 * Description: feign 调用添加头部
 */
@AutoConfiguration
public class FeignConfig implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header(BendanResHeaderConstant.from, Collections.singletonList(BendanResHeaderConstant.feignValue));

        //1、使用RequestContextHolder拿到刚进来的请求数据
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (requestAttributes != null) {
            //老请求
            HttpServletRequest request = requestAttributes.getRequest();
            String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
            requestTemplate.header(HttpHeaders.AUTHORIZATION, authorization);
        }
    }
}
