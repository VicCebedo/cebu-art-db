/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.dao;

import java.util.List;
import com.cebedo.vic.artdb.model.IPhoto;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
public interface PhotoDao {

    void create(final IPhoto photo);

    List<IPhoto> getPhotosByUserId(final long userId, final int offset);

    void delete(final long id);

    List<IPhoto> getPhotos(final int offset);

    void updateCaption(final IPhoto photo);

}
