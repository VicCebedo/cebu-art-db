/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.controller;

import com.cebedo.vic.artdb.dto.UserDTO;
import com.cebedo.vic.artdb.model.User;
import com.cebedo.vic.artdb.utils.AuthUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
@Controller
public class LoginController {

    @GetMapping("/")
    String index() {
        return "index";
    }

    @GetMapping("/login")
    String pageLogin(Model model) {
        model.addAttribute("user", new UserDTO());
        return "login";
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
        return "home";
    }

    @GetMapping("/login/fail")
    String pageLoginFail() {
        return "login-fail";
    }
}
