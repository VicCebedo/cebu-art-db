/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.controller;

import com.cebedo.vic.artdb.dto.PhotoDTO;
import com.cebedo.vic.artdb.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
@Controller
@RequestMapping("/logged-in/photo")
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    @PostMapping("/upload")
    String upload(final PhotoDTO photo, RedirectAttributes attrs) {
        // TODO (Beta) Optimize thumbnails, should pre-transform before upload to cloud.
        // TODO (Beta) Handle valid image extensions only when uploading.
        this.photoService.create(photo.getUrl(), photo.getCaption());
        attrs.addFlashAttribute("showUploadSuccess", true);
        return "redirect:/logged-in/home";
    }

    @DeleteMapping("/delete")
    String delete(final PhotoDTO photo, RedirectAttributes attrs) {
        this.photoService.delete(photo.getId(), photo.getCloud());
        attrs.addFlashAttribute("showDeleteSuccess", true);
        return "redirect:/logged-in/home";
    }

}
