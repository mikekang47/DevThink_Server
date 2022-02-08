package com.devthink.devthink_server.filters;

import com.devthink.devthink_server.application.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {
    private final AuthenticationService authenticationService;

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {
        
        return filterWithPathAndMethod(request) || doAuthentication(request, response);
    }

    private boolean filterWithPathAndMethod(HttpServletRequest request) {
        String path = request.getRequestURI();
        if(!path.equals("/posts")) {
            return true;
        }
        String method = request.getMethod();
        if(method.equals("GET")) {
            return true;
        }
        return false;
    }

    private boolean doAuthentication(HttpServletRequest request,
                                     HttpServletResponse response)
            throws IOException {
        String authorization = request.getHeader("Authorization");

        if(authorization == null) {
            response.sendError(HttpStatus.UNAUTHORIZED.value());
            return false;
        }

        String accessToken  = authorization.substring("Bearer ".length());
        authenticationService.parseToken(accessToken);

        return true;
    }
}
