/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.model.impl;

import com.cebedo.vic.artdb.constants.ActionEnum;
import com.cebedo.vic.artdb.model.INotification;
import java.util.Date;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
@Document(collection = "notification")
public class Notification implements INotification {

    private String id;
    private ActionEnum action;
    private long userId;
    private String username;
    private long photoId;
    private boolean unread;
    private Date datetime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ActionEnum getAction() {
        return action;
    }

    public void setAction(ActionEnum action) {
        this.action = action;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getPhotoId() {
        return photoId;
    }

    public void setPhotoId(long photoId) {
        this.photoId = photoId;
    }

    public boolean isUnread() {
        return unread;
    }

    public void setUnread(boolean unread) {
        this.unread = unread;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    @Override
    public String id() {
        return this.id;
    }

    @Override
    public ActionEnum action() {
        return this.action;
    }

    @Override
    public long userId() {
        return this.userId;
    }

    @Override
    public String username() {
        return this.username;
    }

    @Override
    public long photoId() {
        return this.photoId;
    }

    @Override
    public boolean unread() {
        return this.unread;
    }

    @Override
    public Date datetime() {
        return this.datetime;
    }

}
