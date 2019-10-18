/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.service;

import com.cebedo.vic.artdb.constants.ActionEnum;
import com.cebedo.vic.artdb.model.IPhoto;
import com.cebedo.vic.artdb.model.IUser;
import com.cebedo.vic.artdb.model.impl.Notification;
import java.util.List;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
public interface NotificationService {

    List<Notification> getByOwnerId(final long ownerId, final int page);

    void markExpiredNotificationsAsRead();

    IPhoto readNotification(final String uuid, final long id);

    void createNotification(final IUser currentUser, final long photoId, final ActionEnum action, final String refId, final String content);

}
