/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.service.impl;

import com.cebedo.vic.artdb.builder.PhotoBuilder;
import com.cebedo.vic.artdb.constants.ActionEnum;
import com.cebedo.vic.artdb.dao.PhotoDao;
import com.cebedo.vic.artdb.dto.ResponseDto;
import com.cebedo.vic.artdb.model.IComment;
import com.cebedo.vic.artdb.model.ILike;
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
import com.cebedo.vic.artdb.model.impl.Comment;
import com.cebedo.vic.artdb.model.impl.Like;
import com.cebedo.vic.artdb.model.impl.Notification;
import com.cebedo.vic.artdb.repository.NotificationRepository;
import java.text.SimpleDateFormat;

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

    @Autowired
    private NotificationRepository notificationRepository;

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
    public ResponseDto delete(final long photoId, final String cloudName) {
        // Artist only feature.
        if (!AuthUtils.isArtist()) {
            return ResponseDto.newInstanceWithError("Something went terribly wrong. Please contact support.");
        }

        // Delete from postgresql.
        this.photoDao.delete(photoId);
        try {
            // Delete physical photo.
            String uname = AuthUtils.getAuth().user().username();
            cloudinary.uploader().destroy("user-uploads/" + uname + "/" + cloudName, ObjectUtils.emptyMap());

            // Delete all likes.
            this.likeRepository.deleteByPhotoId(photoId);

            // Delete all comments.
            this.commentRepository.deleteByPhotoId(photoId);

            // Delete all notifications.
            this.notificationRepository.deleteByPhotoId(photoId);

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
    public IComment createComment(final IComment comment) {
        IUser user = AuthUtils.getAuth().user();
        Comment mutable = (Comment) comment;
        mutable.setId(UUID.randomUUID().toString());
        mutable.setDatetime(new Date());
        mutable.setUserId(user.id());
        mutable.setUsername(user.username());

        // Save notification and comment.
        saveNotification(user, comment.photoId(), ActionEnum.COMMENT);

        return this.commentRepository.save(mutable);
    }

    private void saveNotification(IUser currentUser, long photoId, ActionEnum action) {
        IPhoto photo = this.photoDao.get(photoId);
        long userId = currentUser.id();
        long ownerId = photo.user().id();

        // Create notification only if initiator of action
        // and owner of photo is not the same.
        if (ownerId != userId) {
            Notification notif = new Notification();
            Date currentDate = new Date();
            notif.setId(UUID.randomUUID().toString());
            notif.setAction(action);
            notif.setUserId(userId);
            notif.setUsername(currentUser.username());
            notif.setPhotoId(photoId);
            notif.setUnread(true);
            notif.setDatetime(currentDate);

            // Owner is the uploader of the photo.
            String dateString = new SimpleDateFormat("MMM dd, h:mm aaa").format(currentDate);
            notif.setOwnerId(ownerId);
            notif.setDateDisplay(dateString);

            this.notificationRepository.save(notif);
        }
    }

    @Override
    public boolean deleteComment(final IComment comment) {
        this.commentRepository.deleteByIdAndUserId(
                comment.id(),
                AuthUtils.getAuth().user().id());
        return true;
    }

    @Override
    public ILike toggleLike(final ILike like) {
        IUser user = AuthUtils.getAuth().user();
        long userId = user.id();
        long photoId = like.photoId();
        ILike result = this.likeRepository.findByPhotoIdAndUserId(photoId, userId);

        // If count is more than zero,
        // then un-like. Else, do like.
        if (result != null) {
            this.likeRepository.deleteById(result.id());
            return new Like();
        }

        // Save notification.
        saveNotification(user, photoId, ActionEnum.LIKE);

        Like mutable = (Like) like;
        mutable.setUserId(userId);
        mutable.setId(UUID.randomUUID().toString());
        return this.likeRepository.save(mutable);
    }
}
