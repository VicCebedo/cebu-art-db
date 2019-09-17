/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.builder;

import com.cebedo.vic.artdb.model.Photo;
import com.cebedo.vic.artdb.model.impl.PhotoImpl;
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

    public PhotoBuilder(long i, long uId, String u, String c) {
        Objects.requireNonNull(i);
        Objects.requireNonNull(uId);
        Objects.requireNonNull(u);
        Objects.requireNonNull(c);
        this.id = i;
        this.userId = uId;
        this.url = u;
        this.caption = c;
    }

    public Photo build() {
        return new PhotoImpl(this);
    }

    public static Photo buildNewInstance() {
        return new PhotoImpl(new PhotoBuilder(0, 0, "", ""));
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

}
