/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.repository;

import com.cebedo.vic.artdb.model.impl.Notification;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
public interface NotificationRepository extends MongoRepository<Notification, String> {

    List<Notification> findByOwnerIdOrderByDatetimeDesc(final long ownerId, final Pageable pageable);

    List<Notification> findByUnread(final boolean unread);

    void deleteByPhotoId(final long photoId);

    void deleteByReferenceId(final String referenceId);

}
