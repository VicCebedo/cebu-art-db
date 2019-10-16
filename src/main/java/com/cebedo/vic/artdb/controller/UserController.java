/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.controller;

import com.cebedo.vic.artdb.dto.ProfileDto;
import com.cebedo.vic.artdb.dto.ResponseDto;
import com.cebedo.vic.artdb.dto.CredentialsDto;
import com.cebedo.vic.artdb.service.PhotoService;
import com.cebedo.vic.artdb.service.UserService;
import com.cebedo.vic.artdb.utils.AuthUtils;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
import com.cebedo.vic.artdb.model.IPhoto;
import com.cebedo.vic.artdb.model.IUser;

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
    String create(final CredentialsDto user, final RedirectAttributes attrs) {
        ResponseDto rsp = this.userService.create(user);
        attrs.addFlashAttribute("responseErrors", rsp.getErrors());
        attrs.addFlashAttribute("responseMessages", rsp.getMessages());
        return rsp.getErrors() == null || rsp.getErrors().isEmpty()
                ? "redirect:/login"
                : "redirect:/register";
    }

    @PutMapping("/logged-in/user/password/update")
    String updatePassword(final CredentialsDto changePass, final RedirectAttributes attrs) {
        ResponseDto rsp = this.userService.changePassword(changePass);
        attrs.addFlashAttribute("responseErrors", rsp.getErrors());
        attrs.addFlashAttribute("responseMessages", rsp.getMessages());

        // If artist, back to profile.
        // Else, to index.
        if (AuthUtils.isArtist()) {
            return "redirect:/logged-in/home";
        }
        return "redirect:/";
    }

    @PutMapping("/logged-in/user/profile-pic/update")
    String updateProfilePic(final ProfileDto profile, final RedirectAttributes attrs) {
        ResponseDto rsp = this.userService.updateProfilePic(profile.getProfilePic());
        attrs.addFlashAttribute("responseMessages", rsp.getMessages());
        return "redirect:/logged-in/home";
    }

    @PutMapping("/logged-in/user/profile/update")
    String updateProfileCurrentUser(final ProfileDto profile, final RedirectAttributes attrs) {
        ResponseDto rsp = this.userService.updateProfileCurrentUser(profile);
        attrs.addFlashAttribute("responseErrors", rsp.getErrors());
        attrs.addFlashAttribute("responseMessages", rsp.getMessages());
        return "redirect:/logged-in/home";
    }

    @DeleteMapping("/logged-in/user/profile-pic/delete")
    @ResponseBody
    void deleteProfilePic() {
        this.userService.deleteProfilePic();
    }

    @GetMapping("/logged-in/user/{id}/photo/pagination/next")
    @ResponseBody
    List<IPhoto> homePaginationNext(
            @PathVariable("id") final long id,
            final Model model,
            final HttpServletRequest request) {

        // Get current offset value.
        HttpSession session = request.getSession();
        int offset = session.getAttribute("home-pagination-offset") == null
                ? 0
                : (int) session.getAttribute("home-pagination-offset");

        // Process the response.
        session.setAttribute("home-pagination-offset", offset + 5);
        return this.photoService.getPhotosByUserId(id, offset);
    }

    @GetMapping("/{username}")
    String pageUser(
            @PathVariable("username") final String username,
            final Model model,
            final HttpServletRequest request) {

        // If not yet authenticated, redirect to login.
        if (!AuthUtils.isAuthenticated()) {
            return "redirect:/login";
        }

        // If this username is equal to mine,
        // redirect to home.
        boolean isArtist = AuthUtils.isArtist();
        if (username.equals(AuthUtils.getAuth().user().username())) {

            // If non-artist, redirect to index.
            if (!isArtist) {
                return "redirect:/";
            }
            return "redirect:/logged-in/home";
        }

        // If we are visiting another person's profile.
        IUser user = this.userService.get(username);
        model.addAttribute("user", user);
        model.addAttribute("profile", new ProfileDto(user));
        model.addAttribute("isGuest", true);
        model.addAttribute("isArtist", isArtist);
        model.addAttribute("changePass", new CredentialsDto());
        request.getSession().setAttribute("index-pagination-offset", 0);
        request.getSession().setAttribute("home-pagination-offset", 0);
        request.getSession().setAttribute("users-pagination-offset", 0);
        return "home";
    }
}
