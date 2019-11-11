/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.service;

import com.cebedo.vic.artdb.model.impl.Follow;
import java.util.List;
import com.cebedo.vic.artdb.model.IFollow;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
public interface FollowService {

    boolean isFollow(final long targetId, final long followerId);

    int countFollowCurrentUser();

    IFollow toggleFollow(final IFollow dto);

    List<Follow> getByCurrentUser();

}
