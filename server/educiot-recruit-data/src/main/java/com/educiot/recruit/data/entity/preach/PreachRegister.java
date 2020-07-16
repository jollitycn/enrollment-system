package com.educiot.recruit.data.entity.preach;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.educiot.recruit.common.constant.Constant;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 宣讲计划报名表
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("e_r_preach_register")
@ApiModel(value = "PreachRegister对象", description = "宣讲计划报名表")
public class PreachRegister implements Serializable {

    public class Columns {
        public static final String PREACH_REGISTER_ID = "preach_register_id";
        public static final String PREACH_PLAN_ID = "preach_plan_id";
        public static final String WILLING_STUDENT_ID = "willing_student_id";
        public static final String STATUS = "status";
    }

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "宣讲计划报名ID")
    @TableId(value = "preach_register_id", type = IdType.ID_WORKER)
    private String preachRegisterId;

    @ApiModelProperty(value = "招生宣讲计划ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long preachPlanId;

    @ApiModelProperty(value = "学生公关id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long studentPublicRelationId;

    @ApiModelProperty(value = "状态（0：报名，1：签到）")
    private Integer status;

    @ApiModelProperty(value = "创建人")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long creatorId;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constant.Sys.LOCALDATETIME_FORMATTER)
    private LocalDateTime createTime;

}
