/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
public class ResponseDto {

    private List<String> errors;
    private List<String> messages;

    public static ResponseDto newInstanceWithErrors(List<String> errors) {
        ResponseDto dto = new ResponseDto();
        dto.setErrors(errors);
        return dto;
    }

    public static ResponseDto newInstanceWithMessage(String msg) {
        ResponseDto dto = new ResponseDto();
        dto.addMessage(msg);
        return dto;
    }

    public static ResponseDto newInstanceWithError(String msg) {
        ResponseDto dto = new ResponseDto();
        dto.addError(msg);
        return dto;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public void addMessage(String msg) {
        if (this.messages == null) {
            this.messages = new ArrayList<>();
        }
        this.messages.add(msg);
    }

    public void addError(String msg) {
        if (this.errors == null) {
            this.errors = new ArrayList<>();
        }
        this.errors.add(msg);
    }

}
