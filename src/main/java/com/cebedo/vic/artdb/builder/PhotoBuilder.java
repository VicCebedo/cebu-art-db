/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.builder;

import com.cebedo.vic.artdb.model.Photo;
import com.cebedo.vic.artdb.model.User;
import com.cebedo.vic.artdb.model.impl.PhotoImpl;
import java.sql.Timestamp;
import java.util.Objects;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
public final class PhotoBuilder {

    private final long id;
    private final long userId;
    private final String url;
    private final String caption;
    private final Timestamp timestamp;
    private final String username;
    private final String userProfileName;

    public PhotoBuilder(long i, String u, String c, Timestamp t, User user) {
        Objects.requireNonNull(i);
        Objects.requireNonNull(u);
        Objects.requireNonNull(c);
        Objects.requireNonNull(t);
        Objects.requireNonNull(user);
        this.id = i;
        this.userId = user.id();
        this.url = u;
        this.caption = c;
        this.timestamp = t;
        this.username = user.username();
        this.userProfileName = (user.profile() == null || user.profile().getName() == null)
                ? ""
                : user.profile().getName();
    }

    public PhotoBuilder(long i, long uId, String u, String c, Timestamp t) {
        Objects.requireNonNull(i);
        Objects.requireNonNull(uId);
        Objects.requireNonNull(u);
        Objects.requireNonNull(c);
        Objects.requireNonNull(t);
        this.id = i;
        this.userId = uId;
        this.url = u;
        this.caption = c;
        this.timestamp = t;
        this.username = "";
        this.userProfileName = "";
    }

    public Photo build() {
        return new PhotoImpl(this);
    }

    public static Photo buildNewInstance() {
        return new PhotoImpl(new PhotoBuilder(
                0,
                0,
                "",
                "",
                new Timestamp(System.currentTimeMillis())
        ));
    }

    public long id() {
        return this.id;
    }

    public long userId() {
        return this.userId;
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

    public String username() {
        return this.username;
    }

    public String userProfileName() {
        return this.userProfileName;
    }

}
