package com.educiot.recruit.data.entity.preach;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 宣讲生源学校关联表
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("e_r_preach_school_relation")
@ApiModel(value = "PreachSchoolRelation对象", description = "宣讲生源学校关联表")
public class PreachSchoolRelation implements Serializable {

    public class Columns {
        public static final String PREACH_PLAN_ID = "preach_plan_id";
        public static final String STATUS = "status";
    }

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "宣讲学校关联ID")
    @TableId(value = "preach_school_relation_id", type = IdType.ID_WORKER)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long preachSchoolRelationId;

    @ApiModelProperty(value = "招生宣讲计划ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long preachPlanId;

    @ApiModelProperty(value = "生源学校")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long sourceSchoolId;


}
