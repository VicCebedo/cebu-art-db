/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.service.impl;

import com.cebedo.vic.artdb.dao.UserDao;
import com.cebedo.vic.artdb.model.ICatch;
import com.cebedo.vic.artdb.model.IUser;
import com.cebedo.vic.artdb.model.impl.Catch;
import com.cebedo.vic.artdb.repository.CatchRepository;
import com.cebedo.vic.artdb.service.CatchService;
import com.cebedo.vic.artdb.utils.AuthUtils;
import java.util.Date;
import java.util.UUID;
import static jdk.nashorn.internal.runtime.Debug.id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
@Service("catchService")
@Transactional
public class CatchServiceImpl implements CatchService {

    @Autowired
    private CatchRepository catchRepository;

    @Autowired
    private UserDao userDao;

    @Override
    public ICatch toggleCatch(final ICatch dto) {
        IUser targetUser = this.userDao.get(dto.targetId());
        IUser currentUser = AuthUtils.getAuth().user();
        Catch result = this.catchRepository.findByTargetIdAndFollowerId(targetUser.id(), currentUser.id());

        // Get target user id.
        // If null, save it.
        // Else, delete it.
        if (result == null) {
            Catch mutable = (Catch) dto;
            mutable.setId(UUID.randomUUID().toString());
            mutable.setDatetime(new Date());
            mutable.setTargetId(targetUser.id());
            mutable.setTargetUsername(targetUser.username());
            mutable.setFollowerId(currentUser.id());
            mutable.setFollowerUsername(currentUser.username());
            return this.catchRepository.save(mutable);
        }

        this.catchRepository.deleteById(result.id());
        return new Catch();
    }

    @Override
    public boolean isCatch(long targetId, long followerId) {
        return this.catchRepository.countByTargetIdAndFollowerId(targetId, followerId) > 0;
    }

    @Override
    public int countCatchCurrentUser() {
        return this.catchRepository.countByFollowerId(AuthUtils.getAuth().user().id());
    }

}
