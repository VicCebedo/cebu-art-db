/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.controller;

import com.cebedo.vic.artdb.model.IPhoto;
import com.cebedo.vic.artdb.model.impl.Comment;
import com.cebedo.vic.artdb.model.impl.Like;
import com.cebedo.vic.artdb.utils.AuthUtils;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.cebedo.vic.artdb.model.impl.Notification;
import com.cebedo.vic.artdb.service.NotificationService;
import com.cebedo.vic.artdb.utils.SessionUtils;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/logged-in/notifications")
    String pageNotifications(final Model model, final HttpServletRequest request) {
        model.addAttribute("isArtist", AuthUtils.isArtist());
        model.addAttribute("user", AuthUtils.getAuth().user());
        model.addAttribute("like", new Like());
        model.addAttribute("comment", new Comment());
        SessionUtils.resetPaginationOffsets(request);
        return "notifications";
    }

    @GetMapping("/logged-in/notifications/pagination/next")
    @ResponseBody
    List<Notification> notificationPaginationNext(final HttpServletRequest request) {
        long currentUserId = AuthUtils.getAuth().user().id();
        int page = SessionUtils.getAttributeInt(request, "notifications-pagination-page");
        List<Notification> notifs = this.notificationService.getByOwnerId(currentUserId, page);
        SessionUtils.setAttributeInt(request, "notifications-pagination-page", page + 1);
        return notifs;
    }

    @GetMapping("/logged-in/notification/{uuid}/photo/{id}")
    @ResponseBody
    IPhoto readNotification(
            @PathVariable("uuid") final String uuid,
            @PathVariable("id") final long id) {
        return this.notificationService.readNotification(uuid, id);
    }

}
