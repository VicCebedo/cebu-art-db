/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.controller;

import com.cebedo.vic.artdb.dto.UserDTO;
import com.cebedo.vic.artdb.model.Profile;
import com.cebedo.vic.artdb.model.impl.ProfileImpl;
import com.cebedo.vic.artdb.service.UserService;
import com.cebedo.vic.artdb.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
@Controller
@RequestMapping
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user/register")
    String register(final UserDTO user) {
        this.userService.create(user.getUsername(), user.getPassword());
        return "register-success";
    }

    @PostMapping("/logged-in/user/profile/update")
    String updateProfile(final ProfileImpl profile) {
        this.userService.updateProfile(profile);
        return "general-success";
    }

    @GetMapping("/logged-in/user/profile")
    String profilePage(Model model) {
        model.addAttribute("profile", AuthUtils.getAuth().user().profile());
        return "profile";
    }
}
