/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.service;

import com.cebedo.vic.artdb.model.impl.Comment;
import com.cebedo.vic.artdb.model.impl.Like;
import com.cebedo.vic.artdb.dto.ResponseDto;
import java.util.List;
import com.cebedo.vic.artdb.model.IPhoto;

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

    Comment createComment(final Comment comment);

    boolean deleteComment(final Comment comment);

    Like toggleLike(final Like like);

}
