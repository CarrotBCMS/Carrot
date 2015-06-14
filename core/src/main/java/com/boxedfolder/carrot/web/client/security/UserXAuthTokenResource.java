package com.boxedfolder.carrot.web.client.security;

import com.boxedfolder.carrot.config.security.xauth.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This generates the token that must be present in subsequent REST
 * invocations.
 *
 * @author Philip W. Sorst (philip@sorst.net)
 * @author Josh Long (josh@joshlong.com)
 *         <p/>
 *         Modified by:
 * @author bfolder
 */
@RestController
public class UserXAuthTokenResource {
    private final TokenUtils tokenUtils = new TokenUtils();
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    @Autowired
    public UserXAuthTokenResource(AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    @RequestMapping(value = "/client/authenticate", method = {RequestMethod.POST})
    public UserTransfer authorize(@RequestParam String username, @RequestParam String password) {
        System.out.println("BLA: " + username + password);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails details = userDetailsService.loadUserByUsername(username);
        Map<String, Boolean> roles = new HashMap<String, Boolean>();
        for (GrantedAuthority authority : details.getAuthorities()) {
            roles.put(authority.toString(), Boolean.TRUE);
        }

        return new UserTransfer(details.getUsername(), roles, tokenUtils.createToken(details));
    }

    /**
     * Class which wraps user data into a json compatible transfer object.
     */
    public static class UserTransfer {
        private final String name;
        private final Map<String, Boolean> roles;
        private final String token;

        public UserTransfer(String userName, Map<String, Boolean> roles, String token) {
            Map<String, Boolean> mapOfRoles = new ConcurrentHashMap<String, Boolean>();
            for (String key : roles.keySet()) {
                mapOfRoles.put(key, roles.get(key));
            }

            this.roles = mapOfRoles;
            this.token = token;
            this.name = userName;
        }

        public String getName() {
            return this.name;
        }

        public Map<String, Boolean> getRoles() {
            return this.roles;
        }

        public String getToken() {
            return this.token;
        }
    }
}
