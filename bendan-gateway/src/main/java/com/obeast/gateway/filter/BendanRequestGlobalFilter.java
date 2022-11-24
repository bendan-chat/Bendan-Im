package com.obeast.gateway.filter;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.json.JSONUtil;
import com.obeast.common.base.CommonResult;
import com.obeast.common.base.ResultCode;
import com.obeast.common.constant.BendanResHeaderConstant;
import com.obeast.common.constant.OAuth2Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

/**
 * @author wxl
 * Date 2022/11/1 16:17
 * @version 1.0
 * Description: 全局过滤器
 */
public class BendanRequestGlobalFilter implements GlobalFilter, Ordered {
    private static final Logger log = LoggerFactory.getLogger(BendanRequestGlobalFilter.class);
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        // 跨域放行
        if (request.getMethod() == HttpMethod.OPTIONS) {
            response.setStatusCode(HttpStatus.OK);
            return Mono.empty();
        }
        ServerHttpRequest exchangeRequest = request
                .mutate()
                .headers(httpHeaders -> {
                    httpHeaders.put(BendanResHeaderConstant.from, Collections.singletonList(BendanResHeaderConstant.bendanValue));
                }).build();
        System.out.println(request.getURI().getPath());
        boolean isAuthToken = CharSequenceUtil.containsAnyIgnoreCase(request.getURI().getPath(),
                OAuth2Constant.LOGIN_URL);

        if (!authenticateToken(request) && !isAuthToken){
            return this.responseBody(exchange);
        }
        return chain.filter(
                exchange
                        .mutate()
                        .request(exchangeRequest.mutate().build()).build()
        );
    }


    /**
     * Description:认证
     * @author wxl
     * Date: 2022/11/22 17:11
     * @param request HttpServletRequest
     * @return boolean
     */
    private boolean authenticateToken(ServerHttpRequest request) {
        if (request != null) {
            List<String> authorizationList = request.getHeaders().get(BendanResHeaderConstant.authorization);
            if (authorizationList != null) {
                String authorization = authorizationList.get(0);
                return true;
            }
        }
        return false;
    }



    /**
     * Description: res响应
     * @author wxl
     * Date: 2022/11/24 17:14
     * @param exchange exchange
     * @return reactor.core.publisher.Mono<java.lang.Void>
     */
    public Mono<Void> responseBody(ServerWebExchange exchange) {
//        响应头
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, "application/json");
        ServerWebExchange header = exchange.mutate().response(response).build();
//        响应体
        String str = JSONUtil.toJsonStr(CommonResult.error(ResultCode.UN_AUTHORIZED, ResultCode.UN_AUTHORIZED.getMessage()));
        byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
        return header.getResponse()
                .writeWith(Flux.just(exchange.getResponse().bufferFactory().wrap(bytes)));
    }

    /**
     * 设置响应体的请求头
     */
    public ServerWebExchange responseHeader(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, "application/json");
        return exchange.mutate().response(response).build();
    }

    @Override
    public int getOrder() {
        return 10;
    }
}
