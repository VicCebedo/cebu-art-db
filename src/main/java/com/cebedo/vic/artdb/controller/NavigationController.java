/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.controller;

import com.cebedo.vic.artdb.dto.PhotoDTO;
import com.cebedo.vic.artdb.dto.UserDTO;
import com.cebedo.vic.artdb.model.User;
import com.cebedo.vic.artdb.service.PhotoService;
import com.cebedo.vic.artdb.utils.AuthUtils;
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

    @GetMapping("/")
    String index(Model model) {
        model.addAttribute("photos", this.photoService.getAll());
        return "index";
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
        User user = AuthUtils.getAuth().user();
        model.addAttribute("user", user);
        model.addAttribute("profile", user.profile());
        model.addAttribute("photos", this.photoService.getAllByCurrentUser());
        model.addAttribute("photo", new PhotoDTO());
        return "home";
    }
}
