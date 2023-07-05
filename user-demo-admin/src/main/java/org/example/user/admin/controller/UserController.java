package org.example.user.admin.controller;

import cn.hutool.http.Status;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.example.user.admin.model.UserInfoResponse;
import org.example.user.admin.model.UserWelcomeMail;
import org.example.user.admin.service.UserService;
import org.example.user.admin.model.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Rest controller definition for user admin backend
 *
 * @author zhixiong.huang
 */
@RestController
@Slf4j
@RequestMapping("/admin/users")
@Api(tags = {"Users Management Endpoints"})
public class UserController {


    @Autowired
    private UserService userService;

    @ApiOperation(
            value = "Query the registered users",
            produces = MediaType.APPLICATION_JSON_VALUE,
            response = UserInfoResponse.class
    )
    @ApiResponses({
            @ApiResponse(code = Status.HTTP_OK,
                    message = "Successfully and return users data",
                    response = UserInfoResponse.class
            ),
            @ApiResponse(code = Status.HTTP_BAD_REQUEST, message = "If any fields are missing from the request."),
            @ApiResponse(code = Status.HTTP_INTERNAL_ERROR, message = "Internal server error."),
            @ApiResponse(code = Status.HTTP_UNAVAILABLE, message = "Service is down, Caller should wait and retry.")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public UserInfoResponse query(
            @ApiParam("The registered email address")
            @RequestParam(value = "email", required = false) String email,
            @ApiParam("Whether the user is deleted")
            @RequestParam(value = "deleted", required = false) boolean deleted,
            @ApiParam(value = "The page number, start from 1", required = true)
            @RequestParam(value = "pn") Integer pn,
            @ApiParam(value = "The page size", required = true)
            @RequestParam(value = "ps") Integer ps){
        return userService.list(email,deleted,pn, ps);

    }

    @ApiOperation(
            value = "Delete the registered users"
    )
    @ApiResponses({
            @ApiResponse(code = Status.HTTP_OK,
                    message = "The users are deleted successfully"
            ),
            @ApiResponse(code = Status.HTTP_BAD_REQUEST, message = "If any fields are missing from the request."),
            @ApiResponse(code = Status.HTTP_INTERNAL_ERROR, message = "Internal server error."),
            @ApiResponse(code = Status.HTTP_UNAVAILABLE, message = "Service is down, Caller should wait and retry.")
    })
    @DeleteMapping(value = "/{ids}")
    public void delete(
            @ApiParam(value = "The user ids to be deleted, delimiter is comma, for example 1,2,3", example = "1,2,3", required = true)
            @PathVariable(value = "ids",required = true) String ids){
        log.info("ids:{}", ids);
        String[] tokens = ids.split(",");
        userService.delete(Arrays.stream(tokens).map(id->Long.valueOf(id)).collect(Collectors.toList()));
    }

    @ApiOperation(
            value = "Query the welcome email content sent to the user",
            produces = MediaType.APPLICATION_JSON_VALUE,
            response = UserWelcomeMail.class
    )
    @ApiResponses({
            @ApiResponse(code = Status.HTTP_OK,
                    message = "Successfully and return the mail content",
                    response = UserWelcomeMail.class
            ),
            @ApiResponse(code = Status.HTTP_BAD_REQUEST, message = "If any fields are missing from the request."),
            @ApiResponse(code = Status.HTTP_INTERNAL_ERROR, message = "Internal server error"),
            @ApiResponse(code = Status.HTTP_UNAVAILABLE, message = "Service is down, Caller should wait and retry.")
    })
    @GetMapping(value = "/welcome-mail")
    public UserWelcomeMail welcomeMail(
            @ApiParam(value = "The user id", example = "1234", required = true)
            @RequestParam(value = "user_id") Long userId){

        return userService.welcomeEmail(userId);
    }
}
