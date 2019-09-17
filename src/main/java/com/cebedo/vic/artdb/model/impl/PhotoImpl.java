/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.model.impl;

import com.cebedo.vic.artdb.builder.PhotoBuilder;
import com.cebedo.vic.artdb.model.Photo;
import java.util.Objects;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
public final class PhotoImpl implements Photo {

    private final long id;
    private final long userId;
    private final String url;
    private final String caption;

    public PhotoImpl(PhotoBuilder b) {
        Objects.requireNonNull(b);
        this.id = b.id();
        this.userId = b.userId();
        this.url = b.url();
        this.caption = b.caption();
    }

    @Override
    public long id() {
        return this.id;
    }

    @Override
    public long userId() {
        return this.userId;
    }

    @Override
    public String url() {
        return this.url;
    }

    @Override
    public String caption() {
        return this.caption;
    }

}
