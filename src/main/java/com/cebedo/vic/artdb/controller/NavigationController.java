/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.controller;

import com.cebedo.vic.artdb.dto.PhotoDto;
import com.cebedo.vic.artdb.dto.ProfileDto;
import com.cebedo.vic.artdb.dto.ResponseDto;
import com.cebedo.vic.artdb.dto.UserDto;
import com.cebedo.vic.artdb.model.User;
import com.cebedo.vic.artdb.model.impl.MutableUser;
import com.cebedo.vic.artdb.service.PhotoService;
import com.cebedo.vic.artdb.service.UserService;
import com.cebedo.vic.artdb.utils.AuthUtils;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    String pageIndex(Model model, HttpServletRequest request) {
        model.addAttribute("photos", this.photoService.getPhotos(0));
        request.getSession().setAttribute("index-pagination-offset", 0);
        request.getSession().setAttribute("home-pagination-offset", 0);
        return "index";
    }

    @GetMapping("/artists")
    String pageArtists(Model model, HttpServletRequest request) {
        model.addAttribute("artists", this.userService.getAll());
        request.getSession().setAttribute("index-pagination-offset", 0);
        request.getSession().setAttribute("home-pagination-offset", 0);
        return "artists";
    }

    @GetMapping("/login")
    String pageLogin(Model model, HttpServletRequest request) {
        model.addAttribute("user", new UserDto());
        request.getSession().setAttribute("index-pagination-offset", 0);
        request.getSession().setAttribute("home-pagination-offset", 0);
        return "login";
    }

    @GetMapping("/login/fail")
    String pageLoginFail(RedirectAttributes attrs) {
        ResponseDto rsp = ResponseDto.newInstanceWithError("Incorrect username or password.");
        attrs.addFlashAttribute("responseErrors", rsp.getErrors());
        return "redirect:/login";
    }

    @GetMapping("/register")
    String pageRegister(Model model, HttpServletRequest request) {
        model.addAttribute("user", new UserDto());
        request.getSession().setAttribute("index-pagination-offset", 0);
        request.getSession().setAttribute("home-pagination-offset", 0);
        return "register";
    }

    @GetMapping("/logged-in/home")
    String pageHome(Model model, HttpServletRequest request) {
        User user = AuthUtils.getAuth().user();
        MutableUser profile = AuthUtils.getAuth().profile();
        model.addAttribute("user", user);
        model.addAttribute("profile", new ProfileDto(profile));
        model.addAttribute("photos", this.photoService.getPhotosByCurrentUser(0));
        model.addAttribute("photo", new PhotoDto());
        model.addAttribute("changePass", new UserDto());
        model.addAttribute("missingBio", StringUtils.isBlank(profile.bio()));
        model.addAttribute("missingEmail", StringUtils.isBlank(profile.email()));
        model.addAttribute("missingName", StringUtils.isBlank(profile.name()));
        model.addAttribute("missingPhone", StringUtils.isBlank(profile.phone()));
        model.addAttribute("missingWebsite", StringUtils.isBlank(profile.website()));
        model.addAttribute("missingProfilePic", StringUtils.isBlank(profile.profilePic()));
        model.addAttribute("isGuest", false);
        request.getSession().setAttribute("index-pagination-offset", 0);
        request.getSession().setAttribute("home-pagination-offset", 0);
        return "home";
    }
}
