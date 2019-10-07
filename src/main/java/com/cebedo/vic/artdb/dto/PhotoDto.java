/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.dto;

import com.cebedo.vic.artdb.model.Photo;
import com.cebedo.vic.artdb.model.User;
import java.sql.Timestamp;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
public class PhotoDto {

    private long id;
    private String url;

    @NotNull
    @Size(max = 2000, message = "Caption must not be more than 2000 characters.")
    private String caption;

    private String thumbnailCaption;
    private String cloud;
    private String thumbnail;
    private Timestamp timestamp;
    private User user;

    public PhotoDto() {
        ;
    }

    public PhotoDto(Photo p) {
        this.id = p.id();
        this.url = p.url();
        this.caption = p.caption();
        this.thumbnailCaption = p.thumbnailCaption();
        this.cloud = p.cloud();
        this.thumbnail = p.thumbnail();
        this.timestamp = p.timestamp();
        this.user = p.user();
    }

    public String getThumbnailCaption() {
        return thumbnailCaption;
    }

    public void setThumbnailCaption(String thumbnailCaption) {
        this.thumbnailCaption = thumbnailCaption;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
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

}
