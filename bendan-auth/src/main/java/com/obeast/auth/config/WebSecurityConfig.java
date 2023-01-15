package com.obeast.auth.config;


import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author Joe Grandja
 * @since 0.1.0
 */
// TODO: 2023/1/14 关闭debug
@EnableWebSecurity(debug = true)
public class WebSecurityConfig {


    /**
     * Description: 暴露静态资源
     * @author wxl
     * Date: 2022/12/3 20:53
     * @param http http
     * @return org.springframework.security.web.SecurityFilterChain
     */
    @Bean
    @Order(0)
    SecurityFilterChain resources(HttpSecurity http) throws Exception {
        http.requestMatchers((matchers) -> matchers.antMatchers("/actuator/**", "/css/**","/assets/**", "/webjars/**",  "/error"))
                .authorizeHttpRequests((authorize) -> authorize.anyRequest().permitAll()).requestCache().disable()
                .securityContext().disable().sessionManagement().disable();
        return http.build();
    }
}
