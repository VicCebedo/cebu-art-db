/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.controller;

import com.cebedo.vic.artdb.dto.CredentialsDto;
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
import com.cebedo.vic.artdb.utils.SessionUtils;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
@Controller
public class ArtistController {

    @Autowired
    private UserService userService;

    @GetMapping("/logged-in/artists")
    String pageArtists(final Model model, final HttpServletRequest request) {
        model.addAttribute("isArtist", AuthUtils.isArtist());
        model.addAttribute("artists", this.userService.getUsers(0));
        model.addAttribute("changePass", new CredentialsDto());
        SessionUtils.resetPaginationOffsets(request);
        return "artists";
    }

    @GetMapping("/logged-in/artists/pagination/next")
    @ResponseBody
    String artistsPaginationNext(final Model model, final HttpServletRequest request) {
        // Get current offset value.
        int offset = SessionUtils.getAttributeInt(request, "users-pagination-offset");

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

            String follow = "<div class=\"ui left labeled mini right floated button\" tabindex=\"0\">\n"
                    + "  <a class=\"ui mini basic black right pointing label\">\n"
                    + "    1,048\n"
                    + "  </a>\n"
                    + "  <div class=\"ui black mini button\">\n"
                    + "    <i class=\"plus icon\"></i> Catch\n"
                    + "  </div>\n"
                    + "</div>";

            String template
                    = "<div class=\"item\">\n"
                    + "    <div class=\"image\" onclick=\"loading(); location.href='/" + user.username() + "';\">\n"
                    + "        <img src=\"" + profPic + "\"/>\n"
                    + "    </div>\n"
                    + "    <div class=\"content\">\n"
                    + "        <a href=\"#\" onclick=\"loading(); location.href='/" + user.username() + "';\" class=\"header\">\n"
                    + "            " + displayName + "\n"
                    + "        </a>\n"
                    + follow
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
