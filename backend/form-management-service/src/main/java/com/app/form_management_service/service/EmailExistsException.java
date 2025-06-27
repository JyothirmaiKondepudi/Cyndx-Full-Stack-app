package com.app.form_management_service.service;

public class EmailExistsException extends Exception{
    public EmailExistsException(String msg){
        super(msg);
    }

}
