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
public final class UserImpl implements User {

    private final long id;
    private final long profileId;
    private final String username;
    private final String password;

    public UserImpl(long i, long pId, String u, String p) {
        this.id = i;
        this.profileId = pId;
        this.username = u;
        this.password = p;
    }

    @Override
    public long id() {
        return this.id;
    }

    @Override
    public long profileId() {
        return this.profileId;
    }

    @Override
    public String username() {
        return this.username;
    }

    @Override
    public String password() {
        return this.password;
    }

}
