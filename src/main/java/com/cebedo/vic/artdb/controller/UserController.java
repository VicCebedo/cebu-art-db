/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.controller;

import com.cebedo.vic.artdb.dto.ProfileDTO;
import com.cebedo.vic.artdb.dto.UserDTO;
import com.cebedo.vic.artdb.model.User;
import com.cebedo.vic.artdb.service.PhotoService;
import com.cebedo.vic.artdb.service.UserService;
import com.cebedo.vic.artdb.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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

    @PutMapping("/logged-in/user/password/update")
    String updatePassword(final UserDTO changePass, RedirectAttributes attrs) {
        boolean success = this.userService.changePassword(changePass);
        attrs.addFlashAttribute("showChangePassSuccess", success);
        return "redirect:/logged-in/home";
    }

    @DeleteMapping("/logged-in/user/profile-pic/delete")
    @ResponseBody
    void deleteProfilePic() {
        this.userService.deleteProfilePic();
    }

    @PutMapping("/logged-in/user/profile-pic/update")
    String updateProfilePic(final ProfileDTO profile, RedirectAttributes attrs) {
        this.userService.updateProfilePic(profile.getProfilePic());
        attrs.addFlashAttribute("showChangeProfPicSuccess", true);
        return "redirect:/logged-in/home";
    }

    @PutMapping("/logged-in/user/profile/update")
    String updateProfileCurrentUser(final ProfileDTO profile, RedirectAttributes attrs) {
        this.userService.updateProfileCurrentUser(profile);
        attrs.addFlashAttribute("showEditProfileSuccess", true);
        return "redirect:/logged-in/home";
    }

    @GetMapping("/{username}")
    String pageUser(@PathVariable("username") final String username, Model model) {
        // If this username is equal to mine,
        // redirect to home.
        if (AuthUtils.isCustomToken() && username.equals(AuthUtils.getAuth().user().username())) {
            return "redirect:/logged-in/home";
        }

        // If we are visiting another person's profile.
        User user = this.userService.get(username);
        model.addAttribute("user", user);
        model.addAttribute("profile", new ProfileDTO(user));
        model.addAttribute("photos", this.photoService.getAllByUserId(user.id()));
        model.addAttribute("isGuest", true);
        return "home";
    }
}
