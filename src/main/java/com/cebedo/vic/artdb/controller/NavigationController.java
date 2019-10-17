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
import com.cebedo.vic.artdb.model.IUser;
import com.cebedo.vic.artdb.model.impl.Notification;
import com.cebedo.vic.artdb.service.NotificationService;

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

    @Autowired
    private NotificationService notificationService;

    private void resetPaginationOffsets(final HttpServletRequest request) {
        request.getSession().setAttribute("index-pagination-offset", 0);
        request.getSession().setAttribute("home-pagination-offset", 0);
        request.getSession().setAttribute("users-pagination-offset", 0);
    }

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
        resetPaginationOffsets(request);
        return "index";
    }

    @GetMapping("/logged-in/artists")
    String pageArtists(final Model model, final HttpServletRequest request) {
        model.addAttribute("isArtist", AuthUtils.isArtist());
        model.addAttribute("artists", this.userService.getUsers(0));
        model.addAttribute("changePass", new CredentialsDto());
        resetPaginationOffsets(request);
        return "artists";
    }

    @GetMapping("/logged-in/notifications")
    String pageNotifications(final Model model, final HttpServletRequest request) {
        model.addAttribute("isArtist", AuthUtils.isArtist());
        model.addAttribute("user", AuthUtils.getAuth().user());
        model.addAttribute("like", new Like());
        model.addAttribute("comment", new Comment());
        resetPaginationOffsets(request);
        return "notifications";
    }

    @GetMapping("/logged-in/notifications/pagination/next")
    @ResponseBody
    List<Notification> notificationPaginationNext() {
        // TODO Implement offset.
        long currentUserId = AuthUtils.getAuth().user().id();
        return this.notificationService.getByOwnerId(currentUserId);
    }

    @GetMapping("/logged-in/artists/pagination/next")
    @ResponseBody
    String artistsPaginationNext(final Model model, final HttpServletRequest request) {
        // Get current offset value.
        HttpSession session = request.getSession();
        int offset = session.getAttribute("users-pagination-offset") == null
                ? 0
                : (int) session.getAttribute("users-pagination-offset");

        // Get photos.
        List<IUser> users = this.userService.getUsers(offset);
        StringBuilder returnString = new StringBuilder();
        for (IUser user : users) {

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
    String pageLogin(final Model model, final HttpServletRequest request) {
        model.addAttribute("user", new CredentialsDto());
        resetPaginationOffsets(request);
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
        resetPaginationOffsets(request);
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
        resetPaginationOffsets(request);
        return "home";
    }
}
