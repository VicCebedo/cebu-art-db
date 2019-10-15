/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.service;

import com.cebedo.vic.artdb.dto.CommentDto;
import com.cebedo.vic.artdb.dto.LikeDto;
import com.cebedo.vic.artdb.dto.ResponseDto;
import com.cebedo.vic.artdb.model.Photo;
import java.util.List;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
public interface PhotoService {

    ResponseDto create(final Photo photo);

    ResponseDto delete(final long id, final String cloudName);

    ResponseDto updateCaption(final Photo photo);

    List<Photo> getPhotos(final int offset);

    List<Photo> getPhotosByCurrentUser(final int offset);

    List<Photo> getPhotosByUserId(final long id, final int offset);

    List<CommentDto> getCommentsByPhotoId(final long id);

    CommentDto createComment(final CommentDto comment);

    boolean deleteComment(final CommentDto comment);

    LikeDto toggleLike(final LikeDto like);

}
