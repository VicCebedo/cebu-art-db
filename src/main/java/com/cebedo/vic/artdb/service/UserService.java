/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.service;

import com.cebedo.vic.artdb.dto.ProfileDto;
import com.cebedo.vic.artdb.dto.ResponseDto;
import com.cebedo.vic.artdb.dto.UserDto;
import com.cebedo.vic.artdb.model.User;
import java.util.List;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
public interface UserService {

    ResponseDto changePassword(UserDto user);

    ResponseDto create(UserDto user);

    User get(String username);

    List<User> getUsers(int offset);

    ResponseDto updateProfileCurrentUser(ProfileDto user);

    ResponseDto updateProfilePic(String profilePic);

    void deleteProfilePic();

}
