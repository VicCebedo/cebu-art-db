/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.service.impl;

import com.cebedo.vic.artdb.constants.ActionEnum;
import com.cebedo.vic.artdb.dao.PhotoDao;
import com.cebedo.vic.artdb.model.IPhoto;
import com.cebedo.vic.artdb.model.IUser;
import com.cebedo.vic.artdb.model.impl.Notification;
import com.cebedo.vic.artdb.repository.NotificationRepository;
import com.cebedo.vic.artdb.service.NotificationService;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
@Service("notificationService")
@Transactional
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private PhotoDao photoDao;

    @Override
    public List<Notification> getByOwnerId(final long ownerId, final int page) {
        return this.notificationRepository.findByOwnerIdOrderByDatetimeDesc(ownerId, PageRequest.of(page, 10));
    }

    @Override
    public IPhoto readNotification(final String uuid, final long id) {
        Notification notif = this.notificationRepository.findById(uuid).get();
        notif.setUnread(false);
        this.notificationRepository.save(notif);
        return this.photoDao.get(id);
    }

    @Override
    public void createNotification(
            final IUser currentUser,
            final long photoId,
            final ActionEnum action,
            final String refId,
            final String content) {

        IPhoto photo = this.photoDao.getPartial(photoId);
        long userId = currentUser.id();
        long ownerId = photo.user().id();

        // Create notification only if initiator of action
        // and owner of photo is not the same.
        if (ownerId != userId) {
            Date currentDate = new Date();
            Notification notif = new Notification();
            notif.setId(UUID.randomUUID().toString());
            notif.setAction(action);
            notif.setUserId(userId);
            notif.setUsername(currentUser.username());
            notif.setPhotoId(photoId);
            notif.setUnread(true);
            notif.setDatetime(currentDate);
            notif.setContent(content);
            notif.setReferenceId(refId);

            // Thumbnail url.
            String[] urlSplit = photo.url().split("/upload/");
            notif.setThumbnail(urlSplit[0] + "/upload/c_limit,h_100,w_100/" + urlSplit[1]);

            // Owner is the uploader of the photo.
            String dateString = new SimpleDateFormat("MMM dd, h:mm aaa").format(currentDate);
            notif.setOwnerId(ownerId);
            notif.setDateDisplay(dateString);

            this.notificationRepository.save(notif);
        }
    }

    @Override
    public void markExpiredNotificationsAsRead() {
        // Get all notifications where unread = true.
        List<Notification> notifs = this.notificationRepository.findByUnread(true);

        // Go through each,
        // check if notification is greater than 24 hours.
        for (Notification notif : notifs) {

            long past = notif.datetime().getTime();
            long now = System.currentTimeMillis();
            long life = now - past;
            long oneDay = 86400000;

            // If life of the notification has exceeded more than a day.
            if (life > oneDay) {

                // If yes, set unread = false (mark as read).
                // Then save.
                notif.setUnread(false);
            }
        }

        this.notificationRepository.saveAll(notifs);
    }

}
