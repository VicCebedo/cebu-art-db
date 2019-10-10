/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.utils;

import com.cebedo.vic.artdb.config.WebAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
public class AuthUtils {

    public static WebAuthenticationToken getAuth() {
        return (WebAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
    }

    public static boolean isArtist() {
        WebAuthenticationToken token = (WebAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return token.user().artist();
    }

    public static boolean isAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication() instanceof WebAuthenticationToken;
    }

}
