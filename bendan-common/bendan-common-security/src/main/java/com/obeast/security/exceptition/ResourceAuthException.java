
package com.obeast.security.exceptition;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.obeast.core.base.CommonResult;
import com.obeast.core.constant.WebResultEnum;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 * @author wxl
 * Date 2022/11/29 9:25
 * @version 1.0
 * Description: 客户端异常处理 AuthenticationException 不同细化异常处理
 */
@Slf4j
@RequiredArgsConstructor
public class ResourceAuthException implements AuthenticationEntryPoint {

	private final ObjectMapper objectMapper;

	@Override
	@SneakyThrows
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) {
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		CommonResult<?> result = matchAuthenticationException(authException, response);
		PrintWriter printWriter = response.getWriter();
		printWriter.append(objectMapper.writeValueAsString(result));
	}

	private CommonResult<?> matchAuthenticationException (AuthenticationException authException, HttpServletResponse response) {
		CommonResult<String> result;
		switch (authException){
			case InvalidBearerTokenException ignored -> {
				log.error("资源服务器异常InvalidBearerTokenException:", authException);
				response.setStatus(WebResultEnum.UN_AUTHORIZED.getCode());
				result =  CommonResult.unauthorized();
			}
			case InsufficientAuthenticationException ignored -> {
				log.error("资源服务器异常InsufficientAuthenticationException:", authException);
				response.setStatus(WebResultEnum.REQ_REJECT.getCode());
				result =  CommonResult.error(WebResultEnum.REQ_REJECT, WebResultEnum.REQ_REJECT.getMessage());
			}
			default -> {
				log.error("资源服务器异常default:", authException);
				response.setStatus(WebResultEnum.INTERNAL_RESOURCE_SERVER_ERROR.getCode());
				result =  CommonResult.error(WebResultEnum.INTERNAL_RESOURCE_SERVER_ERROR, WebResultEnum.INTERNAL_RESOURCE_SERVER_ERROR.getMessage());
			}
		}

		return result;
	}


}
