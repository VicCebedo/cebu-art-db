/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.model.impl;

import com.cebedo.vic.artdb.builder.UserBuilder;
import java.util.Objects;
import com.cebedo.vic.artdb.model.IUser;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
public class User implements IUser {

    private long id;
    private String username;
    private String password;
    private String name;
    private String bio;
    private String website;
    private String email;
    private String phone;
    private String profilePic;
    private boolean artist;
    private int catchCount;

    public User() {
        ;
    }

    public User(final UserBuilder b) {
        Objects.requireNonNull(b);
        this.id = b.id();
        this.username = b.username();
        this.password = b.password();
        this.name = b.name();
        this.bio = b.bio();
        this.website = b.website();
        this.email = b.email();
        this.phone = b.phone();
        this.profilePic = b.profilePic();
        this.artist = b.artist();
    }

    public boolean isArtist() {
        return artist;
    }

    public void setArtist(boolean artist) {
        this.artist = artist;
    }

    @Override
    public long id() {
        return id;
    }

    @Override
    public String username() {
        return username;
    }

    @Override
    public String password() {
        return password;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String bio() {
        return bio;
    }

    @Override
    public String website() {
        return website;
    }

    @Override
    public String email() {
        return email;
    }

    @Override
    public String phone() {
        return phone;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String profilePic() {
        return this.profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getBio() {
        return bio;
    }

    public String getWebsite() {
        return website;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getProfilePic() {
        return profilePic;
    }

    @Override
    public boolean artist() {
        return this.artist;
    }

    public int getCatchCount() {
        return catchCount;
    }

    public void setCatchCount(int catchCount) {
        this.catchCount = catchCount;
    }

    @Override
    public int catchCount() {
        return this.catchCount;
    }

}
