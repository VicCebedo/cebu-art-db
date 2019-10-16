/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.dao;

import com.cebedo.vic.artdb.dto.ProfileDto;
import java.util.List;
import com.cebedo.vic.artdb.model.IUser;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
public interface UserDao {

    IUser get(final String username);

    IUser get(final long id);

    void changePassword(final String newPassword);

    void updateProfilePhoto(final String profilePic);

    void create(final IUser user);

    List<IUser> getUsers(final int offset);

    void updateProfileCurrentUser(final ProfileDto user);

    int getInviteCodeRemaining(final String code);

    void decrementInviteCodeRemaining(final String code, final int newRemaining);

}
