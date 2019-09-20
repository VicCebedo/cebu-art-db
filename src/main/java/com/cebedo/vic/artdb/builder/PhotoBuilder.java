/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.builder;

import com.cebedo.vic.artdb.model.Photo;
import com.cebedo.vic.artdb.model.User;
import com.cebedo.vic.artdb.model.impl.ImmutablePhoto;
import java.sql.Timestamp;
import java.util.Objects;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
public final class PhotoBuilder {

    private final long id;
    private final String url;
    private final String caption;
    private final Timestamp timestamp;
    private final User user;

    public PhotoBuilder(long i, String u, String c, Timestamp t, User user) {
        Objects.requireNonNull(i);
        Objects.requireNonNull(u);
        Objects.requireNonNull(c);
        Objects.requireNonNull(t);
        Objects.requireNonNull(user);
        this.id = i;
        this.url = u;
        this.caption = c;
        this.timestamp = t;
        this.user = user;
    }

    public Photo build() {
        return new ImmutablePhoto(this);
    }

    public static Photo newInstance() {
        return new ImmutablePhoto(new PhotoBuilder(
                0,
                "",
                "",
                new Timestamp(System.currentTimeMillis()),
                UserBuilder.newInstance()
        ));
    }

    public long id() {
        return this.id;
    }

    public String url() {
        return this.url;
    }

    public String caption() {
        return this.caption;
    }

    public Timestamp timestamp() {
        return this.timestamp;
    }

    public User user() {
        return this.user;
    }

}
