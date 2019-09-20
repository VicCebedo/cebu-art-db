/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.dto;

import com.cebedo.vic.artdb.model.User;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
public class ProfileDTO {

    private String name;
    private String bio;
    private String website;
    private String email;
    private String phone;

    public ProfileDTO() {
        ;
    }

    public ProfileDTO(User user) {
        this.name = user.name();
        this.bio = user.bio();
        this.website = user.website();
        this.email = user.email();
        this.phone = user.phone();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
