/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.dao.impl;

import com.cebedo.vic.artdb.builder.UserBuilder;
import com.cebedo.vic.artdb.dao.UserDao;
import com.cebedo.vic.artdb.dto.ProfileDto;
import com.cebedo.vic.artdb.utils.AuthUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.cebedo.vic.artdb.model.IUser;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
@Repository("userDao")
public class UserDaoImpl implements UserDao {

    @Autowired
    private DataSource dataSource;

    @Override
    public void changePassword(final String newPassword) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("UPDATE users SET password = ? WHERE username = ?");
            stmt.setString(1, newPassword);
            stmt.setString(2, AuthUtils.getAuth().user().username().toLowerCase());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateProfileCurrentUser(final ProfileDto profile) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("UPDATE users SET name=?, bio=?, website=?, email=?, phone=? WHERE id=?");
            stmt.setString(1, profile.getName());
            stmt.setString(2, profile.getBio());
            stmt.setString(3, profile.getWebsite());
            stmt.setString(4, profile.getEmail());
            stmt.setString(5, profile.getPhone());
            stmt.setLong(6, AuthUtils.getAuth().user().id());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateProfilePhoto(final String profilePic) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("UPDATE users SET profile_pic=? WHERE id=?");
            stmt.setString(1, profilePic);
            stmt.setLong(2, AuthUtils.getAuth().user().id());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void decrementInviteCodeRemaining(final String code, final int newRemaining) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("UPDATE invite_codes SET remaining = ? WHERE code = ?");
            stmt.setInt(1, newRemaining);
            stmt.setString(2, code);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void create(final IUser user) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO public.users(username, password, name, bio, website, email, phone, profile_pic, artist) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            stmt.setString(1, user.username().toLowerCase());
            stmt.setString(2, user.password());
            stmt.setString(3, "");
            stmt.setString(4, "");
            stmt.setString(5, "");
            stmt.setString(6, "");
            stmt.setString(7, "");
            stmt.setString(8, "");
            stmt.setBoolean(9, user.artist());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public IUser get(final String username) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            stmt.setString(1, username.toLowerCase());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new UserBuilder(
                        rs.getLong("id"),
                        username.toLowerCase(),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("bio"),
                        rs.getString("website"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("profile_pic"),
                        rs.getBoolean("artist")).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return UserBuilder.newInstance();
    }

    @Override
    public int getInviteCodeRemaining(final String code) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM invite_codes WHERE code = ?");
            stmt.setString(1, code);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("remaining");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public IUser get(final long id) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM users WHERE id = ?");
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new UserBuilder(
                        id,
                        rs.getString("username").toLowerCase(),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("bio"),
                        rs.getString("website"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("profile_pic"),
                        rs.getBoolean("artist")).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return UserBuilder.newInstance();
    }

    @Override
    public List<IUser> getUsers(final int offset) {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE artist = true LIMIT 5 OFFSET " + offset);

            List<IUser> users = new ArrayList<>();
            while (rs.next()) {
                IUser user = new UserBuilder(
                        rs.getLong("id"),
                        rs.getString("username").toLowerCase(),
                        "",
                        rs.getString("name"),
                        rs.getString("bio"),
                        rs.getString("website"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("profile_pic"),
                        rs.getBoolean("artist")).build();
                users.add(user);
            }

            return users;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
