package org.example.user.register.model;


import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@ApiModel
public enum ErrorCode {

    EMAIL_EXISTED(1000, "email is registered"),
    EMAIL_FORBIDDEN(1001, "email is forbidden by admin"),
    EMAIL_BAD_FORMAT(1002, "bad email format"),

    USER_DOSE_NOT_EXIST(2000, "user dose not exist"),
    USER_PASSWORD_INVALID(2001, "Password dose not match");



    private int code;
    private String msg;

    ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
