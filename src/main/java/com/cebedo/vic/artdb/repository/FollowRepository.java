/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.repository;

import com.cebedo.vic.artdb.model.impl.Follow;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
public interface FollowRepository extends MongoRepository<Follow, String> {

    Follow findByTargetIdAndFollowerId(final long targetId, final long followerId);

    List<Follow> findByFollowerId(final long followerId);

    long countByTargetIdAndFollowerId(final long targetId, final long followerId);

    int countByFollowerId(final long followerId);

}
