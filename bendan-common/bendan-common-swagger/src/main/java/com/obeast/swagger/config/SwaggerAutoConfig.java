package com.obeast.swagger.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.Scopes;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wxl
 * Date 2022/12/6 16:27
 * @version 1.0
 * Description: swagger
 */
@RequiredArgsConstructor
@ConditionalOnMissingClass("org.springframework.cloud.gateway.config.GatewayAutoConfiguration")
public class SwaggerAutoConfig {

    private final SwaggerProperties swaggerProperties;

    private final ServiceInstance serviceInstance;
    @Bean
    public OpenAPI springOpenAPI() {
        OpenAPI openAPI = new OpenAPI().info(new Info().title(swaggerProperties.getTitle()));
        // oauth2.0 password
        openAPI.schemaRequirement(HttpHeaders.AUTHORIZATION, this.securityScheme());
        // servers
        List<Server> serverList = new ArrayList<>();
        String path = swaggerProperties.getServices().get(serviceInstance.getServiceId());
        serverList.add(new Server().url(swaggerProperties.getGateway() + "/" + path));
        openAPI.servers(serverList);
        return openAPI;
    }


    private SecurityScheme securityScheme() {
        OAuthFlow clientCredential = new OAuthFlow();
        clientCredential.setTokenUrl(swaggerProperties.getTokenUrl());
        clientCredential.setScopes(new Scopes().addString(swaggerProperties.getScope(), swaggerProperties.getScope()));
        OAuthFlows oauthFlows = new OAuthFlows();
        oauthFlows.password(clientCredential);
        SecurityScheme securityScheme = new SecurityScheme();
        securityScheme.setType(SecurityScheme.Type.OAUTH2);
        securityScheme.setFlows(oauthFlows);
        return securityScheme;
    }

}
