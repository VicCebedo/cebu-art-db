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
    public List<Notification> getByOwnerId(final long ownerId) {
        return this.notificationRepository.findByOwnerIdOrderByDatetimeDesc(ownerId);
    }

}
