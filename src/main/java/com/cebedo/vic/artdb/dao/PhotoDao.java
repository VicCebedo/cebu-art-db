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

    void create(Photo photo);

    List<Photo> getAllByUserId(long userId);

}
