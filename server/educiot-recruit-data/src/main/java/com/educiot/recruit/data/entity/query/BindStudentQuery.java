package com.educiot.recruit.data.entity.query;

import com.educiot.common.base.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Pan Juncai
 * @version 1.0
 * @date 2020/4/29 13:55
 */
@Data
@ApiModel(value = "小程序登录注册页面绑定学生入参实体")
@EqualsAndHashCode(callSuper = true)
public class BindStudentQuery extends BaseQuery {
    @ApiModelProperty(value = "登录类型（0：学生，1：家长）", name = "loginType", required = true)
    @NotNull(message = "登录类型不能为空")
    @Min(value = 0, message = "登录类型（0：学生，1：家长）")
    @Max(value = 1, message = "登录类型（0：学生，1：家长）")
    private Integer loginType;

    @ApiModelProperty(value = "学生姓名", name = "studentName", required = true)
    @NotBlank(message = "学生姓名不能为空")
    private String studentName;

    @ApiModelProperty(value = "家长手机", name = "parentTelephone", required = true)
    @NotBlank(message = "家长手机不能为空")
    private String parentTelephone;

    @ApiModelProperty(value = "openid", name = "openid", required = true)
    @NotBlank(message = "openid不能为空")
    private String openid;

//    @ApiModelProperty(value = "招生学校ID", name = "recruitSchoolId", required = true)
//    @NotNull(message = "招生学校ID不能为空")
//    @Min(value = 1L, message = "招生学校ID不合法")
//    @Max(value = Long.MAX_VALUE, message = "招生学校ID不合法")
//    private Long recruitSchoolId;
}
