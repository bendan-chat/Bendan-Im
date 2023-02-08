package com.obeast.gateway.filter;

import cn.hutool.json.JSONUtil;
import com.obeast.core.base.CommonResult;
import com.obeast.core.constant.ResHeaderConstant;
import com.obeast.core.constant.WebResultEnum;
import com.obeast.gateway.config.GatewayProperties;
import lombok.RequiredArgsConstructor;
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
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.addOriginalRequestUrl;

/**
 * @author wxl
 * Date 2022/11/1 16:17
 * @version 1.0
 * Description: 全局过滤器
 */
@RequiredArgsConstructor
public class BendanRequestGlobalFilter implements GlobalFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(BendanRequestGlobalFilter.class);

    private final GatewayProperties gatewayProperties;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (checkOptions(exchange)){
            return Mono.empty();
        }
        ServerHttpRequest request = addHttpHeader(exchange);
        String path = request.getURI().getRawPath();
        if (!discharged(path)){
            String newPath = stripPrefix(exchange, request);
            ServerHttpRequest newRequest = request.mutate().path(newPath).build();
            exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, newRequest.getURI());
            return chain.filter(exchange.mutate().request(newRequest.mutate().build()).build());
        }
        return chain.filter(exchange.mutate().request(request.mutate().build()).build());
    }



    /**
     * Description: 放行路径
     * @author wxl
     * Date: 2022/11/25 9:51
     * @param path path
     * @return boolean
     */
    private boolean discharged(String path) {
        List<String> ignoreUrls = this.gatewayProperties.getIgnoreUrls();
        for (int i = 0; i < ignoreUrls.size(); i++) {
            if (ignoreUrls.contains(path)){
                return true;
            }
        }
        return false;
    }


    /**
     * Description: 检查是否是跨域请求
     * @author wxl
     * Date: 2022/12/7 13:08
     * @param exchange  exchange
     * @return java.lang.Boolean
     */
    private Boolean checkOptions (ServerWebExchange exchange) {
        ServerHttpRequest serverHttpRequest = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        // 跨域放行
        if (serverHttpRequest.getMethod() == HttpMethod.OPTIONS) {
            response.setStatusCode(HttpStatus.OK);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * Description: 重写路由
     * @author wxl
     * Date: 2022/12/7 13:02
     * @param exchange exchange
     * @param request  request
     * @return java.lang.String
     */
    private String stripPrefix (ServerWebExchange exchange, ServerHttpRequest request) {
        // 2. 重写StripPrefix
        addOriginalRequestUrl(exchange, request.getURI());
        String rawPath = request.getURI().getRawPath();
        log.debug("当前请求的路------> {}", rawPath);
        return  "/" + Arrays
                .stream(StringUtils.tokenizeToStringArray(rawPath, "/"))
                .skip(1L)
                .collect(Collectors.joining("/"));

    }

    /**
     * Description: 新增请求头中from 参数
     * @author wxl
     * Date: 2022/12/7 13:03
     * @param exchange exchange
     * @return org.springframework.http.server.reactive.ServerHttpRequest
     */
    private ServerHttpRequest addHttpHeader (ServerWebExchange exchange) {
        return exchange.getRequest().mutate().headers(httpHeaders -> {
            httpHeaders.put(ResHeaderConstant.from,
                    Collections.singletonList(ResHeaderConstant.bendanValue));
        }).build();
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
        String str = JSONUtil.toJsonStr(CommonResult.error(WebResultEnum.UN_AUTHORIZED, WebResultEnum.UN_AUTHORIZED.getMessage()));
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
