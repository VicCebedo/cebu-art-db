package com.cebedo.vic.artdb.repository;

import com.cebedo.vic.artdb.dto.CommentDto;
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
public interface CommentRepository extends MongoRepository<CommentDto, String> {

    List<CommentDto> findByPhotoId(long photoId);

}
