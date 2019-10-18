/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.controller;

import com.cebedo.vic.artdb.dto.CredentialsDto;
import com.cebedo.vic.artdb.model.ICatch;
import com.cebedo.vic.artdb.service.UserService;
import com.cebedo.vic.artdb.utils.AuthUtils;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.cebedo.vic.artdb.model.IUser;
import com.cebedo.vic.artdb.model.impl.Catch;
import com.cebedo.vic.artdb.service.CatchService;
import com.cebedo.vic.artdb.utils.SessionUtils;
import org.springframework.web.bind.annotation.PutMapping;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
@Controller
public class ArtistController {

    @Autowired
    private UserService userService;

    @Autowired
    private CatchService catchService;

    @GetMapping("/logged-in/artist")
    String pageArtist(final Model model, final HttpServletRequest request) {
        model.addAttribute("isArtist", AuthUtils.isArtist());
        model.addAttribute("catch", new Catch());
        model.addAttribute("artists", this.userService.getUsers(0));
        model.addAttribute("changePass", new CredentialsDto());
        SessionUtils.resetPaginationOffsets(request);
        return "artist";
    }

    @PutMapping("/logged-in/artist/catch/toggle")
    @ResponseBody
    ICatch toggleCatch(final Catch obj) {
        return this.catchService.toggleCatch(obj);
    }

    @GetMapping("/logged-in/artist/pagination/next")
    @ResponseBody
    String artistPaginationNext(final Model model, final HttpServletRequest request) {
        // Get current offset value.
        int offset = SessionUtils.getAttributeInt(request, "users-pagination-offset");

        // Get photos.
        List<IUser> users = this.userService.getUsers(offset);
        StringBuilder returnString = new StringBuilder();
        IUser currentUser = AuthUtils.getAuth().user();

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

            // Catch button.
            // Do not include if this is me.
            String catchButton = "";
            if (user.id() != currentUser.id()) {
                boolean isCatch = this.catchService.isCatch(user.id(), currentUser.id());
                String catchId = "catch-" + user.id();
                catchButton = isCatch
                        ? "<div class=\"content\">\n"
                        + "  <div id=\"" + catchId + "\" class=\"ui mini green basic button right floated\" onclick=\"toggleCatch(" + user.id() + ", '" + user.username() + "')\">\n"
                        + "    Catching\n"
                        + "  </div>\n"
                        + "</div>\n"
                        + "<br/>"
                        : "<div class=\"content\">\n"
                        + "  <div id=\"" + catchId + "\" class=\"ui mini green button right floated\" onclick=\"toggleCatch(" + user.id() + ", '" + user.username() + "')\">\n"
                        + "    Catch\n"
                        + "  </div>\n"
                        + "</div>\n"
                        + "<br/>";
            } else {
                catchButton = "<br/>";
            }

            String template
                    = "<div class=\"item\">\n"
                    + catchButton
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
        SessionUtils.setAttributeInt(request, "users-pagination-offset", offset + 5);
        return returnString.toString();
    }

}
