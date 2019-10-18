package com.cebedo.vic.artdb.repository;

import com.cebedo.vic.artdb.model.impl.Comment;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
public interface CommentRepository extends MongoRepository<Comment, String> {

    List<Comment> findByPhotoIdOrderByDatetimeAsc(final long photoId);

    long countByPhotoId(final long photoId);

    void deleteByIdAndUserId(final String id, final long userId);

    void deleteByPhotoId(final long photoId);

}
