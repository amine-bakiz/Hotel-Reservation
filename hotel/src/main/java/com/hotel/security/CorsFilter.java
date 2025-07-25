package com.hotel.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CorsFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String originHeader = request.getHeader("origin");
        response.setHeader("Access-Control-Allow-Origin",originHeader);
        response.setHeader("Access-Control-Allow-Methods","POST,GET,PUT,DELETE,OPTIONS");
        response.setHeader("Access-Control-Max-Age","3600");
        response.setHeader("Access-Control-Allow-Headers","*");

        if("OPTIONS".equalsIgnoreCase(request.getMethod())){
            response.setStatus(HttpServletResponse.SC_OK);
        }else{
            filterChain.doFilter(servletRequest, servletResponse);
        }


    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
