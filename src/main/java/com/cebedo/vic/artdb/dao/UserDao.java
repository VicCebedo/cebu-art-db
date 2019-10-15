/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.dao;

import com.cebedo.vic.artdb.dto.ProfileDto;
import com.cebedo.vic.artdb.model.User;
import java.util.List;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
public interface UserDao {

    User get(final String username);

    User get(final long id);

    void changePassword(final String username, final String newPassword);

    void updateProfilePhoto(final String profilePic);

    void create(final User user);

    List<User> getUsers(final int offset);

    void updateProfileCurrentUser(final ProfileDto user);

    int getInviteCodeRemaining(final String code);

    void decrementInviteCodeRemaining(final String code, final int newRemaining);

}
