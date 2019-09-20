/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.model.impl;

import com.cebedo.vic.artdb.model.User;
import java.util.Objects;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
public class MutableUser implements User {

    private long id;
    private String username;
    private String password;
    private String name;
    private String bio;
    private String website;
    private String email;
    private String phone;

    public MutableUser(User usr) {
        Objects.requireNonNull(usr);
        this.id = usr.id();
        this.username = usr.username();
        this.password = usr.password();
        this.name = usr.name();
        this.bio = usr.bio();
        this.website = usr.website();
        this.email = usr.email();
        this.phone = usr.phone();
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

}
