/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import com.cebedo.vic.artdb.model.impl.Follow;
import org.springframework.web.bind.annotation.PutMapping;
import com.cebedo.vic.artdb.service.FollowService;
import com.cebedo.vic.artdb.model.IFollow;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
@Controller
public class FollowController {

    @Autowired
    private FollowService followService;

    @PutMapping("/logged-in/follow/toggle")
    @ResponseBody
    IFollow toggleFollow(final Follow obj) {
        return this.followService.toggleFollow(obj);
    }

}
