/*
 * Carrot - beacon management
 * Copyright (C) 2015 Heiko Dreyer
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.boxedfolder.carrot.config.security.xauth;

import com.boxedfolder.carrot.config.security.AuthenticationHelper;
import com.boxedfolder.carrot.config.security.filter.XAuthTokenFilter;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author Philip W. Sorst (philip@sorst.net)
 * @author Josh Long (josh@joshlong.com)
 *         <p/>
 *         Modified by:
 * @author Heiko Dreyer
 */
public class XAuthTokenConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private UserDetailsService detailsService;
    private AuthenticationHelper authenticationHelper;

    public XAuthTokenConfigurer(UserDetailsService detailsService, AuthenticationHelper authenticationHelper) {
        this.detailsService = detailsService;
        this.authenticationHelper = authenticationHelper;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        XAuthTokenFilter customFilter = new XAuthTokenFilter(detailsService, authenticationHelper);
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
