package com.fhdufhdu.mim.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fhdufhdu.mim.exception.ExceptionResponse;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Component
public class ExceptionHandler implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            setErrorResponse((HttpServletResponse) response, e);
        }
    }

    private void setErrorResponse(HttpServletResponse response, Throwable ex) {
        ObjectMapper mapper = new ObjectMapper();
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), HttpStatus.BAD_REQUEST.value(),
                ex.getMessage());
        try {
            String json = mapper.writeValueAsString(exceptionResponse);
            response.getWriter().write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
