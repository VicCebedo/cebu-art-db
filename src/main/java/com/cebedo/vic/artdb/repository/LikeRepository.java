/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.repository;

import com.cebedo.vic.artdb.model.impl.Like;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
public interface LikeRepository extends MongoRepository<Like, String> {

    Like findByPhotoIdAndUserId(final long photoId, final long userId);

    long countByPhotoId(final long photoId);

}
