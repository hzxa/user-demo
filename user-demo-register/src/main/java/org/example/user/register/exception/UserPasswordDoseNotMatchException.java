package org.example.user.register.exception;

public class UserPasswordDoseNotMatchException extends RuntimeException{
    public UserPasswordDoseNotMatchException() {
        super("password is not correct");
    }
}
