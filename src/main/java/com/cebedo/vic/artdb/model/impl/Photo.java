/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.model.impl;

import com.cebedo.vic.artdb.builder.PhotoBuilder;
import java.sql.Timestamp;
import java.util.Objects;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.cebedo.vic.artdb.model.IPhoto;
import com.cebedo.vic.artdb.model.IUser;
import java.util.List;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
public class Photo implements IPhoto {

    private long id;
    private String url;

    @NotNull
    @Size(max = 2000, message = "Caption must not be more than 2000 characters.")
    private String caption;

    private String cloud;
    private Timestamp timestamp;
    private IUser user;
    private long commentCount;
    private long likeCount;
    private boolean liked;
    private List<Comment> comments;

    public Photo() {
        ;
    }

    public Photo(PhotoBuilder b) {
        Objects.requireNonNull(b);
        this.id = b.id();
        this.url = b.url();
        this.caption = b.caption();
        this.cloud = this.url.isEmpty()
                ? ""
                : this.url.substring(this.url.lastIndexOf('/') + 1, this.url.lastIndexOf('.'));
        this.timestamp = b.timestamp();
        this.user = b.user();
        this.commentCount = b.commentCount();
        this.likeCount = b.likeCount();
        this.liked = b.liked();
        this.comments = b.comments();
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(long likeCount) {
        this.likeCount = likeCount;
    }

    public long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(long commentCount) {
        this.commentCount = commentCount;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public IUser getUser() {
        return user;
    }

    public void setUser(IUser user) {
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCloud() {
        return cloud;
    }

    public void setCloud(String cloud) {
        this.cloud = cloud;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
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
    public Timestamp timestamp() {
        return this.timestamp;
    }

    @Override
    public IUser user() {
        return this.user;
    }

    @Override
    public long commentCount() {
        return this.id;
    }

    @Override
    public long likeCount() {
        return this.id;
    }

    @Override
    public boolean liked() {
        return this.liked;
    }

    @Override
    public List<Comment> comments() {
        return this.comments;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

}
