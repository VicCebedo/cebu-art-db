/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.model.impl;

import com.cebedo.vic.artdb.builder.PhotoBuilder;
import com.cebedo.vic.artdb.model.Photo;
import java.sql.Timestamp;
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
    private final String cloudName;
    private final String thumbnailUrl;
    private final Timestamp timestamp;
    private final String username;
    private final String userProfileName;

    public PhotoImpl(PhotoBuilder b) {
        Objects.requireNonNull(b);
        this.id = b.id();
        this.caption = b.caption();
        this.cloudName = b.url().substring(b.url().lastIndexOf('/') + 1, b.url().lastIndexOf('.'));
        this.timestamp = b.timestamp();

        // Photo manipulations.
        String[] urlSplit = b.url().split("/upload/");
        this.thumbnailUrl = urlSplit[0] + "/upload/w_400,h_300,c_fill,g_auto,q_auto:best/" + urlSplit[1];
        this.url = b.url();

        // Uploader data.
        this.userId = b.userId();
        this.username = b.username();
        this.userProfileName = b.userProfileName();
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

    @Override
    public String cloudName() {
        return this.cloudName;
    }

    @Override
    public String thumbnailUrl() {
        return this.thumbnailUrl;
    }

    @Override
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
