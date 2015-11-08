package com.boxedfolder.carrot.config.security;

import com.boxedfolder.carrot.config.Profiles;
import com.boxedfolder.carrot.config.security.service.UserDetailService;
import com.boxedfolder.carrot.config.security.xauth.XAuthTokenConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;

import javax.inject.Inject;

/**
 * This security config is not using spring session to store session data in redis or something.
 * A stateless session with a custom x-auth-filter had been used instead.
 *
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@Configuration
@EnableWebMvcSecurity
@EnableWebSecurity(debug = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private UserProperties userProperties;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/client/analytics/logs/**").permitAll();

        // Define secured routes here
        String[] securedEndpoints = {
                "/client/ping",
                "/client/beacons/**",
                "/client/apps/**",
                "/client/events/**",
                "/client/analytics/**"
        };

        for (String endpoint : securedEndpoints) {
            http.authorizeRequests().antMatchers(endpoint).authenticated();
        }

        SecurityConfigurer<DefaultSecurityFilterChain, HttpSecurity> securityConfigurerAdapter =
                new XAuthTokenConfigurer(userDetailsServiceBean());
        http.apply(securityConfigurerAdapter);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authManagerBuilder) throws Exception {
        authManagerBuilder.userDetailsService(new UserDetailService(userProperties));
    }


    @Bean
    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return super.userDetailsServiceBean();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Inject
    public void setUserProperties(UserProperties userProperties) {
        this.userProperties = userProperties;
    }
}
