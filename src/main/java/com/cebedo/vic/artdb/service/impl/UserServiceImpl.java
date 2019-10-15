/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.service.impl;

import com.cebedo.vic.artdb.builder.UserBuilder;
import com.cebedo.vic.artdb.dao.UserDao;
import com.cebedo.vic.artdb.dto.ProfileDto;
import com.cebedo.vic.artdb.dto.ResponseDto;
import com.cebedo.vic.artdb.dto.UserDto;
import com.cebedo.vic.artdb.model.User;
import com.cebedo.vic.artdb.service.UserService;
import com.cebedo.vic.artdb.utils.AuthUtils;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
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
    private Validator validator;

    @Autowired
    private UserDao userDao;

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public User get(final String username) {
        return this.userDao.get(username);
    }

    @Override
    public ResponseDto changePassword(final UserDto changePass) {
        // Validations.
        String newPassword = changePass.getNewPassword();
        boolean retypeMatch = newPassword.equals(changePass.getNewPasswordRetype());

        if (retypeMatch) {

            // General validation.
            Set<ConstraintViolation<UserDto>> constraintViolations = validator.validate(changePass);
            if (constraintViolations.size() > 0) {
                List<String> errors = new ArrayList<>();
                for (ConstraintViolation violation : constraintViolations) {
                    errors.add(violation.getMessage());
                }
                return ResponseDto.newInstanceWithErrors(errors);
            }

            User user = AuthUtils.getAuth().user();
            if (ENCODER.matches(changePass.getPassword(), user.password())) {
                this.userDao.changePassword(user.username(), ENCODER.encode(newPassword));
                return ResponseDto.newInstanceWithMessage("Your password is now updated.");
            }
            return ResponseDto.newInstanceWithError("Incorrect password. Please try again.");
        }
        return ResponseDto.newInstanceWithError("Your new passwords don't match. Please try again.");
    }

    @Override
    public ResponseDto create(final UserDto userDto) {
        // Check if passwords are not equal.
        String password = userDto.getPassword();
        String retype = userDto.getNewPasswordRetype();
        if (!password.equals(retype)) {
            return ResponseDto.newInstanceWithError("Your passwords don't match. Please try again.");
        }

        // Check if user exists.
        String username = userDto.getUsername();
        User userCheck = this.userDao.get(username);
        if (userCheck.id() != 0) {
            return ResponseDto.newInstanceWithError("The username you selected already exists. Please try again.");
        }

        // General validation.
        Set<ConstraintViolation<UserDto>> constraintViolations = validator.validate(userDto);
        if (constraintViolations.size() > 0) {
            List<String> errors = new ArrayList<>();
            for (ConstraintViolation violation : constraintViolations) {
                errors.add(violation.getMessage());
            }
            return ResponseDto.newInstanceWithErrors(errors);
        }

        // If user has a code, then check if the code is valid.
        // Else, account will be basic, not artist account.
        String inviteCode = userDto.getInviteCode();
        boolean hasCode = !inviteCode.isEmpty();
        if (hasCode) {

            // Check if invite code exists.
            // And if invite code remaining > 0.
            int remaining = this.userDao.getInviteCodeRemaining(inviteCode);
            if (remaining == 0) {
                return ResponseDto.newInstanceWithError("The invite code you provided is not valid. Please try again.");
            }

            // If invite code remaining > 0,
            // deduct 1 from remaining.
            this.userDao.decrementInviteCodeRemaining(inviteCode, remaining - 1);
        }

        // Normal behaviour.
        User user = new UserBuilder(0, username, ENCODER.encode(password), "", "", "", "", "", "", hasCode).build();
        this.userDao.create(user);
        return ResponseDto.newInstanceWithMessage("Your user registration was successful.");
    }

    @Override
    public ResponseDto updateProfileCurrentUser(final ProfileDto profile) {
        // Artist only feature.
        if (!AuthUtils.isArtist()) {
            return ResponseDto.newInstanceWithError("Something went terribly wrong. Please contact support.");
        }

        // Websites must be prefixed with http or https.
        String web = profile.getWebsite();
        if (!web.isEmpty() && !web.startsWith("http")) {
            profile.setWebsite("http://" + web);
        }

        // Validations.
        Set<ConstraintViolation<ProfileDto>> constraintViolations = validator.validate(profile);
        if (constraintViolations.size() > 0) {
            List<String> errors = new ArrayList<>();
            for (ConstraintViolation violation : constraintViolations) {
                errors.add(violation.getMessage());
            }
            return ResponseDto.newInstanceWithErrors(errors);
        }

        // Update in DB and in session.
        this.userDao.updateProfileCurrentUser(profile);
        AuthUtils.getAuth().profile().setName(profile.getName());
        AuthUtils.getAuth().profile().setBio(profile.getBio());
        AuthUtils.getAuth().profile().setWebsite(profile.getWebsite());
        AuthUtils.getAuth().profile().setEmail(profile.getEmail());
        AuthUtils.getAuth().profile().setPhone(profile.getPhone());

        return ResponseDto.newInstanceWithMessage("Your profile is now updated.");
    }

    @Override
    public ResponseDto updateProfilePic(String profilePic) {
        // Artist only feature.
        if (!AuthUtils.isArtist()) {
            return ResponseDto.newInstanceWithError("Something went terribly wrong. Please contact support.");
        }

        this.userDao.updateProfilePhoto(profilePic);
        AuthUtils.getAuth().profile().setProfilePic(profilePic);
        return ResponseDto.newInstanceWithMessage("Your profile photo is now updated.");
    }

    @Override
    public List<User> getUsers(int offset) {
        return this.userDao.getUsers(offset);
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
