/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.dao.impl;

import com.cebedo.vic.artdb.builder.UserBuilder;
import com.cebedo.vic.artdb.dao.UserDao;
import com.cebedo.vic.artdb.dto.ProfileDTO;
import com.cebedo.vic.artdb.model.User;
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

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
@Repository("userDao")
public class UserDaoImpl implements UserDao {

    // TODO Put character limits on DB columns.
    @Autowired
    private DataSource dataSource;

    @Override
    public void changePassword(String username, String newPassword) {
        try (Connection connection = dataSource.getConnection()) {
            // TODO Include user id in WHERE clause.
            PreparedStatement stmt = connection.prepareStatement("UPDATE users SET password = ? WHERE username = ?");
            stmt.setString(1, newPassword);
            stmt.setString(2, username);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateProfileCurrentUser(ProfileDTO profile) {
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
    public void create(User user) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO public.users(username, password, name, bio, website, email, phone) VALUES (?, ?, ?, ?, ?, ?, ?);");
            stmt.setString(1, user.username());
            stmt.setString(2, user.password());
            stmt.setString(3, "");
            stmt.setString(4, "");
            stmt.setString(5, "");
            stmt.setString(6, "");
            stmt.setString(7, "");
            stmt.executeUpdate();
        } catch (Exception e) {
            // TODO Error handling if fail, like duplicate username.
            e.printStackTrace();
        }
    }

    @Override
    public User get(String username) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new UserBuilder(
                        rs.getLong("id"),
                        username,
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("bio"),
                        rs.getString("website"),
                        rs.getString("email"),
                        rs.getString("phone")).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return UserBuilder.newInstance();
    }

    @Override
    public User get(long id) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM users WHERE id = ?");
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new UserBuilder(
                        id,
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("bio"),
                        rs.getString("website"),
                        rs.getString("email"),
                        rs.getString("phone")).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return UserBuilder.newInstance();
    }

    @Override
    public List<User> getAll() {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users");

            List<User> users = new ArrayList<>();
            while (rs.next()) {
                User user = new UserBuilder(
                        rs.getLong("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("name"),
                        "",
                        "",
                        "",
                        "").build();
                users.add(user);
            }

            return users;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
