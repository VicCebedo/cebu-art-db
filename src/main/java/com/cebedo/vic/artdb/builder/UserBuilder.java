/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.builder;

import com.cebedo.vic.artdb.model.Profile;
import com.cebedo.vic.artdb.model.User;
import com.cebedo.vic.artdb.model.impl.UserImpl;
import java.util.Objects;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
public final class UserBuilder {

    private final long id;
    private final String username;
    private final String password;
    private final Profile profile;

    public UserBuilder(long i, String u, String p) {
        Objects.requireNonNull(i);
        Objects.requireNonNull(u);
        Objects.requireNonNull(p);
        this.id = i;
        this.username = u;
        this.password = p;
        this.profile = null;
    }

    public UserBuilder(long i, String u, String p, Profile prof) {
        Objects.requireNonNull(i);
        Objects.requireNonNull(u);
        Objects.requireNonNull(p);
        Objects.requireNonNull(prof);
        this.id = i;
        this.username = u;
        this.password = p;
        this.profile = prof;
    }

    public User build() {
        return new UserImpl(this);
    }

    public static User buildNewInstance() {
        return new UserBuilder(0, "", "").build();
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

    public Profile profile() {
        return this.profile;
    }

}
