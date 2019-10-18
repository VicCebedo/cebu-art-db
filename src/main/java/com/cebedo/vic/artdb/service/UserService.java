/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.service;

import com.cebedo.vic.artdb.dto.ProfileDto;
import com.cebedo.vic.artdb.dto.ResponseDto;
import com.cebedo.vic.artdb.dto.CredentialsDto;
import java.util.List;
import com.cebedo.vic.artdb.model.IUser;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
public interface UserService {

    ResponseDto changePassword(final CredentialsDto user);

    ResponseDto create(final CredentialsDto user);

    IUser get(final String username);

    List<IUser> getUsers(final int offset);

    ResponseDto updateProfile(final ProfileDto user);

    ResponseDto updateProfilePic(final String profilePic);

    void deleteProfilePic();

}
