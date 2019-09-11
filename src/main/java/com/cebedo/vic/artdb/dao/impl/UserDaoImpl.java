/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.dao.impl;

import com.cebedo.vic.artdb.builder.UserBuilder;
import com.cebedo.vic.artdb.dao.UserDao;
import com.cebedo.vic.artdb.model.User;
import java.sql.Connection;
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

    @Autowired
    private DataSource dataSource;

    @Override
    public List<User> users() {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users");

            List<User> users = new ArrayList<>();
            while (rs.next()) {
                User user = new UserBuilder(
                        rs.getLong("id"),
                        rs.getString("username"),
                        rs.getString("password")).build();
                users.add(user);
            }

            return users;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }
}
