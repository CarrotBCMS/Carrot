package com.boxedfolder.carrot.config.general;

import com.boxedfolder.carrot.config.UserProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.session.web.http.HttpSessionStrategy;

import javax.inject.Inject;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@Configuration
@EnableWebMvcSecurity
@EnableWebSecurity(debug = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private UserProperties userProperties;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/").permitAll();
        http.authorizeRequests().antMatchers("/sync/ping").permitAll();

        // Define secured routes here
        String[] securedEndpoints = {"/**"};

        for (String endpoint : securedEndpoints) {
            http.authorizeRequests().antMatchers(endpoint).authenticated();
        }

        http.requestCache().requestCache(new NullRequestCache())
                .and()
                .httpBasic();
        http.csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser(userProperties.getUsername())
                .password(userProperties.getPassword())
                .roles("USER");
    }

    @Bean
    public HttpSessionStrategy httpSessionStrategy() {
        return new HeaderHttpSessionStrategy();
    }

    @Inject
    public void setUserProperties(UserProperties userProperties) {
        this.userProperties = userProperties;
    }
}
