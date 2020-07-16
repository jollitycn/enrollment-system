package com.educiot.recruit.data.entity.query;

import com.educiot.common.base.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * @author Pan Juncai
 * @version 1.0
 * @date 2020/4/29 13:55
 */
@Data
@ApiModel(value = "小程序校验学生和家长手机是否存在的入参实体")
@EqualsAndHashCode(callSuper = true)
public class CheckStudentQuery extends BaseQuery {
    @ApiModelProperty(value = "学生姓名", name = "studentName", required = true)
    @NotBlank(message = "学生姓名不能为空")
    private String studentName;

    @ApiModelProperty(value = "家长手机", name = "parentTelephone", required = true)
    @NotBlank(message = "家长手机不能为空")
    private String parentTelephone;
}
