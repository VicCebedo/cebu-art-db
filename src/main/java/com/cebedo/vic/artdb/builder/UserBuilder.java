/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.builder;

import com.cebedo.vic.artdb.model.User;
import com.cebedo.vic.artdb.model.impl.ImmutableUser;
import java.util.Objects;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
public final class UserBuilder {

    private final long id;
    private final String username;
    private final String password;
    private final String name;
    private final String bio;
    private final String website;
    private final String email;
    private final String phone;
    private final String profilePic;

    public UserBuilder(long i, String u, String p, String name, String bio, String website, String email, String phone, String profPic) {
        Objects.requireNonNull(i);
        Objects.requireNonNull(u);
        Objects.requireNonNull(p);
        Objects.requireNonNull(name);
        Objects.requireNonNull(bio);
        Objects.requireNonNull(website);
        Objects.requireNonNull(email);
        Objects.requireNonNull(phone);
        this.id = i;
        this.username = u;
        this.password = p;
        this.name = name;
        this.bio = bio;
        this.website = website;
        this.email = email;
        this.phone = phone;
        this.profilePic = profPic;
    }

    public User build() {
        return new ImmutableUser(this);
    }

    public static User newInstance() {
        return new UserBuilder(0, "", "", "", "", "", "", "", "").build();
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

    public String name() {
        return this.name;
    }

    public String bio() {
        return this.bio;
    }

    public String website() {
        return this.website;
    }

    public String email() {
        return this.email;
    }

    public String phone() {
        return this.phone;
    }

    public String profilePic() {
        return this.profilePic;
    }

}
