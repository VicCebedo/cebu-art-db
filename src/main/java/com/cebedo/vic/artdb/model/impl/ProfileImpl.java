/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.model.impl;

import com.cebedo.vic.artdb.model.Profile;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
public class ProfileImpl implements Profile {

    private String name;
    private String bio;
    private String website;
    private String email;
    private String phone;

    public ProfileImpl(String n, String b, String w, String e, String p) {
        this.name = n;
        this.bio = b;
        this.website = w;
        this.email = e;
        this.phone = p;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getBio() {
        return bio;
    }

    @Override
    public void setBio(String bio) {
        this.bio = bio;
    }

    @Override
    public String getWebsite() {
        return website;
    }

    @Override
    public void setWebsite(String website) {
        this.website = website;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPhone() {
        return phone;
    }

    @Override
    public void setPhone(String phone) {
        this.phone = phone;
    }

}
