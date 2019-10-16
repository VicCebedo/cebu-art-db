/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.model.impl;

import com.cebedo.vic.artdb.model.ILike;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
@Document(collection = "like")
public class Like implements ILike {

    private String id;
    private long photoId;
    private long userId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getPhotoId() {
        return photoId;
    }

    public void setPhotoId(long photoId) {
        this.photoId = photoId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public String id() {
        return this.id;
    }

    @Override
    public long photoId() {
        return this.photoId;
    }

    @Override
    public long userId() {
        return this.userId;
    }

}
