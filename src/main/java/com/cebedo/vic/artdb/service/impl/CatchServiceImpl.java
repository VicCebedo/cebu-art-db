/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.service.impl;

import com.cebedo.vic.artdb.repository.CatchRepository;
import com.cebedo.vic.artdb.service.CatchService;
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

    @Override
    public boolean isCatch(long targetId, long followerId) {
        return this.catchRepository.countByTargetIdAndFollowerId(targetId, followerId) > 0;
    }

}
