/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.dao;

import com.cebedo.vic.artdb.dto.ProfileDTO;
import com.cebedo.vic.artdb.model.User;
import java.util.List;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
public interface UserDao {

    User get(String username);

    User get(long id);

    void changePassword(String username, String newPassword);

    void updateProfilePhoto(String profilePic);

    void create(User user);

    List<User> getAll();

    void updateProfileCurrentUser(ProfileDTO user);

}
