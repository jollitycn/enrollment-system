package com.educiot.recruit.data.entity.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.educiot.recruit.common.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Pan Juncai
 * @version 1.0
 * @date 2020/4/22 10:41
 */
@Data
@ApiModel(value = "班级信息返回实体VO")
@EqualsAndHashCode(callSuper = true)
public class ClassVO extends BaseVO {
    @ApiModelProperty(value = "班级ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long classId;

    @ApiModelProperty(value = "班级名称")
    private String className;

    @ApiModelProperty(value = "公关等级（0：高，1：中，2：低）")
    private Integer level;

    @ApiModelProperty(value = "班主任")
    private String adviser;

    @ApiModelProperty(value = "联系方式")
    private String telephone;

    @ApiModelProperty(value = "学生人数")
    private Integer amount;

    @ApiModelProperty(value = "意向生")
    private Integer willingAmount;

    @ApiModelProperty(value = "说明")
    private String comment;

    @ApiModelProperty(value = "目标生数量")
    private int aimStudentCount;

    @ApiModelProperty(value = "意向生数量")
    private int studentCount;
}
