package com.springsmallads.exception;

public class UserExistsNotException extends Exception{
    public UserExistsNotException() {
        super("User Does Not Exist");
    }
}
