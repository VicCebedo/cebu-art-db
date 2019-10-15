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
    private final boolean artist;

    public UserBuilder(
            final long i,
            final String u,
            final String p,
            final String name,
            final String bio,
            final String website,
            final String email,
            final String phone,
            final String profPic,
            final boolean art) {

        Objects.requireNonNull(i);
        Objects.requireNonNull(u);
        Objects.requireNonNull(p);
        Objects.requireNonNull(name);
        Objects.requireNonNull(bio);
        Objects.requireNonNull(website);
        Objects.requireNonNull(email);
        Objects.requireNonNull(phone);
        Objects.requireNonNull(profPic);
        Objects.requireNonNull(art);

        this.id = i;
        this.username = u;
        this.password = p;
        this.name = name;
        this.bio = bio;
        this.website = website;
        this.email = email;
        this.phone = phone;
        this.profilePic = profPic;
        this.artist = art;
    }

    public User build() {
        return new ImmutableUser(this);
    }

    public static User newInstance() {
        return new UserBuilder(0, "", "", "", "", "", "", "", "", false).build();
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

    public boolean artist() {
        return this.artist;
    }

}
