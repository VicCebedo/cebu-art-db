/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.model;

import com.cebedo.vic.artdb.constants.ActionEnum;
import java.util.Date;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
public interface INotification {

    String id();

    String referenceId();

    ActionEnum action();

    long userId();

    String username();

    long photoId();

    String thumbnail();

    boolean unread();

    Date datetime();

    long ownerId();

    String dateDisplay();

    String content();
}
