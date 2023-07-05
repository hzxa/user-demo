package org.example.user.core.domain;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * User information data used by admin
 *
 * @author zhixiong.huang
 */

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserInfo {
    private Long userId;
    private String email;
    private String firstName;
    private String lastName;
    private String birthdate;
    private String gender;
    private Integer zipcode;

    private boolean deleted;
    private Date gmtCreate;
    private Date gmtModified;

}
