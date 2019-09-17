/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.dao.impl;

import com.cebedo.vic.artdb.builder.PhotoBuilder;
import com.cebedo.vic.artdb.dao.PhotoDao;
import com.cebedo.vic.artdb.model.Photo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
@Repository("photoDao")
public class PhotoDaoImpl implements PhotoDao {

    @Autowired
    private DataSource dataSource;

    @Override
    public void create(Photo photo) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO photos(user_id, url, caption) VALUES (?, ?, ?)");
            stmt.setLong(1, photo.userId());
            stmt.setString(2, photo.url());
            stmt.setString(3, photo.caption());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Photo> getAllByUserId(long userId) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM photos WHERE user_id = ?");
            stmt.setLong(1, userId);

            ResultSet rs = stmt.executeQuery();
            List<Photo> photos = new ArrayList<>();
            while (rs.next()) {
                photos.add(new PhotoBuilder(
                        rs.getLong("id"),
                        rs.getLong("user_id"),
                        rs.getString("url"),
                        rs.getString("caption")).build());
            }

            return photos;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

}
