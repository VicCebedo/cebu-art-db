/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.dto;

import javax.validation.constraints.Size;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
public class CredentialsDto {

    @Size(min = 4, max = 30, message = "Username must be at least 4 characters long, but not more than 30.")
    private String username;

    @Size(min = 6, message = "Password length must be at least 6 digits.")
    private String password;

    @Size(min = 6, message = "New password length must be at least 6 digits.")
    private String newPassword;

    private String newPasswordRetype;
    private String inviteCode;

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPasswordRetype() {
        return newPasswordRetype;
    }

    public void setNewPasswordRetype(String newPasswordRetype) {
        this.newPasswordRetype = newPasswordRetype;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
