/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.service;

import com.cebedo.vic.artdb.model.ICatch;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
public interface CatchService {

    boolean isCatch(final long targetId, final long followerId);

    int countCatchCurrentUser();

    ICatch toggleCatch(final ICatch dto);

}
