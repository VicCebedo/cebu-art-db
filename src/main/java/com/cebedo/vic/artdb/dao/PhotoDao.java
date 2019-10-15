/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.dao;

import com.cebedo.vic.artdb.model.Photo;
import java.util.List;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
public interface PhotoDao {

    void create(final Photo photo);

    List<Photo> getPhotosByUserId(final long userId, final int offset);

    void delete(final long id);

    List<Photo> getPhotos(final int offset);

    void updateCaption(final Photo photo);

}
