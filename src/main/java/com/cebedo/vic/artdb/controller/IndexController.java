/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.controller;

import com.cebedo.vic.artdb.model.impl.Comment;
import com.cebedo.vic.artdb.model.impl.Like;
import com.cebedo.vic.artdb.model.impl.Photo;
import com.cebedo.vic.artdb.dto.ProfileDto;
import com.cebedo.vic.artdb.dto.ResponseDto;
import com.cebedo.vic.artdb.dto.CredentialsDto;
import com.cebedo.vic.artdb.model.impl.User;
import com.cebedo.vic.artdb.service.PhotoService;
import com.cebedo.vic.artdb.utils.AuthUtils;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.cebedo.vic.artdb.model.IUser;
import com.cebedo.vic.artdb.utils.SessionUtils;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
@Controller
public class IndexController {

    @Autowired
    private PhotoService photoService;

    @GetMapping("/")
    String pageIndex(final Model model, final HttpServletRequest request) {
        // If not yet authenticated, redirect to login.
        if (!AuthUtils.isAuthenticated()) {
            return "redirect:/login";
        }

        model.addAttribute("user", AuthUtils.getAuth().user());
        model.addAttribute("isArtist", AuthUtils.isArtist());
        model.addAttribute("photos", this.photoService.getPhotos(0));
        model.addAttribute("changePass", new CredentialsDto());
        model.addAttribute("comment", new Comment());
        model.addAttribute("like", new Like());
        SessionUtils.resetPaginationOffsets(request);
        return "index";
    }

    @GetMapping("/login")
    String pageLogin(final Model model, final HttpServletRequest request) {
        model.addAttribute("user", new CredentialsDto());
        SessionUtils.resetPaginationOffsets(request);
        return "login";
    }

    @GetMapping("/login/fail")
    String pageLoginFail(final RedirectAttributes attrs) {
        ResponseDto rsp = ResponseDto.newInstanceWithError("Incorrect username or password.");
        attrs.addFlashAttribute("responseErrors", rsp.getErrors());
        return "redirect:/login";
    }

    @GetMapping("/register")
    String pageRegister(final Model model, final HttpServletRequest request) {
        model.addAttribute("user", new CredentialsDto());
        SessionUtils.resetPaginationOffsets(request);
        return "register";
    }

    @GetMapping("/logged-in/home")
    String pageHome(final Model model, final HttpServletRequest request) {
        // If non-artist account, redirect to index.
        if (!AuthUtils.isArtist()) {
            return "redirect:/";
        }

        IUser user = AuthUtils.getAuth().user();
        User profile = AuthUtils.getAuth().profile();
        model.addAttribute("user", user);
        model.addAttribute("profile", new ProfileDto(profile));
        model.addAttribute("photos", this.photoService.getPhotosByCurrentUser(0));
        model.addAttribute("photo", new Photo());
        model.addAttribute("changePass", new CredentialsDto());
        model.addAttribute("missingBio", StringUtils.isBlank(profile.bio()));
        model.addAttribute("missingEmail", StringUtils.isBlank(profile.email()));
        model.addAttribute("missingName", StringUtils.isBlank(profile.name()));
        model.addAttribute("missingPhone", StringUtils.isBlank(profile.phone()));
        model.addAttribute("missingWebsite", StringUtils.isBlank(profile.website()));
        model.addAttribute("missingProfilePic", StringUtils.isBlank(profile.profilePic()));
        model.addAttribute("isGuest", false);
        SessionUtils.resetPaginationOffsets(request);
        return "home";
    }
}
