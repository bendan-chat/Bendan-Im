
package com.obeast.core.config.web;


import cn.hutool.json.JSONUtil;
import com.obeast.core.base.CommonResult;
import com.obeast.core.constant.ResHeaderConstant;
import com.obeast.core.exception.BendanException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 声明拦截器
 */
@Slf4j
public class RequestHeaderContextInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        try {
            String header = request.getHeader(ResHeaderConstant.from);
            if (header == null) {
                throw new BendanException("异常调用");
            }else {
                if (header.equals(ResHeaderConstant.bendanValue)){
                    log.info("fromHeader:  {}", header);
                    return true;
                }else {
                    throw new BendanException("异常调用");
                }
            }
        } catch (Exception e) {
            log.error("全局拦截器异常-》》",e);
            response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
            response.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache");
            response.setCharacterEncoding("UTF-8");
            String body = JSONUtil.toJsonStr(CommonResult.remoteFailed());
            response.getWriter().println(body);
            response.getWriter().flush();
            return false;
        }
    }
}

