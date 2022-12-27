package com.aws.basic.configuration;

import com.aws.basic.configuration.handler.JwtAccessDeniedHandler;
import com.aws.basic.configuration.handler.JwtAuthenticationEntryPoint;
import com.aws.basic.configuration.handler.JwtAuthenticationFailureFilter;
import com.azure.spring.aad.webapi.AADResourceServerWebSecurityConfigurerAdapter;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends AADResourceServerWebSecurityConfigurerAdapter {

    private final JwtAccessDeniedHandler accessDeniedHandler;
    private final JwtAuthenticationEntryPoint authenticationEntryPoint;
    private final JwtAuthenticationFailureFilter authenticationFailureFilter;

    public WebSecurityConfig(JwtAccessDeniedHandler accessDeniedHandler, JwtAuthenticationEntryPoint authenticationEntryPoint, JwtAuthenticationFailureFilter authenticationFailureFilter) {
        this.accessDeniedHandler = accessDeniedHandler;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.authenticationFailureFilter = authenticationFailureFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);

        http
                .exceptionHandling()
//                .accessDeniedHandler(accessDeniedHandler) /*** customized access-denied handler **/
//                .authenticationEntryPoint(authenticationEntryPoint) /** before reaching controller, this will validate the authentication first **/
                .and()
                .addFilter(authenticationFailureFilter.bearerTokenAuthenticationFilter(authenticationManagerBean())) /*** customizing the responses if failed authentication **/

                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests(request -> request
                        .mvcMatchers(HttpMethod.GET, "/error-404").permitAll()
                        .mvcMatchers(HttpMethod.GET, "/error").permitAll()
                        .mvcMatchers(HttpMethod.GET, "/error-500").permitAll()
                        .mvcMatchers(HttpMethod.GET, "/**").authenticated()
                        .anyRequest().denyAll())
                .headers()
                .contentSecurityPolicy("default-src 'none'");

    }
}
