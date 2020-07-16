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
 * @date 2020/4/10 10:42
 */
@Data
@ApiModel(value = "新增班级信息列表入参实体")
@EqualsAndHashCode(callSuper = true)
public class AddClassQuery extends BaseQuery {

    @ApiModelProperty(value = "生源学校ID", name = "sourceSchoolId", required = true)
    @NotNull(message = "生源学校ID不能为空")
    @Min(value = 1, message = "生源学校ID不合法")
    @Max(value = Long.MAX_VALUE, message = "生源学校ID不合法")
    private Long sourceSchoolId;

    @ApiModelProperty(value = "班级名称", name = "className", required = true)
    @NotBlank(message = "班级名称不能为空")
    private String className;

    @ApiModelProperty(value = "公关等级（0：高，1：中，2：低）", name = "level", required = true)
    @NotNull(message = "公关等级不能为空")
    @Min(value = 0, message = "公关等级（0：高，1：中，2：低）")
    @Max(value = 2, message = "公关等级（0：高，1：中，2：低）")
    private Integer level;

    @ApiModelProperty(value = "班主任", name = "adviser", required = true)
    @NotBlank(message = "班级名称不能为空")
    private String adviser;

    @ApiModelProperty(value = "联系方式", name = "telephone", required = true)
    @NotBlank(message = "班级名称不能为空")
    private String telephone;

    @ApiModelProperty(value = "学生人数", name = "amount", required = true)
    @NotNull(message = "学生人数不能为空")
    @Min(value = 0, message = "学生人数不合法")
    @Max(value = Integer.MAX_VALUE, message = "学生人数不合法")
    private Integer amount;

    @ApiModelProperty(value = "意向生数", name = "willingAmount", required = true)
    @NotNull(message = "意向生数不能为空")
    @Min(value = 0, message = "意向生数不合法")
    @Max(value = Integer.MAX_VALUE, message = "意向生数不合法")
    private Integer willingAmount;

    @ApiModelProperty(value = "说明", name = "comment")
    private String comment;
}
