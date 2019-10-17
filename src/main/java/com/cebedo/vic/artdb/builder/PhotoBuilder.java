/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.builder;

import com.cebedo.vic.artdb.model.impl.Photo;
import java.sql.Timestamp;
import java.util.Objects;
import com.cebedo.vic.artdb.model.IPhoto;
import com.cebedo.vic.artdb.model.IUser;
import com.cebedo.vic.artdb.model.impl.Comment;
import java.util.List;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
public final class PhotoBuilder {

    private final long id;
    private final String url;
    private final String caption;
    private final Timestamp timestamp;
    private final IUser user;
    private final long commentCount;
    private final long likeCount;
    private final boolean liked;
    private List<Comment> comments;

    public PhotoBuilder(
            final long i,
            final String u,
            final String c,
            final Timestamp t,
            final IUser user,
            final long cCount,
            final long lCount,
            final boolean isLiked) {

        Objects.requireNonNull(i);
        Objects.requireNonNull(u);
        Objects.requireNonNull(c);
        Objects.requireNonNull(t);
        Objects.requireNonNull(user);
        Objects.requireNonNull(cCount);
        Objects.requireNonNull(lCount);
        Objects.requireNonNull(isLiked);

        this.id = i;
        this.url = u;
        this.caption = c;
        this.timestamp = t;
        this.user = user;
        this.commentCount = cCount;
        this.likeCount = lCount;
        this.liked = isLiked;
    }

    public IPhoto build() {
        return new Photo(this);
    }

    public static IPhoto newInstance() {
        return new Photo(new PhotoBuilder(
                0,
                "",
                "",
                new Timestamp(System.currentTimeMillis()),
                UserBuilder.newInstance(),
                0,
                0,
                false
        ));
    }

    public long id() {
        return this.id;
    }

    public long commentCount() {
        return this.commentCount;
    }

    public long likeCount() {
        return this.likeCount;
    }

    public String url() {
        return this.url;
    }

    public String caption() {
        return this.caption;
    }

    public Timestamp timestamp() {
        return this.timestamp;
    }

    public IUser user() {
        return this.user;
    }

    public boolean liked() {
        return this.liked;
    }

    public List<Comment> comments() {
        return this.comments;
    }

    public PhotoBuilder withComments(List<Comment> c) {
        this.comments = c;
        return this;
    }

}
