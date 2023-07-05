package org.example.user.register.controller;

import cn.hutool.http.Status;
import io.swagger.annotations.*;
import org.example.user.register.model.UserResponse;
import org.example.user.register.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
@Api(tags = {"Users Login Endpoints"})
public class UserLoginController {


    @Autowired
    private UserService userService;


    @ApiOperation(
            value = "Login system through email and password",
            produces = MediaType.APPLICATION_JSON_VALUE,
            response = UserResponse.class
    )
    @ApiResponses({
            @ApiResponse(code = Status.HTTP_OK,
                    message = "Successfully login and return the user information",
                    response = UserResponse.class
            ),
            @ApiResponse(code = Status.HTTP_BAD_REQUEST, message = "If any fields are missing from the request."),
            @ApiResponse(code = Status.HTTP_FORBIDDEN, message = "If login credentials are not valid."),
            @ApiResponse(code = Status.HTTP_INTERNAL_ERROR, message = "Internal server error"),
            @ApiResponse(code = Status.HTTP_UNAVAILABLE, message = "Service is down, Caller should wait and retry.")
    })
    @PostMapping(
            value = "/password/auth",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public UserResponse login(
            @ApiParam(value = "The user email", required = true)
            @RequestParam(value = "email") String email,
            @ApiParam(value = "The user password", required = true)
            @RequestParam(value = "password") String password) {

        return userService.login(email, password);

    }
}
