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
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
        request.getSession().setAttribute("users-pagination-offset", 0);
        return "index";
    }

    @GetMapping("/artists")
    String pageArtists(Model model, HttpServletRequest request) {
        model.addAttribute("artists", this.userService.getUsers(0));
        request.getSession().setAttribute("index-pagination-offset", 0);
        request.getSession().setAttribute("home-pagination-offset", 0);
        request.getSession().setAttribute("users-pagination-offset", 0);
        return "artists";
    }

    @GetMapping("/artists/pagination/next")
    @ResponseBody
    String artistsPaginationNext(Model model, HttpServletRequest request) {
        // Get current offset value.
        HttpSession session = request.getSession();
        int offset = session.getAttribute("users-pagination-offset") == null
                ? 0
                : (int) session.getAttribute("users-pagination-offset");

        // Get photos.
        List<User> users = this.userService.getUsers(offset);
        StringBuilder returnString = new StringBuilder();
        for (User user : users) {

            String profPic = user.profilePic().isEmpty()
                    ? "https://res.cloudinary.com/hqx5vpvj4/image/upload/v1569906610/static/ecmvsberhzwodn6roitt.png"
                    : user.profilePic();

            String displayName = user.name().isEmpty()
                    ? user.username()
                    : user.name();

            String email = user.email().isEmpty()
                    ? ""
                    : "            <div class=\"ui label\"><i class=\"envelope icon\"></i> " + user.email() + "</div>\n";

            String website = user.website().isEmpty()
                    ? ""
                    : "            <a onclick=\"window.open('" + user.website() + "', '_blank');\">\n"
                    + "                <div class=\"ui label\"><i class=\"globe icon\"></i> " + user.website() + "</div>\n"
                    + "            </a>\n";

            String phone = user.phone().isEmpty()
                    ? ""
                    : "            <div class=\"ui label\"><i class=\"phone icon\"></i> " + user.phone() + "</div>\n";

            String template
                    = "<div class=\"item\">\n"
                    + "    <div class=\"image\" onclick=\"loading(); location.href='/" + user.username() + "';\">\n"
                    + "        <img src=\"" + profPic + "\"/>\n"
                    + "    </div>\n"
                    + "    <div class=\"content\">\n"
                    + "        <a href=\"#\" onclick=\"loading(); location.href='/" + user.username() + "';\" class=\"header\">\n"
                    + "            " + displayName + "\n"
                    + "        </a>\n"
                    + "        <div class=\"extra\">\n"
                    + "            <div class=\"ui label\"><i class=\"user icon\"></i> " + user.username() + "</div>\n"
                    + website
                    + email
                    + phone
                    + "        </div>\n"
                    + "        <div class=\"description\">\n"
                    + "            <p style=\"white-space: pre-line\">" + user.bio() + "</p>\n"
                    + "        </div>\n"
                    + "    </div>\n"
                    + "</div>";

            returnString.append(template);
        }

        // Increment offset by five, then save.
        session.setAttribute("users-pagination-offset", offset + 5);
        return returnString.toString();
    }

    @GetMapping("/login")
    String pageLogin(Model model, HttpServletRequest request) {
        model.addAttribute("user", new UserDto());
        request.getSession().setAttribute("index-pagination-offset", 0);
        request.getSession().setAttribute("home-pagination-offset", 0);
        request.getSession().setAttribute("users-pagination-offset", 0);
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
        request.getSession().setAttribute("users-pagination-offset", 0);
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
        request.getSession().setAttribute("users-pagination-offset", 0);
        return "home";
    }
}
