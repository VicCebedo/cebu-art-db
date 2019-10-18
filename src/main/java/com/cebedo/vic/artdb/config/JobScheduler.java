/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.config;

import com.cebedo.vic.artdb.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
@Configuration
@EnableScheduling
public class JobScheduler {

    @Autowired
    NotificationService notificationService;

    /**
     * 3600000 milliseconds = 1 hour.
     */
    @Scheduled(fixedRate = 3600000)
    public void markExpiredNotificationsAsRead() {
        this.notificationService.markExpiredNotificationsAsRead();
    }

}
