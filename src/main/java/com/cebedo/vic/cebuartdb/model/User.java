/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.cebuartdb.model;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
public interface User {

    long id();

    long profileId();

    String username();

    String password();
}
