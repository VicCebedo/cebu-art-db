/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.dao.impl;

import com.cebedo.vic.artdb.builder.PhotoBuilder;
import com.cebedo.vic.artdb.builder.UserBuilder;
import com.cebedo.vic.artdb.dao.PhotoDao;
import com.cebedo.vic.artdb.dao.UserDao;
import com.cebedo.vic.artdb.model.Photo;
import com.cebedo.vic.artdb.model.User;
import com.cebedo.vic.artdb.utils.AuthUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Comparator;
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

    @Autowired
    private UserDao userDao;

    @Override
    public void create(Photo photo) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO photos(user_id, url, caption, timestamp) VALUES (?, ?, ?, ?)");
            stmt.setLong(1, AuthUtils.getAuth().user().id());
            stmt.setString(2, photo.url());
            stmt.setString(3, photo.caption());
            stmt.setTimestamp(4, photo.timestamp());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(long id) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM photos WHERE id = ? AND user_id = ?");
            stmt.setLong(1, id);
            stmt.setLong(2, AuthUtils.getAuth().user().id());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Photo> getAllByUserId(long userId) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM photos WHERE user_id = ? ORDER BY timestamp DESC");
            stmt.setLong(1, userId);

            ResultSet rs = stmt.executeQuery();
            List<Photo> photos = new ArrayList<>();
            User user = this.userDao.get(userId);

            while (rs.next()) {
                photos.add(new PhotoBuilder(
                        rs.getLong("id"),
                        rs.getString("url"),
                        rs.getString("caption"),
                        rs.getTimestamp("timestamp"),
                        user).build());
            }

            return photos;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public List<Photo> getAll() {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT photos.id, photos.url, photos.caption, photos.timestamp, users.username, users.name\n"
                    + "FROM photos\n"
                    + "INNER JOIN users ON photos.user_id = users.id\n"
                    + "ORDER BY timestamp DESC";
            PreparedStatement stmt = connection.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();
            List<Photo> photos = new ArrayList<>();

            while (rs.next()) {
                User user = new UserBuilder(0, rs.getString("username"), "", rs.getString("name"), "", "", "", "").build();
                photos.add(new PhotoBuilder(
                        rs.getLong("id"),
                        rs.getString("url"),
                        rs.getString("caption"),
                        rs.getTimestamp("timestamp"),
                        user).build());
            }

            return photos;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

}
