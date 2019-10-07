/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.dao;

import com.cebedo.vic.artdb.dto.PhotoDto;
import com.cebedo.vic.artdb.model.Photo;
import java.util.List;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
public interface PhotoDao {

    void create(Photo photo);

    List<Photo> getPhotosByUserId(long userId, int offset);

    void delete(long id);

    List<Photo> getPhotos(int offset);

    void updateCaption(PhotoDto photo);

}
