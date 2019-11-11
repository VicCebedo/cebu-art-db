/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.service.impl;

import com.cebedo.vic.artdb.dao.UserDao;
import com.cebedo.vic.artdb.model.IUser;
import com.cebedo.vic.artdb.model.impl.Follow;
import com.cebedo.vic.artdb.utils.AuthUtils;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cebedo.vic.artdb.service.FollowService;
import com.cebedo.vic.artdb.repository.FollowRepository;
import com.cebedo.vic.artdb.model.IFollow;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
@Service("followService")
@Transactional
public class FollowServiceImpl implements FollowService {

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private UserDao userDao;

    @Override
    public List<Follow> getByCurrentUser() {
        return this.followRepository.findByFollowerId(AuthUtils.getAuth().user().id());
    }

    @Override
    public IFollow toggleFollow(final IFollow dto) {
        IUser targetUser = this.userDao.get(dto.targetId());
        IUser currentUser = AuthUtils.getAuth().user();
        Follow result = this.followRepository.findByTargetIdAndFollowerId(targetUser.id(), currentUser.id());

        // Get target user id.
        // If null, save it.
        // Else, delete it.
        if (result == null) {
            Follow mutable = (Follow) dto;
            mutable.setId(UUID.randomUUID().toString());
            mutable.setDatetime(new Date());
            mutable.setTargetId(targetUser.id());
            mutable.setTargetUsername(targetUser.username());
            mutable.setFollowerId(currentUser.id());
            mutable.setFollowerUsername(currentUser.username());
            return this.followRepository.save(mutable);
        }

        this.followRepository.deleteById(result.id());
        return new Follow();
    }

    @Override
    public boolean isFollow(long targetId, long followerId) {
        return this.followRepository.countByTargetIdAndFollowerId(targetId, followerId) > 0;
    }

    @Override
    public int countFollowCurrentUser() {
        return this.followRepository.countByFollowerId(AuthUtils.getAuth().user().id());
    }

}
