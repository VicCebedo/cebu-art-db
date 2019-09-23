/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.controller;

import com.cebedo.vic.artdb.dto.PhotoDTO;
import com.cebedo.vic.artdb.dto.ProfileDTO;
import com.cebedo.vic.artdb.dto.UserDTO;
import com.cebedo.vic.artdb.model.User;
import com.cebedo.vic.artdb.model.impl.MutableUser;
import com.cebedo.vic.artdb.service.PhotoService;
import com.cebedo.vic.artdb.service.UserService;
import com.cebedo.vic.artdb.utils.AuthUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
@Controller
public class NavigationController {

    @Autowired
    private PhotoService photoService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    String pageIndex(Model model) {
        model.addAttribute("photos", this.photoService.getAll());
        return "index";
    }

    @GetMapping("/artists")
    String pageArtists(Model model) {
        model.addAttribute("artists", this.userService.getAll());
        return "artists";
    }

    @GetMapping("/login")
    String pageLogin(Model model) {
        model.addAttribute("user", new UserDTO());
        return "login";
    }

    @GetMapping("/login/fail")
    String pageLoginFail() {
        return "login-fail";
    }

    @GetMapping("/register")
    String pageRegister(Model model) {
        model.addAttribute("user", new UserDTO());
        return "register";
    }

    @GetMapping("/logged-in/home")
    String pageHome(Model model) {
        // TODO (Beta) Mobile compatible website.
        // TODO (Beta) Create android app.
        // TODO (Beta) "Random" button?
        // TODO (Beta) Add profile pic.
        User user = AuthUtils.getAuth().user();
        MutableUser profile = AuthUtils.getAuth().profile();
        model.addAttribute("user", user);
        model.addAttribute("profile", new ProfileDTO(profile));
        model.addAttribute("photos", this.photoService.getAllByCurrentUser());
        model.addAttribute("photo", new PhotoDTO());
        model.addAttribute("changePass", new UserDTO());
        model.addAttribute("missingBio", StringUtils.isBlank(profile.bio()));
        model.addAttribute("missingEmail", StringUtils.isBlank(profile.email()));
        model.addAttribute("missingName", StringUtils.isBlank(profile.name()));
        model.addAttribute("missingPhone", StringUtils.isBlank(profile.phone()));
        model.addAttribute("missingWebsite", StringUtils.isBlank(profile.website()));
        model.addAttribute("isGuest", false);
        return "home";
    }
}
