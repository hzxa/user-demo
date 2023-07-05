package org.example.user.register.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.user.register.exception.UserDoseNotExistException;
import org.example.user.register.exception.UserPasswordDoseNotMatchException;
import org.example.user.register.model.ErrorCode;
import org.example.user.register.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author zhixiong.huang
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler({UserDoseNotExistException.class})
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ResponseBody
    public Object handleUserDoseNotExist(Exception e){
        return ErrorResponse.builder()
                .errorCode(ErrorCode.USER_DOSE_NOT_EXIST.getCode())
                .msg(ErrorCode.USER_DOSE_NOT_EXIST.getMsg())
                .build();
    }

    @ExceptionHandler({UserPasswordDoseNotMatchException.class})
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ResponseBody
    public Object handlePasswordInvalid(Exception e){
        return ErrorResponse.builder()
                .errorCode(ErrorCode.USER_PASSWORD_INVALID.getCode())
                .msg(ErrorCode.USER_PASSWORD_INVALID.getMsg())
                .build();
    }

//    @ExceptionHandler({Exception.class})
//    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
//    @ResponseBody
//    public void handleDefaultException(Exception e){
//        log.error("Exception:{}",e);

//    }
}
