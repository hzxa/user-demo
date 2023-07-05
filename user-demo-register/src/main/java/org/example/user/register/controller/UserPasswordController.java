package org.example.user.register.controller;

import cn.hutool.http.Status;
import io.swagger.annotations.*;
import org.example.user.register.model.ChangePasswordResponse;
import org.example.user.register.service.UserPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "user/password")
@Api(tags = {"Users Password Endpoints"})
public class UserPasswordController {

    @Autowired
    private UserPasswordService service;

    @ApiOperation(
            value = "Change the user password",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            response = ChangePasswordResponse.class
    )
    @ApiResponses({
            @ApiResponse(code = Status.HTTP_OK,
                    message = "The password is changed",
                    response = ChangePasswordResponse.class
            ),
            @ApiResponse(code = Status.HTTP_BAD_REQUEST, message = "If any fields are missing from the request."),
            @ApiResponse(code = Status.HTTP_FORBIDDEN, message = "If login credentials are not valid."),
            @ApiResponse(code = Status.HTTP_INTERNAL_ERROR, message = "Internal server error"),
            @ApiResponse(code = Status.HTTP_UNAVAILABLE, message = "Service is down, Caller should wait and retry.")
    })
    @PostMapping(
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ChangePasswordResponse changePassword(
            @ApiParam(value = "The user id")
            @RequestParam(value = "user_id") Long userId,
            @ApiParam(value = "The old password")
            @RequestParam(value = "old_password") String oldPassword,
            @ApiParam(value = "The new password")
            @RequestParam(value = "new_password") String newPassword){
        return service.changePassword(userId, oldPassword, newPassword);
    }
}
