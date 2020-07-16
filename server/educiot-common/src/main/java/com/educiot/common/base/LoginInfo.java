package com.educiot.common.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Jason Hong
 */
@Data
@ApiModel("登录用户信息基类")

@EqualsAndHashCode(callSuper = false)
public class LoginInfo extends BaseVO {

    @ApiModelProperty(value = "token信息")
    public String token;

}
