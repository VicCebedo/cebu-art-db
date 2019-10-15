/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.model;

import java.sql.Timestamp;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
public interface Photo {

    long id();

    String url();

    String caption();

    @Deprecated
    String thumbnailCaption();

    String cloud();

    @Deprecated
    String thumbnail();

    Timestamp timestamp();

    User user();

    long commentCount();

    long likeCount();

    boolean liked();

}
