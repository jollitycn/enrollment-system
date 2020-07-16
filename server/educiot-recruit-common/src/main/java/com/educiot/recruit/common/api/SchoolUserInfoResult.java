package com.educiot.recruit.common.api;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Jason Hong
 */
@Data
@ApiModel(value = "SchoolUserInfoResult")
@EqualsAndHashCode(callSuper = false)
public class SchoolUserInfoResult extends APIResult {
    private UserInfo data;
}