package com.revan;

import org.springframework.web.filter.GenericFilterBean;
import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CORSFilter extends GenericFilterBean implements Filter {

    // A filter that restricts the kind of methods that can be used
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setHeader("Access-Control-Allow-Origin", "http://localhost:8081");
        httpResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");

        httpResponse.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, X-Auth-Token, X-Csrf-Token, Authorization");

        httpResponse.setHeader("Access-Control-Allow-Credentials", "false");
        httpResponse.setHeader("Access-Control-Max-Age", "3600");

        System.out.println("********** CORS Configuration Completed **********");

        chain.doFilter(request, response);
    }


}