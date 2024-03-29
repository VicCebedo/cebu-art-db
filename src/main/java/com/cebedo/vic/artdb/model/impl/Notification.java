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
    private String referenceId;
    private ActionEnum action;
    private long userId;
    private String username;
    private long photoId;
    private String thumbnail;
    private String content;
    private boolean unread;
    private Date datetime;
    private long ownerId;
    private String dateDisplay;

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

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

    public String getDateDisplay() {
        return dateDisplay;
    }

    public void setDateDisplay(String dateDisplay) {
        this.dateDisplay = dateDisplay;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    @Override
    public long ownerId() {
        return this.ownerId;
    }

    @Override
    public String dateDisplay() {
        return this.dateDisplay;
    }

    @Override
    public String content() {
        return this.content;
    }

    @Override
    public String referenceId() {
        return this.referenceId;
    }

    @Override
    public String thumbnail() {
        return this.thumbnail;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

}
