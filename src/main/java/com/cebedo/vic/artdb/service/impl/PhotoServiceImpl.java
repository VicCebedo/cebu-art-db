/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.service.impl;

import com.cebedo.vic.artdb.builder.PhotoBuilder;
import com.cebedo.vic.artdb.dao.PhotoDao;
import com.cebedo.vic.artdb.model.impl.Comment;
import com.cebedo.vic.artdb.model.impl.Like;
import com.cebedo.vic.artdb.dto.ResponseDto;
import com.cebedo.vic.artdb.repository.CommentRepository;
import com.cebedo.vic.artdb.repository.LikeRepository;
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
import com.cebedo.vic.artdb.model.IPhoto;
import com.cebedo.vic.artdb.model.IUser;

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

    @Autowired
    private LikeRepository likeRepository;

    @Override
    public ResponseDto create(final IPhoto photo) {
        // Artist only feature.
        if (!AuthUtils.isArtist()) {
            return ResponseDto.newInstanceWithError("Something went terribly wrong. Please contact support.");
        }

        Set<ConstraintViolation<IPhoto>> constraintViolations = validator.validate(photo);
        if (constraintViolations.size() > 0) {
            List<String> errors = new ArrayList<>();
            for (ConstraintViolation violation : constraintViolations) {
                errors.add(violation.getMessage());
            }
            return ResponseDto.newInstanceWithErrors(errors);
        }

        this.photoDao.create(new PhotoBuilder(
                0,
                photo.url(),
                photo.caption(),
                new Timestamp(System.currentTimeMillis()),
                AuthUtils.getAuth().user(),
                0,
                0,
                false)
                .build());
        return ResponseDto.newInstanceWithMessage("You have uploaded a new photo.");
    }

    @Override
    public ResponseDto updateCaption(final IPhoto photo) {
        // Artist only feature.
        if (!AuthUtils.isArtist()) {
            return ResponseDto.newInstanceWithError("Something went terribly wrong. Please contact support.");
        }

        Set<ConstraintViolation<IPhoto>> constraintViolations = validator.validate(photo);
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
    public List<IPhoto> getPhotosByCurrentUser(final int offset) {
        return this.getPhotosByUserId(AuthUtils.getAuth().user().id(), offset);
    }

    @Override
    public List<IPhoto> getPhotosByUserId(final long id, final int offset) {
        return this.photoDao.getPhotosByUserId(id, offset);
    }

    @Override
    public List<IPhoto> getPhotos(final int offset) {
        return this.photoDao.getPhotos(offset);
    }

    @Override
    public ResponseDto delete(final long id, final String cloudName) {
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
    public List<Comment> getCommentsByPhotoId(final long id) {
        return this.commentRepository.findByPhotoId(id);
    }

    @Override
    public Comment createComment(final Comment comment) {
        IUser user = AuthUtils.getAuth().user();
        comment.setId(UUID.randomUUID().toString());
        comment.setDatetime(new Date());
        comment.setUserId(user.id());
        comment.setUsername(user.username());
        return this.commentRepository.save(comment);
    }

    @Override
    public boolean deleteComment(final Comment comment) {
        this.commentRepository.deleteByIdAndUserId(
                comment.getId(),
                AuthUtils.getAuth().user().id());
        return true;
    }

    @Override
    public Like toggleLike(final Like like) {
        Like result = this.likeRepository.findByPhotoIdAndUserId(
                like.getPhotoId(),
                AuthUtils.getAuth().user().id());

        // If count is more than zero,
        // the un-like. Else, do like.
        if (result != null) {
            this.likeRepository.deleteById(result.getId());
            return new Like();
        }
        like.setId(UUID.randomUUID().toString());
        return this.likeRepository.save(like);
    }
}
