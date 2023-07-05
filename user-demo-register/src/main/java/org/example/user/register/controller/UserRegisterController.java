package org.example.user.register.controller;

import cn.hutool.http.Status;
import io.swagger.annotations.*;
import org.example.user.register.model.EmailCheckResult;
import org.example.user.register.model.UserResponse;
import org.example.user.register.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@RestController
@RequestMapping(path = "/user/register")
@Api(tags = {"Users Register Endpoints"})
public class UserRegisterController {

    @Autowired
    private UserService userService;

    @ApiOperation(
            value = "Check if a email has been registered",
            produces = MediaType.APPLICATION_JSON_VALUE,
            response = UserResponse.class
    )
    @ApiResponses({
            @ApiResponse(code = Status.HTTP_OK,
                    message = "The user is registered successfully",
                    response = EmailCheckResult.class
            ),
            @ApiResponse(code = Status.HTTP_BAD_REQUEST, message = "If any fields are missing from the request."),
            @ApiResponse(code = Status.HTTP_INTERNAL_ERROR, message = "Internal server error"),
            @ApiResponse(code = Status.HTTP_UNAVAILABLE, message = "Service is down, Caller should wait and retry.")
    })
    @GetMapping(value = "/check",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public EmailCheckResult valid(
            @ApiParam(value = "The user email",required = true)
            @RequestParam(value = "email") String email){
        return userService.checkEmail(email);
    }

    @ApiOperation(
            value = "Register a user system through email and password",
            produces = MediaType.APPLICATION_JSON_VALUE,
            response = UserResponse.class
    )
    @ApiResponses({
            @ApiResponse(code = Status.HTTP_OK,
                    message = "The user is registered successfully",
                    response = UserResponse.class
            ),
            @ApiResponse(code = Status.HTTP_BAD_REQUEST, message = "If any fields are missing from the request."),
            @ApiResponse(code = Status.HTTP_INTERNAL_ERROR, message = "Internal server error"),
            @ApiResponse(code = Status.HTTP_UNAVAILABLE, message = "Service is down, Caller should wait and retry.")
    })
    @PostMapping(
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public UserResponse register(
            @ApiParam(value = "The user email", required = true)
            @NotEmpty
            @RequestParam(value = "email") String email,
            @ApiParam(value = "The user password", required = true)
            @NotBlank
            @RequestParam(value = "password") String password,
            @ApiParam(value = "The user first name", required = false)
            @RequestParam(value = "first_name", required = false) String firstName,
            @ApiParam(value = "The user last name", required = false)
            @RequestParam(value = "last_name", required = false) String lastName,
            @ApiParam(value = "The user birth date", required = false)
            @RequestParam(value = "birthdate", required = false) String birthdate,
            @ApiParam(value = "The user gender", required = false)
            @RequestParam(value = "gender", required = false) String gender,
            @ApiParam(value = "The user zipcode", required = false)
            @RequestParam(value = "zipcode", required = false) Integer zipcode){

        return userService.create(email,password,firstName, lastName, birthdate, gender, zipcode);

    }
}
