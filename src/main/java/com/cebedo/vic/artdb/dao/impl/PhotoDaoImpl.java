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
import com.cebedo.vic.artdb.model.ILike;
import com.cebedo.vic.artdb.model.impl.User;
import com.cebedo.vic.artdb.repository.CommentRepository;
import com.cebedo.vic.artdb.repository.LikeRepository;
import com.cebedo.vic.artdb.utils.AuthUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.cebedo.vic.artdb.model.IPhoto;
import com.cebedo.vic.artdb.model.IUser;
import com.cebedo.vic.artdb.model.impl.Comment;
import com.cebedo.vic.artdb.model.impl.Photo;

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

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private LikeRepository likeRepository;

    @Override
    public void create(IPhoto photo) {
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
    public void updateCaption(IPhoto photo) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "UPDATE photos "
                    + "SET caption = ? "
                    + "WHERE id = ? AND user_id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, photo.caption());
            stmt.setLong(2, photo.id());
            stmt.setLong(3, AuthUtils.getAuth().user().id());
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
    public List<IPhoto> getPhotosByUserId(long userId, int offset) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM photos "
                    + "WHERE user_id = ? "
                    + "ORDER BY timestamp DESC "
                    + "LIMIT 5 OFFSET " + offset);
            stmt.setLong(1, userId);

            ResultSet rs = stmt.executeQuery();
            List<IPhoto> photos = new ArrayList<>();
            IUser user = this.userDao.get(userId);

            while (rs.next()) {
                long photoId = rs.getLong("id");
                long commentCount = this.commentRepository.countByPhotoId(photoId);
                ILike like = this.likeRepository.findByPhotoIdAndUserId(photoId, userId);
                long likeCount = this.likeRepository.countByPhotoId(photoId);

                photos.add(new PhotoBuilder(
                        photoId,
                        rs.getString("url"),
                        rs.getString("caption"),
                        rs.getTimestamp("timestamp"),
                        user,
                        commentCount,
                        likeCount,
                        like != null)
                        .build());
            }

            return photos;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public List<IPhoto> getPhotos(int offset) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT photos.id, photos.url, photos.caption, photos.timestamp, users.username, users.name\n"
                    + "FROM photos\n"
                    + "INNER JOIN users ON photos.user_id = users.id\n"
                    + "ORDER BY timestamp DESC\n"
                    + "LIMIT 5 OFFSET " + offset;
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            List<IPhoto> photos = new ArrayList<>();
            long userId = AuthUtils.getAuth().user().id();

            while (rs.next()) {

                User usr = new User();
                usr.setUsername(rs.getString("username"));
                usr.setName(rs.getString("name"));

                long photoId = rs.getLong("id");
                long commentCount = this.commentRepository.countByPhotoId(photoId);
                long likeCount = this.likeRepository.countByPhotoId(photoId);
                ILike like = this.likeRepository.findByPhotoIdAndUserId(photoId, userId);

                photos.add(new PhotoBuilder(
                        photoId,
                        rs.getString("url"),
                        rs.getString("caption"),
                        rs.getTimestamp("timestamp"),
                        usr,
                        commentCount,
                        likeCount,
                        like != null)
                        .build());
            }

            return photos;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public IPhoto getPartial(final long id) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM photos WHERE id = ?");
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User usr = new User();
                usr.setId(rs.getLong("user_id"));
                Photo photo = (Photo) PhotoBuilder.newInstance();
                photo.setUser(usr);
                photo.setUrl(rs.getString("url"));
                return photo;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Photo();
    }

    @Override
    public IPhoto get(final long id) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM photos WHERE id = ?");
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                long photoId = rs.getLong("id");
                long commentCount = this.commentRepository.countByPhotoId(photoId);
                long likeCount = this.likeRepository.countByPhotoId(photoId);
                long userId = AuthUtils.getAuth().user().id();
                ILike like = this.likeRepository.findByPhotoIdAndUserId(photoId, userId);
                List<Comment> comments = this.commentRepository.findByPhotoIdOrderByDatetimeAsc(id);

                return new PhotoBuilder(
                        photoId,
                        rs.getString("url"),
                        rs.getString("caption"),
                        rs.getTimestamp("timestamp"),
                        UserBuilder.newInstance(),
                        commentCount,
                        likeCount,
                        like != null)
                        .withComments(comments)
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Photo();
    }

}
