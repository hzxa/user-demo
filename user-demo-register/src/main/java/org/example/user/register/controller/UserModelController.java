package org.example.user.register.controller;

import cn.hutool.http.Status;
import io.swagger.annotations.*;
import org.example.user.core.domain.UserModel;
import org.example.user.register.model.ChangePasswordResponse;
import org.example.user.register.service.UserModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user/model")
@Api(tags = {"Users Model Endpoints"})
public class UserModelController {

    @Autowired
    private UserModelService userModelService;

    @ApiOperation(
            value = "Query the user information",
            produces = MediaType.APPLICATION_JSON_VALUE,
            response = UserModel.class
    )
    @ApiResponses({
            @ApiResponse(code = Status.HTTP_OK,
                    message = "The user information",
                    response = UserModel.class
            ),
            @ApiResponse(code = Status.HTTP_BAD_REQUEST, message = "If any fields are missing from the request."),
            @ApiResponse(code = Status.HTTP_INTERNAL_ERROR, message = "Internal server error"),
            @ApiResponse(code = Status.HTTP_UNAVAILABLE, message = "Service is down, Caller should wait and retry.")
    })

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public UserModel query(@RequestParam(value = "user_id") Long userId){
        return userModelService.query(userId);
    }

    @ApiOperation(
            value = "Update the user information",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            response = UserModel.class
    )
    @ApiResponses({
            @ApiResponse(code = Status.HTTP_OK,
                    message = "The user information is updated",
                    response = UserModel.class
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
    public UserModel update(
            @ApiParam(value = "The user id")
            @RequestParam(value="user_id") Long userId,
            @ApiParam(value = "The first name")
            @RequestParam(value="first_name", required = false) String firstName,
            @ApiParam(value = "The user last name")
            @RequestParam(value="last_name", required = false) String lastName,
            @ApiParam(value = "The user birthdate", example = "20230101")
            @RequestParam(value="birthdate", required = false) String birthdate,
            @ApiParam(value = "The user gender")
            @RequestParam(value="gender",required = false) String gender,
            @ApiParam(value = "The user zipcode")
            @RequestParam(value = "zipcode", required = false) Integer zipcode){
        return userModelService.update(userId,firstName,lastName,birthdate,gender,zipcode);
    }
}
