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
 * 【小程序】登录注册入参实体
 *
 * @author Pan Juncai
 * @version 1.0
 * @date 2020/4/28 14:43
 */
@Data
@ApiModel(value = "小程序绑定招生学校和学生入参实体")
@EqualsAndHashCode(callSuper = true)
public class BindRecruitAndStudentQuery extends BaseQuery {
    @ApiModelProperty(value = "获取openID", name = "openid", required = true)
    @NotBlank(message = "openid不能为空")
    private String openid;

    @ApiModelProperty(value = "招生学校ID", name = "recruitSchoolId", required = true)
    @NotNull(message = "招生学校ID不能为空")
    @Min(value = 1L, message = "招生学校ID不合法")
    @Max(value = Long.MAX_VALUE, message = "招生学校ID不合法")
    private Long recruitSchoolId;

    @ApiModelProperty(value = "生源学校ID", name = "sourceSchoolId")
    @NotNull(message = "生源学校ID不能为空")
    @Min(value = 1L, message = "生源学校ID不合法")
    @Max(value = Long.MAX_VALUE, message = "生源学校ID不合法")
    private Long sourceSchoolId;

    @ApiModelProperty(value = "班级ID", name = "classId")
    @NotNull(message = "班级ID不能为空")
    @Min(value = 1L, message = "班级ID不合法")
    @Max(value = Long.MAX_VALUE, message = "班级ID不合法")
    private Long classId;

    @ApiModelProperty(value = "学生ID", name = "studentInfo", required = true)
    @NotNull(message = "学生ID不能为空")
    @Min(value = 1, message = "学生ID不合法")
    @Max(value = Long.MAX_VALUE, message = "学生ID不合法")
    private Long studentInfo;
}
