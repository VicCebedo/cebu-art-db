/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.service.impl;

import com.cebedo.vic.artdb.builder.UserBuilder;
import com.cebedo.vic.artdb.dao.UserDao;
import com.cebedo.vic.artdb.model.User;
import com.cebedo.vic.artdb.service.UserService;
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
    public boolean passwordMatch(String username, String password) {
        User user = this.userDao.get(username);
        return ENCODER.matches(password, user.password());
    }

    @Override
    public void changePassword(String username, String newPassword) {
        this.userDao.changePassword(username, ENCODER.encode(newPassword));
    }

    @Override
    public void create(String username, String password) {
        this.userDao.create(
                new UserBuilder(
                        0, username, ENCODER.encode(password))
                        .build());
    }

    @Override
    public List<User> getAll() {
        return this.userDao.getAll();
    }

}
