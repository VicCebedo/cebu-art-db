/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.model;

import java.util.Date;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
public interface IComment {

    String id();

    long photoId();

    long userId();

    String username();

    String content();

    Date datetime();

}
