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
 * 小程序增加学生入参实体
 *
 * @author Pan Juncai
 * @version 1.0
 * @date 2020/4/28 14:43
 */
@Data
@ApiModel(value = "小程序增加学生入参实体")
@EqualsAndHashCode(callSuper = true)
public class AddBindStudentQuery extends BaseQuery {
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

    @ApiModelProperty(value = "学生姓名", name = "studentName", required = true)
    @NotBlank(message = "学生姓名不能为空")
    private String studentName;
}
