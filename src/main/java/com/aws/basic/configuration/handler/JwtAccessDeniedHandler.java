package com.aws.basic.configuration.handler;

import com.aws.basic.configuration.response.AccessDenied;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;

import static com.aws.basic.util.ResponseWriterUtil.writeErrorResponse;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    private static final Logger LOGGER = Logger.getLogger(JwtAccessDeniedHandler.class.getName());

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) {

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        writeErrorResponse(response, new AccessDenied()); //Parse the response class to the request's response body
        LOGGER.severe(String.format("Access from %s denied. ", request.getRemoteAddr()) + accessDeniedException.getMessage());

    }
}
