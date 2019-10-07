/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.controller;

import com.cebedo.vic.artdb.dto.PhotoDto;
import com.cebedo.vic.artdb.dto.ResponseDto;
import com.cebedo.vic.artdb.model.Photo;
import com.cebedo.vic.artdb.service.PhotoService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
@Controller
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    @GetMapping("/photo/pagination/next")
    @ResponseBody
    String paginationNext(Model model, HttpServletRequest request) {
        // Get current offset value.
        HttpSession session = request.getSession();
        int offset = session.getAttribute("index-pagination-offset") == null
                ? 0
                : (int) session.getAttribute("index-pagination-offset");

        // Get photos.
        List<Photo> photos = this.photoService.getPhotos(offset);
        StringBuilder returnString = new StringBuilder();
        for (Photo photo : photos) {
            String id = "photostream-card-" + photo.id();
            String uploader = photo.user().name().isEmpty() ? photo.user().username() : photo.user().name();
            String template
                    = "<div class=\"card\" style=\"box-shadow: none; background: none; margin-bottom: 50px;\" id=\"" + id + "\">\n"
                    + "    <div class=\"content\">\n"
                    + "        <div class=\"ui small header\" onclick=\"loading(); location.href='" + photo.user().username() + "'\">" + uploader + "</div>\n"
                    + "    </div>\n"
                    + "    <div class=\"image\" caption=\"" + photo.caption() + "\" onclick=\"popupImage(this)\">\n"
                    + "        <img src=\"" + photo.url() + "\" class=\"ui image\" style=\"border-radius: 0px !important\" />\n"
                    + "    </div>\n"
                    + "</div>\n";
            returnString.append(template);
        }

        // Increment offset by five, then save.
        session.setAttribute("index-pagination-offset", offset + 5);
        return returnString.toString();
    }

    @PostMapping("/logged-in/photo/upload")
    String upload(final PhotoDto photo, RedirectAttributes attrs) {
        ResponseDto rsp = this.photoService.create(photo);
        attrs.addFlashAttribute("responseErrors", rsp.getErrors());
        attrs.addFlashAttribute("responseMessages", rsp.getMessages());
        return "redirect:/logged-in/home";
    }

    @PostMapping("/logged-in/photo/caption/update")
    String updateCaption(final PhotoDto photo, RedirectAttributes attrs) {
        ResponseDto rsp = this.photoService.updateCaption(photo);
        attrs.addFlashAttribute("responseErrors", rsp.getErrors());
        attrs.addFlashAttribute("responseMessages", rsp.getMessages());
        return "redirect:/logged-in/home";
    }

    @DeleteMapping("/logged-in/photo/delete")
    String delete(final PhotoDto photo, RedirectAttributes attrs) {
        ResponseDto rsp = this.photoService.delete(photo.getId(), photo.getCloud());
        attrs.addFlashAttribute("responseErrors", rsp.getErrors());
        attrs.addFlashAttribute("responseMessages", rsp.getMessages());
        return "redirect:/logged-in/home";
    }

}
