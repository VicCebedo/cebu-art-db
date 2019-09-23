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
import com.cebedo.vic.artdb.service.PhotoService;
import com.cebedo.vic.artdb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
@Controller
@RequestMapping
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PhotoService photoService;

    @PostMapping("/user/register")
    String create(final UserDTO user, RedirectAttributes attrs) {
        // TODO (v1.1.0) Implement non-artist accounts using Redis.
        // Can only view, heart and comment.
        this.userService.create(user.getUsername(), user.getPassword());
        attrs.addFlashAttribute("showRegisterSuccess", true);
        return "redirect:/login";
    }

    @PutMapping("/logged-in/user/profile/update")
    String updateProfileCurrentUser(final ProfileDTO profile, RedirectAttributes attrs) {
        this.userService.updateProfileCurrentUser(profile);
        attrs.addFlashAttribute("showEditProfileSuccess", true);
        return "redirect:/logged-in/home";
    }

    @GetMapping("/{username}")
    String pageUser(@PathVariable("username") final String username, Model model) {
        User user = this.userService.get(username);
        model.addAttribute("user", user);
        model.addAttribute("profile", new ProfileDTO(user));
        model.addAttribute("photos", this.photoService.getAllByUserId(user.id()));
        model.addAttribute("photo", new PhotoDTO());
        return "home";
    }
}
