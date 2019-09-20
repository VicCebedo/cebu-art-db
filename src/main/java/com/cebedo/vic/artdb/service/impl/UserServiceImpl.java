/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.service.impl;

import com.cebedo.vic.artdb.builder.UserBuilder;
import com.cebedo.vic.artdb.dao.UserDao;
import com.cebedo.vic.artdb.dto.ProfileDTO;
import com.cebedo.vic.artdb.model.User;
import com.cebedo.vic.artdb.service.UserService;
import com.cebedo.vic.artdb.utils.AuthUtils;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    private static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();

    @Autowired
    private UserDao userDao;

    @Override
    public User get(final String username) {
        return this.userDao.get(username);
    }

    @Override
    public void changePassword(final String username, final String newPassword) {
        this.userDao.changePassword(username, ENCODER.encode(newPassword));
    }

    @Override
    public void create(final String username, final String password) {
        this.userDao.create(new UserBuilder(0, username, ENCODER.encode(password), "", "", "", "", "").build()
        );
    }

    @Override
    public void updateProfileCurrentUser(final ProfileDTO profile) {
        AuthUtils.getAuth().profile().setBio(profile.getBio());
        AuthUtils.getAuth().profile().setEmail(profile.getEmail());
        AuthUtils.getAuth().profile().setName(profile.getName());
        AuthUtils.getAuth().profile().setPhone(profile.getPhone());
        AuthUtils.getAuth().profile().setWebsite(profile.getWebsite());
        this.userDao.updateProfileCurrentUser(profile);
    }

    @Override
    public List<User> getAll() {
        return this.userDao.getAll();
    }

}
