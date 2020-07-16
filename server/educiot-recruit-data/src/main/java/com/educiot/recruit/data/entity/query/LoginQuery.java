package com.educiot.recruit.data.entity.query;

import com.educiot.recruit.common.base.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * 【小程序】登录注册入参实体
 *
 * @author Pan Juncai
 * @version 1.0
 * @date 2020/4/28 14:43
 */
@Data
@ApiModel(value = "小程序登录入参实体")
@EqualsAndHashCode(callSuper = true)
public class LoginQuery extends BaseQuery {
    @ApiModelProperty(value = "获取openID的code", name = "code", required = true)
    @NotBlank(message = "code不能为空")
    private String code;

    @ApiModelProperty(value = "招生学校ID", name = "recruitSchoolId")
    private Long recruitSchoolId;

    @ApiModelProperty(value = "招生宣讲计划ID", name = "preachPlanId")
    private Long preachPlanId;
}
