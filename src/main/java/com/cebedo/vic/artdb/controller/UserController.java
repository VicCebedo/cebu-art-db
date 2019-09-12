/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.controller;

import com.cebedo.vic.artdb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/test")
    void test() {
        this.userService.users();
    }

    @RequestMapping("/test/create")
    void testCreate() {
        this.userService.create("ahehe", "aaaa");
    }

}
