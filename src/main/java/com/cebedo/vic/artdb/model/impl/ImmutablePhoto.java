/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.model.impl;

import com.cebedo.vic.artdb.builder.PhotoBuilder;
import com.cebedo.vic.artdb.model.Photo;
import com.cebedo.vic.artdb.model.User;
import java.sql.Timestamp;
import java.util.Objects;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
public final class ImmutablePhoto implements Photo {

    private final long id;
    private final String url;
    private final String caption;
    private final String cloud;
    private final String thumbnail;
    private final Timestamp timestamp;
    private final User user;

    public ImmutablePhoto(PhotoBuilder b) {
        Objects.requireNonNull(b);
        this.id = b.id();
        this.url = b.url();
        this.caption = b.caption();
        this.cloud = this.url.substring(this.url.lastIndexOf('/') + 1, this.url.lastIndexOf('.'));
        this.timestamp = b.timestamp();
        this.user = b.user();

        // Photo manipulations.
        // TODO (Beta) Thumbnail pre-manipulation.
        String[] urlSplit = this.url.split("/upload/");
        this.thumbnail = urlSplit[0] + "/upload/w_400,h_300,c_fill,g_auto/" + urlSplit[1];
    }

    @Override
    public long id() {
        return this.id;
    }

    @Override
    public String url() {
        return this.url;
    }

    @Override
    public String caption() {
        return this.caption;
    }

    @Override
    public String cloud() {
        return this.cloud;
    }

    @Override
    public String thumbnail() {
        return this.thumbnail;
    }

    @Override
    public Timestamp timestamp() {
        return this.timestamp;
    }

    @Override
    public User user() {
        return this.user;
    }
}
