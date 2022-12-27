package com.aws.basic.configuration.handler;


import com.aws.basic.configuration.response.AccessDenied;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

import static com.aws.basic.util.ResponseWriterUtil.writeErrorResponse;

@Component
public class JwtAuthenticationFailureFilter {

    private static final Logger LOGGER = Logger.getLogger(JwtAuthenticationFailureFilter.class.getName());

    public BearerTokenAuthenticationFilter bearerTokenAuthenticationFilter(AuthenticationManager authenticationManager) {
        LOGGER.info("Checking request ...");
        BearerTokenAuthenticationFilter filter = new BearerTokenAuthenticationFilter(authenticationManager);
        filter.setAuthenticationFailureHandler(authenticationFailureHandler());
        return filter;
    }

    private AuthenticationFailureHandler authenticationFailureHandler() {

        return (((request, response, exception) -> {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            if (exception.getMessage().contains("Jwt expired")) {
                //Provide your customized message here

                writeErrorResponse(response, new AccessDenied().message("Token provided is expired."));
                LOGGER.severe(String.format("Access from %s denied. Token expired. ", request.getRemoteAddr()) + exception.getMessage());
            } else {
                //Provide your message here
                writeErrorResponse(response, new AccessDenied().message("Token provided is invalid or malformed."));
                LOGGER.severe(String.format("Access from %s denied. Token invalid. ", request.getRemoteAddr()) + exception.getMessage());
            }

        }));

    }

}
