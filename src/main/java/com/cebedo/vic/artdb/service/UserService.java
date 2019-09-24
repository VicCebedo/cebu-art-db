/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.service;

import com.cebedo.vic.artdb.dto.ProfileDTO;
import com.cebedo.vic.artdb.dto.UserDTO;
import com.cebedo.vic.artdb.model.User;
import java.util.List;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
public interface UserService {

    boolean changePassword(UserDTO user);

    void create(String username, String password);

    User get(String username);

    List<User> getAll();

    void updateProfileCurrentUser(ProfileDTO user);

    void updateProfilePic(String profilePic);

    void deleteProfilePic();

}
