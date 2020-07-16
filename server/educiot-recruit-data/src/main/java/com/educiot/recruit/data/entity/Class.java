package com.educiot.recruit.data.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 班级表
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("e_r_class")
@ApiModel(value="Class对象", description="班级表")
public class Class implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "班级ID")
    @TableId(value = "class_id", type = IdType.ID_WORKER)
    private Long classId;

    @ApiModelProperty(value = "生源学校ID")
    private Long sourceSchoolId;

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

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建人名称")
    private String creatorName;

    @ApiModelProperty(value = "创建人")
    private Long creatorId;

    @ApiModelProperty(value = "学期ID")
    private Long semesterId;
}
