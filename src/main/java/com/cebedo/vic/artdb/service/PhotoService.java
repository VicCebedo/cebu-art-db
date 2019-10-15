/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.service;

import com.cebedo.vic.artdb.dto.CommentDto;
import com.cebedo.vic.artdb.dto.LikeDto;
import com.cebedo.vic.artdb.dto.PhotoDto;
import com.cebedo.vic.artdb.dto.ResponseDto;
import java.util.List;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
public interface PhotoService {

    ResponseDto create(PhotoDto photo);

    ResponseDto delete(long id, String cloudName);

    ResponseDto updateCaption(PhotoDto photo);

    List<PhotoDto> getPhotos(int offset);

    List<PhotoDto> getPhotosByCurrentUser(int offset);

    List<PhotoDto> getPhotosByUserId(long id, int offset);

    List<CommentDto> getCommentsByPhotoId(long id);

    CommentDto createComment(CommentDto comment);

    boolean deleteComment(CommentDto comment);

    LikeDto toggleLike(LikeDto like);

}
