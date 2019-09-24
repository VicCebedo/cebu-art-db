/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.service.impl;

import com.cebedo.vic.artdb.builder.UserBuilder;
import com.cebedo.vic.artdb.dao.UserDao;
import com.cebedo.vic.artdb.dto.ProfileDTO;
import com.cebedo.vic.artdb.dto.UserDTO;
import com.cebedo.vic.artdb.model.User;
import com.cebedo.vic.artdb.service.UserService;
import com.cebedo.vic.artdb.utils.AuthUtils;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.io.IOException;
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

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public User get(final String username) {
        return this.userDao.get(username);
    }

    @Override
    public boolean changePassword(final UserDTO changePass) {
        // Validations.
        // TODO (Alpha) Error-handling/validation if passwords are not secure.
        String newPassword = changePass.getNewPassword();
        boolean valid = newPassword.equals(changePass.getNewPasswordRetype());

        if (valid) {
            User user = AuthUtils.getAuth().user();
            if (ENCODER.matches(changePass.getPassword(), user.password())) {
                this.userDao.changePassword(user.username(), ENCODER.encode(newPassword));
                return true;
            }

            System.out.println("PASSWORD: Doesn't match");
            return false;
        }

        System.out.println("PASSWORD: Not equal");
        return false;
    }

    @Override
    public void create(final String username, final String password) {
        this.userDao.create(new UserBuilder(0, username, ENCODER.encode(password), "", "", "", "", "", "").build());
    }

    @Override
    public void updateProfileCurrentUser(final ProfileDTO profile) {
        // TODO (Alpha) Implement profile pictures.
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

    @Override
    public void updateProfilePic(String profilePic) {
        AuthUtils.getAuth().profile().setProfilePic(profilePic);
        this.userDao.updateProfilePhoto(profilePic);
    }

    @Override
    public void deleteProfilePic() {
        try {
            String uname = AuthUtils.getAuth().user().username();
            this.cloudinary.uploader().destroy("user-uploads/" + uname + "/profile_pic", ObjectUtils.emptyMap());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
