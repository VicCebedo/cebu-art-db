/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.dto;

import com.cebedo.vic.artdb.constants.ActionEnum;
import java.util.Date;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
public class NotificationDto {

    private long photoId;
    private long userId;
    private String username;
    private ActionEnum action;
    private Date datetime;

}
