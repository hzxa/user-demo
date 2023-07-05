package org.example.user.admin.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import org.example.user.core.domain.UserInfo;

import java.util.List;

@Builder
@Data
@ApiModel
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserInfoResponse {
    @ApiModelProperty(position = 0, value = "Total number", required = true)
    private Long total;
    @ApiModelProperty(position = 1, value = "User information", required = false)
    private List<UserInfo> users;
}
