/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.dto;

import com.cebedo.vic.artdb.model.User;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
public class ProfileDto {

    @NotNull
    @Size(max = 30, message = "Name must not be more than 30 characters.")
    private String name;

    @NotNull
    @Size(max = 2000, message = "Artist bio must not be more than 2000 characters.")
    private String bio;

    @NotNull
    @URL(message = "Please provide a valid website link.")
    @Size(max = 150, message = "Website must not be more than 150 characters.")
    private String website;

    @NotNull
    @Email(message = "Please provide a valid email address.")
    @Size(max = 50, message = "Email must not be more than 50 characters.")
    private String email;

    @NotNull
    @Size(max = 20, message = "Phone number must not be more than 20 characters.")
    private String phone;

    @Size(max = 150)
    private String profilePic;

    public ProfileDto() {
        ;
    }

    public ProfileDto(User user) {
        this.name = user.name();
        this.bio = user.bio();
        this.website = user.website();
        this.email = user.email();
        this.phone = user.phone();
        this.profilePic = user.profilePic();
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

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }
}
