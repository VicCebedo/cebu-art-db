/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.model.impl;

import com.cebedo.vic.artdb.model.ICatch;
import java.util.Date;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
@Document(collection = "catch")
public class Catch implements ICatch {

    private String id;
    private long targetId;
    private long followerId;
    private String targetUsername;
    private String followerUsername;
    private Date datetime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getTargetId() {
        return targetId;
    }

    public void setTargetId(long targetId) {
        this.targetId = targetId;
    }

    public long getFollowerId() {
        return followerId;
    }

    public void setFollowerId(long followerId) {
        this.followerId = followerId;
    }

    public String getTargetUsername() {
        return targetUsername;
    }

    public void setTargetUsername(String targetUsername) {
        this.targetUsername = targetUsername;
    }

    public String getFollowerUsername() {
        return followerUsername;
    }

    public void setFollowerUsername(String followerUsername) {
        this.followerUsername = followerUsername;
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
    public long targetId() {
        return this.targetId;
    }

    @Override
    public long followerId() {
        return this.followerId;
    }

    @Override
    public String targetUsername() {
        return this.targetUsername;
    }

    @Override
    public String followerUsername() {
        return this.followerUsername;
    }

    @Override
    public Date datetime() {
        return this.datetime;
    }

}
