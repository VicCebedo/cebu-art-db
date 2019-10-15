/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.config;

import com.cebedo.vic.artdb.model.User;
import com.cebedo.vic.artdb.service.UserService;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
@Component
public class WebAuthenticationProvider implements AuthenticationProvider {

    private static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();

    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(final Authentication auth) throws AuthenticationException {

        // Raw data from request.
        String username = auth.getName().toLowerCase();
        String rawPassword = auth.getCredentials().toString();

        // Get user object based on username,
        // then compare if true.
        User user = this.userService.get(username);
        if (ENCODER.matches(rawPassword, user.password())) {
            return new WebAuthenticationToken(username, "", new ArrayList<>(), user);
        }
        return null;

    }

    @Override
    public boolean supports(Class<?> type) {
        return type.equals(UsernamePasswordAuthenticationToken.class);
    }
}
