/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.config;

import com.cebedo.vic.artdb.model.User;
import java.util.Collection;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
public final class WebAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private final User user;

    public WebAuthenticationToken(
            Object principal,
            Object credentials,
            Collection<? extends GrantedAuthority> authorities,
            User user) {
        super(principal, credentials, authorities);
        this.user = user;
    }

    public User user() {
        return this.user;
    }

}
