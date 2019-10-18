/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.controller;

import com.cebedo.vic.artdb.model.ICatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import com.cebedo.vic.artdb.model.impl.Catch;
import com.cebedo.vic.artdb.service.CatchService;
import org.springframework.web.bind.annotation.PutMapping;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
@Controller
public class CatchController {

    @Autowired
    private CatchService catchService;

    @PutMapping("/logged-in/catch/toggle")
    @ResponseBody
    ICatch toggleCatch(final Catch obj) {
        return this.catchService.toggleCatch(obj);
    }

}
