/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.model;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
public interface User {

    long id();

    String username();

    String password();

    String name();

    String bio();

    String website();

    String email();

    String phone();
}
