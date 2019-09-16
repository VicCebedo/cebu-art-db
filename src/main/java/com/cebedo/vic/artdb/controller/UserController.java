/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.controller;

import com.cebedo.vic.artdb.dto.UserDTO;
import com.cebedo.vic.artdb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    String register(final UserDTO user) {
        this.userService.create(user.getUsername(), user.getPassword());
        return "register-success";
    }

    @GetMapping("/test/2")
    void test2() {
        this.userService.changePassword("ahehe2", "bbbb");
    }

    @GetMapping("/test/3")
    void test3() {
        System.out.println(this.userService.passwordMatch("ahehe2", "bbbb"));
        System.out.println(this.userService.passwordMatch("ahehe2", "bbbb2"));
    }

}
