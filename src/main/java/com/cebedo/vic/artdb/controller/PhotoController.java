/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.controller;

import com.cebedo.vic.artdb.dto.ResponseDto;
import com.cebedo.vic.artdb.model.IComment;
import com.cebedo.vic.artdb.model.ILike;
import com.cebedo.vic.artdb.model.impl.Photo;
import com.cebedo.vic.artdb.service.PhotoService;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.cebedo.vic.artdb.model.IPhoto;
import com.cebedo.vic.artdb.model.impl.Comment;
import com.cebedo.vic.artdb.model.impl.Like;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
@Controller
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    @GetMapping("/logged-in/photo/pagination/next")
    @ResponseBody
    List<IPhoto> indexPaginationNext(final Model model, final HttpServletRequest request) {
        // Get current offset value.
        HttpSession session = request.getSession();
        int offset = session.getAttribute("index-pagination-offset") == null
                ? 0
                : (int) session.getAttribute("index-pagination-offset");

        session.setAttribute("index-pagination-offset", offset + 5);
        return this.photoService.getPhotos(offset);
    }

    @GetMapping("/logged-in/photo/comment/{id}")
    @ResponseBody
    List<Comment> getPhotoComments(@PathVariable("id") final long id) {
        return this.photoService.getCommentsByPhotoId(id);
    }

    @GetMapping("/logged-in/notification/{uuid}/photo/{id}")
    @ResponseBody
    IPhoto readNotification(
            @PathVariable("uuid") final String uuid,
            @PathVariable("id") final long id) {
        return this.photoService.readNotification(uuid, id);
    }

    @DeleteMapping("/logged-in/photo/comment/delete")
    @ResponseBody
    boolean deletePhotoComment(final Comment comment) {
        return this.photoService.deleteComment(comment);
    }

    @PostMapping("/logged-in/photo/comment/create")
    @ResponseBody
    IComment createComment(final Comment comment) {
        return this.photoService.createComment(comment);
    }

    @PostMapping("/logged-in/photo/like/toggle")
    @ResponseBody
    ILike toggleLike(final Like like) {
        return this.photoService.toggleLike(like);
    }

    @PostMapping("/logged-in/photo/upload")
    String upload(final Photo photo, final RedirectAttributes attrs) {
        ResponseDto rsp = this.photoService.create(photo);
        attrs.addFlashAttribute("responseErrors", rsp.getErrors());
        attrs.addFlashAttribute("responseMessages", rsp.getMessages());
        return "redirect:/logged-in/home";
    }

    @PutMapping("/logged-in/photo/caption/update")
    String updateCaption(final Photo photo, final RedirectAttributes attrs) {
        ResponseDto rsp = this.photoService.updateCaption(photo);
        attrs.addFlashAttribute("responseErrors", rsp.getErrors());
        attrs.addFlashAttribute("responseMessages", rsp.getMessages());
        return "redirect:/logged-in/home";
    }

    @DeleteMapping("/logged-in/photo/delete")
    String delete(final Photo photo, final RedirectAttributes attrs) {
        ResponseDto rsp = this.photoService.delete(photo.getId(), photo.getCloud());
        attrs.addFlashAttribute("responseErrors", rsp.getErrors());
        attrs.addFlashAttribute("responseMessages", rsp.getMessages());
        return "redirect:/logged-in/home";
    }

}
