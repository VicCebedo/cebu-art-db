/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.service;

import com.cebedo.vic.artdb.dto.ResponseDto;
import com.cebedo.vic.artdb.model.IComment;
import com.cebedo.vic.artdb.model.ILike;
import java.util.List;
import com.cebedo.vic.artdb.model.IPhoto;
import com.cebedo.vic.artdb.model.impl.Comment;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
public interface PhotoService {

    ResponseDto create(final IPhoto photo);

    ResponseDto delete(final long id, final String cloudName);

    ResponseDto updateCaption(final IPhoto photo);

    List<IPhoto> getPhotos(final int offset);

    List<IPhoto> getPhotosByCurrentUser(final int offset);

    List<IPhoto> getPhotosByUserId(final long id, final int offset);

    List<Comment> getCommentsByPhotoId(final long id);

    IComment createComment(final IComment comment);

    boolean deleteComment(final IComment comment);

    ILike toggleLike(final ILike like);

    IPhoto readNotification(final String uuid, final long id);

}
