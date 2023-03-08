package com.github.marcosws.vehicle.infra.security.jwt.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.github.marcosws.vehicle.infra.security.jwt.ServletUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class UnauthorizedHandler implements AuthenticationEntryPoint {
    private static Logger logger = LoggerFactory.getLogger(UnauthorizedHandler.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
    	
        logger.warn("UnauthorizedHandler, exception: " + authException);
        String json = ServletUtil.getJson("error", "Não autorizado.");
        ServletUtil.write(response, HttpStatus.FORBIDDEN, json);
        
    }
}
