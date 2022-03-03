package com.devthink.devthink_server.filters;


import com.devthink.devthink_server.errors.InvalidTokenException;
import org.springframework.http.HttpStatus;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationErrorFilter extends HttpFilter {
    protected void doFilter(HttpServletRequest request, HttpServletResponse response,
                            FilterChain chain)
            throws IOException, ServletException {
        try {
            chain.doFilter(request,response);
        } catch(InvalidTokenException exception) {
           response.sendError(HttpStatus.UNAUTHORIZED.value());
        }

    }
}
