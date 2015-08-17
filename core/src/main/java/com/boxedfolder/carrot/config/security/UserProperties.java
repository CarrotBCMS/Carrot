package com.boxedfolder.carrot.config.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

/**
 * Load username and password from configuration.
 *
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@Component
@ConfigurationProperties(prefix = "auth")
public class UserProperties {
    private String username = "admin";
    private String password = "carrot";

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
