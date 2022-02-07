package com.devthink.devthink_server.filters;

import com.devthink.devthink_server.application.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {
    private final AuthenticationService authenticationService;

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {
        String authorization = request.getHeader("Authorization");

        String accessToken = authorization.substring("Bearer ".length());
        authenticationService.parseToken(accessToken);
        
        return true;
    }
}
