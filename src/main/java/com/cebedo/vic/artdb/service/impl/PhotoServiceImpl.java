/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.service.impl;

import com.cebedo.vic.artdb.builder.PhotoBuilder;
import com.cebedo.vic.artdb.dao.PhotoDao;
import com.cebedo.vic.artdb.model.Photo;
import com.cebedo.vic.artdb.service.PhotoService;
import com.cebedo.vic.artdb.utils.AuthUtils;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
@Service("photoService")
@Transactional
public class PhotoServiceImpl implements PhotoService {

    @Autowired
    private PhotoDao photoDao;

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public void create(String url, String caption) {
        this.photoDao.create(new PhotoBuilder(
                0,
                url,
                caption,
                new Timestamp(System.currentTimeMillis()),
                AuthUtils.getAuth().user())
                .build());
    }

    @Override
    public List<Photo> getAllByCurrentUser() {
        return this.photoDao.getAllByUserId(AuthUtils.getAuth().user().id());
    }

    @Override
    public List<Photo> getAllByUserId(long id) {
        return this.photoDao.getAllByUserId(id);
    }

    @Override
    public List<Photo> getAll() {
        return this.photoDao.getAll();
    }

    @Override
    public void delete(long id, String cloudName) {
        this.photoDao.delete(id);
        try {
            String uname = AuthUtils.getAuth().user().username();
            cloudinary.uploader().destroy("user-uploads/" + uname + "/" + cloudName, ObjectUtils.emptyMap());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
