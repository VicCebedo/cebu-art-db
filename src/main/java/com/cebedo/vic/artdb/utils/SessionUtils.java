/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
public class SessionUtils {

    public static int getAttributeInt(final HttpServletRequest request, final String attributeName) {
        HttpSession session = request.getSession();
        return session.getAttribute(attributeName) == null
                ? 0
                : (int) session.getAttribute(attributeName);
    }

    public static void setAttributeInt(final HttpServletRequest request, final String attributeName, int value) {
        HttpSession session = request.getSession();
        session.setAttribute(attributeName, value);
    }
}
