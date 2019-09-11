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
public final class ProfileImpl implements Profile {

    private final long id;
    private final String name;
    private final String bio;
    private final String website;
    private final String email;
    private final String phone;

    // TODO Convert to builder.
    public ProfileImpl(long i, String n, String b, String w, String e, String p) {
        this.id = i;
        this.name = n;
        this.bio = b;
        this.website = w;
        this.email = e;
        this.phone = p;
    }

    @Override
    public long id() {
        return this.id;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public String bio() {
        return this.bio;
    }

    @Override
    public String website() {
        return this.website;
    }

    @Override
    public String email() {
        return this.email;
    }

    @Override
    public String phone() {
        return this.phone;
    }

}
