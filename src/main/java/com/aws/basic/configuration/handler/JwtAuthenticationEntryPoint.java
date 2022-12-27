package com.aws.basic.configuration.handler;

import com.aws.basic.configuration.response.AccessDenied;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

import static com.aws.basic.util.ResponseWriterUtil.writeErrorResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final Logger LOGGER = Logger.getLogger(JwtAuthenticationEntryPoint.class.getName());

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) {

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        writeErrorResponse(response, new AccessDenied());
        LOGGER.severe(String.format("Access from %s denied. ", request.getRemoteAddr()) + authException.getMessage());
    }

}
