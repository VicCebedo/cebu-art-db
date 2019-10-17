/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.model;

import com.cebedo.vic.artdb.model.impl.Comment;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
public interface IPhoto {

    long id();

    String url();

    String caption();

    String cloud();

    Timestamp timestamp();

    IUser user();

    long commentCount();

    long likeCount();

    boolean liked();

    List<Comment> comments();

}
