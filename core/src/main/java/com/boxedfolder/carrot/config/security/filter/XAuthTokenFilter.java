package com.boxedfolder.carrot.config.security.filter;

import com.boxedfolder.carrot.config.security.xauth.TokenUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Sifts through all incoming requests and installs a Spring Security principal
 * if a header corresponding to a valid user is found.
 *
 * @author Philip W. Sorst (philip@sorst.net)
 * @author Josh Long (josh@joshlong.com)
 *         <p/>
 *         Modified by:
 * @author Heiko Dreyer
 */
public class XAuthTokenFilter extends GenericFilterBean {
    private final UserDetailsService detailsService;
    private final TokenUtils tokenUtils = new TokenUtils();

    public XAuthTokenFilter(UserDetailsService userDetailsService) {
        this.detailsService = userDetailsService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest)request;
            String authToken = httpServletRequest.getHeader("x-auth-token");

            if (StringUtils.hasText(authToken)) {
                String username = tokenUtils.getUserNameFromToken(authToken);
                UserDetails details = detailsService.loadUserByUsername(username);

                if (tokenUtils.validateToken(authToken, details)) {
                    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(details, details.getPassword(), details.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(token);
                }
            }
            filterChain.doFilter(request, response);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

}