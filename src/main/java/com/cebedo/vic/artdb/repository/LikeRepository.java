/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.repository;

import com.cebedo.vic.artdb.dto.LikeDto;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
public interface LikeRepository extends MongoRepository<LikeDto, String> {

    LikeDto findByPhotoIdAndUserId(long photoId, long userId);

    long countByPhotoId(long photoId);

}
