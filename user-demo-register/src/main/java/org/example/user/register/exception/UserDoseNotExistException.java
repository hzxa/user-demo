package org.example.user.register.exception;

public class UserDoseNotExistException extends RuntimeException{
    public UserDoseNotExistException(){
        super("User dose not exist");
    }
}
