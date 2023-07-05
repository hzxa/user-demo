package org.example.user.admin.model;


import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
@ApiModel
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserWelcomeMail {
    @ApiModelProperty(position = 0, value = "The user ID", required = true)
    private Long userId;

    @ApiModelProperty(position = 1, value = "The email file storage path if have", required = false)
    private String path;

    @ApiModelProperty(position = 2, value = "The email content if have", required = false)
    private String content;

    @ApiModelProperty(position = 2, value = "The email sent date", required = false)
    private Date sentDate;
}
