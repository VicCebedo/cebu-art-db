/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.controller;

import com.cebedo.vic.artdb.dto.PhotoDto;
import com.cebedo.vic.artdb.dto.ResponseDto;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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

    @GetMapping("/pagination/next")
    @ResponseBody
    List<PhotoDto> paginationNext(Model model, HttpServletRequest request) {
        // Get current offset value.
        HttpSession session = request.getSession();
        int offset = session.getAttribute("pagination-offset") == null
                ? 0
                : (int) session.getAttribute("pagination-offset");

        // Increment offset by five, then save.
        session.setAttribute("pagination-offset", offset + 5);
        return this.photoService.getPhotos(offset);
    }

    @PostMapping("/upload")
    String upload(final PhotoDto photo, RedirectAttributes attrs) {
        ResponseDto rsp = this.photoService.create(photo);
        attrs.addFlashAttribute("responseErrors", rsp.getErrors());
        attrs.addFlashAttribute("responseMessages", rsp.getMessages());
        return "redirect:/logged-in/home";
    }

    @PostMapping("/caption/update")
    String updateCaption(final PhotoDto photo, RedirectAttributes attrs) {
        ResponseDto rsp = this.photoService.updateCaption(photo);
        attrs.addFlashAttribute("responseErrors", rsp.getErrors());
        attrs.addFlashAttribute("responseMessages", rsp.getMessages());
        return "redirect:/logged-in/home";
    }

    @DeleteMapping("/delete")
    String delete(final PhotoDto photo, RedirectAttributes attrs) {
        ResponseDto rsp = this.photoService.delete(photo.getId(), photo.getCloud());
        attrs.addFlashAttribute("responseErrors", rsp.getErrors());
        attrs.addFlashAttribute("responseMessages", rsp.getMessages());
        return "redirect:/logged-in/home";
    }

}
