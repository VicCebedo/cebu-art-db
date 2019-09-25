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

    List<Photo> getAllByCurrentUser();

    ResponseDto delete(long id, String cloudName);

    List<Photo> getAll();

    List<Photo> getAllByUserId(long id);

}
