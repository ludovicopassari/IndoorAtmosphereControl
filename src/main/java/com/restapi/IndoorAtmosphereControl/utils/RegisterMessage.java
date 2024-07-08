package com.restapi.IndoorAtmosphereControl.utils;

public class RegisterMessage extends Message {


    @Override
    public String getMessage(RegisterStatus status) {
        return super.getMessage()+status.toString();
    }
}