/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.service.impl;

import com.cebedo.vic.artdb.model.impl.Notification;
import com.cebedo.vic.artdb.repository.NotificationRepository;
import com.cebedo.vic.artdb.service.NotificationService;
import java.util.List;
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
    NotificationRepository notificationRepository;

    @Override
    public List<Notification> getByOwnerId(final long ownerId, final int page) {
        return this.notificationRepository.findByOwnerIdOrderByDatetimeDesc(ownerId, PageRequest.of(page, 10));
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
