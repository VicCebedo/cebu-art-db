/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.config;

import com.cebedo.vic.artdb.model.impl.User;
import java.util.Collection;
import java.util.Objects;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import com.cebedo.vic.artdb.model.IUser;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
public final class WebAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private final IUser user;
    private final User profile;

    public WebAuthenticationToken(
            final Object principal,
            final Object credentials,
            final Collection<? extends GrantedAuthority> authorities,
            final IUser user) {
        super(principal, credentials, authorities);

        Objects.requireNonNull(principal);
        Objects.requireNonNull(credentials);
        Objects.requireNonNull(authorities);
        Objects.requireNonNull(user);
        this.user = user;
        this.profile = new User(user);
    }

    public IUser user() {
        return this.user;
    }

    public User profile() {
        return this.profile;
    }

}
