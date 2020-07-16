package com.educiot.recruit.data.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.educiot.recruit.common.base.BaseVO;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @program: server
 * @description: 目标生返回封装实体VO
 * @author: Mr.AI
 * @create: 2020-05-11
 **/

@Data
@ApiModel(value = "目标生返回封装实体VO")
@EqualsAndHashCode(callSuper = true)
public class AimStudentVO extends BaseVO {

    @ApiModelProperty(value = "目标生ID")
    @TableId(value = "aim_student_id", type = IdType.ID_WORKER)
    @JsonSerialize(using= ToStringSerializer.class)
    private Long aimStudentId;

    @ApiModelProperty(value = "班级ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long classId;

    @ApiModelProperty(value = "学生姓名")
    private String studentName;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建人名称")
    private String creatorName;

    @ApiModelProperty(value = "创建人")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long creatorId;

    @ApiModelProperty(value = "学生联系方式")
    private String studentTelephone;

    @ApiModelProperty(value = "父亲姓名")
    private String fatherName;

    @ApiModelProperty(value = "父亲联系方式")
    private String fatherTelephone;

    @ApiModelProperty(value = "母亲姓名")
    private String motherName;

    @ApiModelProperty(value = "母亲联系方式")
    private String motherTelephone;

    @ApiModelProperty(value = "公关等级（0：高，1：中，2：低）")
    private Integer level;

    @ApiModelProperty(value = "意向原因")
    private String reason;

    @ApiModelProperty(value = "公关方向")
    private String direction;

    @ApiModelProperty(value = "状态（0：已报名，1：跟进中）")
    private Integer status;

    @ApiModelProperty(value = "生源学校ID")
    private Long sourceSchoolId;

}
