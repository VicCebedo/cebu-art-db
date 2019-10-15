/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.model.impl;

import com.cebedo.vic.artdb.builder.UserBuilder;
import com.cebedo.vic.artdb.model.User;
import java.util.Objects;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
public final class ImmutableUser implements User {

    private final long id;
    private final String username;
    private final String password;
    private final String name;
    private final String bio;
    private final String website;
    private final String email;
    private final String phone;
    private final String profilePic;
    private final boolean artist;

    public ImmutableUser(final UserBuilder b) {
        Objects.requireNonNull(b);
        this.id = b.id();
        this.username = b.username();
        this.password = b.password();
        this.name = b.name();
        this.bio = b.bio();
        this.website = b.website();
        this.email = b.email();
        this.phone = b.phone();
        this.profilePic = b.profilePic();
        this.artist = b.artist();
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

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public String bio() {
        return this.bio;
    }

    @Override
    public String website() {
        return this.website;
    }

    @Override
    public String email() {
        return this.email;
    }

    @Override
    public String phone() {
        return this.phone;
    }

    @Override
    public String profilePic() {
        return this.profilePic;
    }

    @Override
    public boolean artist() {
        return this.artist;
    }

}
