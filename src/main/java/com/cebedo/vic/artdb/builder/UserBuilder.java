/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.builder;

import com.cebedo.vic.artdb.model.User;
import com.cebedo.vic.artdb.model.UserImpl;
import java.util.Objects;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
public final class UserBuilder {

    private final long id;
    private final String username;
    private final String password;

    public UserBuilder(long i, String u, String p) {
        Objects.requireNonNull(i);
        Objects.requireNonNull(u);
        Objects.requireNonNull(p);
        this.id = i;
        this.username = u;
        this.password = p;
    }

    public User build() {
        return new UserImpl(this);
    }

    public long id() {
        return this.id;
    }

    public String username() {
        return this.username;
    }

    public String password() {
        return this.password;
    }

}
