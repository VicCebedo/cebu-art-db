/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.service.impl;

import com.cebedo.vic.artdb.builder.PhotoBuilder;
import com.cebedo.vic.artdb.dao.PhotoDao;
import com.cebedo.vic.artdb.dto.CommentDto;
import com.cebedo.vic.artdb.dto.PhotoDto;
import com.cebedo.vic.artdb.dto.ResponseDto;
import com.cebedo.vic.artdb.model.Photo;
import com.cebedo.vic.artdb.repository.CommentRepository;
import com.cebedo.vic.artdb.service.PhotoService;
import com.cebedo.vic.artdb.utils.AuthUtils;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
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
    private Validator validator;

    @Autowired
    private PhotoDao photoDao;

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public ResponseDto create(PhotoDto photo) {
        // Artist only feature.
        if (!AuthUtils.isArtist()) {
            return ResponseDto.newInstanceWithError("Something went terribly wrong. Please contact support.");
        }

        Set<ConstraintViolation<PhotoDto>> constraintViolations = validator.validate(photo);
        if (constraintViolations.size() > 0) {
            List<String> errors = new ArrayList<>();
            for (ConstraintViolation violation : constraintViolations) {
                errors.add(violation.getMessage());
            }
            return ResponseDto.newInstanceWithErrors(errors);
        }

        this.photoDao.create(new PhotoBuilder(
                0,
                photo.getUrl(),
                photo.getCaption(),
                new Timestamp(System.currentTimeMillis()),
                AuthUtils.getAuth().user(),
                0)
                .build());
        return ResponseDto.newInstanceWithMessage("You have uploaded a new photo.");
    }

    @Override
    public ResponseDto updateCaption(PhotoDto photo) {
        // Artist only feature.
        if (!AuthUtils.isArtist()) {
            return ResponseDto.newInstanceWithError("Something went terribly wrong. Please contact support.");
        }

        Set<ConstraintViolation<PhotoDto>> constraintViolations = validator.validate(photo);
        if (constraintViolations.size() > 0) {
            List<String> errors = new ArrayList<>();
            for (ConstraintViolation violation : constraintViolations) {
                errors.add(violation.getMessage());
            }
            return ResponseDto.newInstanceWithErrors(errors);
        }

        this.photoDao.updateCaption(photo);
        return ResponseDto.newInstanceWithMessage("You have updated the caption.");
    }

    @Override
    public List<PhotoDto> getPhotosByCurrentUser(int offset) {
        return this.getPhotosByUserId(AuthUtils.getAuth().user().id(), offset);
    }

    @Override
    public List<PhotoDto> getPhotosByUserId(long id, int offset) {
        return convertToDto(this.photoDao.getPhotosByUserId(id, offset));
    }

    @Override
    public List<PhotoDto> getPhotos(int offset) {
        List<Photo> photos = this.photoDao.getPhotos(offset);
        return convertToDto(photos);
    }

    private List<PhotoDto> convertToDto(List<Photo> photos) {
        List<PhotoDto> converted = new ArrayList<>();
        for (Photo photo : photos) {
            converted.add(new PhotoDto(photo));
        }
        return converted;
    }

    @Override
    public ResponseDto delete(long id, String cloudName) {
        // Artist only feature.
        if (!AuthUtils.isArtist()) {
            return ResponseDto.newInstanceWithError("Something went terribly wrong. Please contact support.");
        }

        this.photoDao.delete(id);
        try {
            String uname = AuthUtils.getAuth().user().username();
            cloudinary.uploader().destroy("user-uploads/" + uname + "/" + cloudName, ObjectUtils.emptyMap());
            return ResponseDto.newInstanceWithMessage("Your photo is now deleted.");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return ResponseDto.newInstanceWithError("Something wrong happened when we tried to delete your photo. Please try again.");
    }

    @Override
    public List<CommentDto> getCommentsByPhotoId(long id) {
        return this.commentRepository.findByPhotoId(id);
    }

    @Override
    public CommentDto createComment(CommentDto comment) {
        // TODO Comment validations(?). 
        comment.setId(UUID.randomUUID().toString());
        comment.setDatetime(new Date());
        return this.commentRepository.save(comment);
    }

    @Override
    public boolean deleteComment(CommentDto comment) {
        // TODO Make sure I'm deleting my comment.
        this.commentRepository.deleteById(comment.getId());
        return true;
    }
}
