package com.boxedfolder.carrot.config.general.filter;

import com.boxedfolder.carrot.config.Profiles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Adds specific headers to every response to allow cross-origin resource sharing. Only
 * available in develop mode.
 *
 * With Spring Boot 1.3.0 this could be activated via annotation.
 *
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@Profile(Profiles.DEVELOP)
@Component
public class CORSFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
            ServletException {
        HttpServletResponse response = (HttpServletResponse)res;
        HttpServletRequest request = (HttpServletRequest)req;

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET,  DELETE, PUT");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Accept, Content-Type, Origin, Authorization, x-auth-token");
        response.addHeader("Access-Control-Expose-Headers", "x-auth-token");

        if (request.getMethod().equals("OPTIONS")) {
            try {
                response.getWriter().print("OK");
                response.getWriter().flush();
            } catch (IOException e) {
                Logger log = LoggerFactory.getLogger(getClass());
                log.error("Exception thrown: " + e.getMessage());
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }
}
