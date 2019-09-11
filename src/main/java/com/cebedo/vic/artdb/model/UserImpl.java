/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.model;

import com.cebedo.vic.artdb.builder.UserBuilder;
import java.util.Objects;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
public final class UserImpl implements User {

    private final long id;
    private final String username;
    private final String password;

    public UserImpl(UserBuilder b) {
        Objects.requireNonNull(b);
        this.id = b.id();
        this.username = b.username();
        this.password = b.password();
    }

    @Override
    public long id() {
        return this.id;
    }

    @Override
    public String username() {
        return this.username;
    }

    @Override
    public String password() {
        return this.password;
    }

}
