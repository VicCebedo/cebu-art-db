/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.service;

import com.cebedo.vic.artdb.dto.PhotoDto;
import com.cebedo.vic.artdb.dto.ResponseDto;
import com.cebedo.vic.artdb.model.Photo;
import java.util.List;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
public interface PhotoService {

    ResponseDto create(PhotoDto photo);

    @Deprecated
    List<Photo> getAllByCurrentUser();

    ResponseDto delete(long id, String cloudName);

    @Deprecated
    List<Photo> getAllByUserId(long id);

    ResponseDto updateCaption(PhotoDto photo);

    List<Photo> getPhotos(int offset);

    List<Photo> getPhotosByCurrentUser(int offset);

    List<Photo> getPhotosByUserId(long id, int offset);

}
