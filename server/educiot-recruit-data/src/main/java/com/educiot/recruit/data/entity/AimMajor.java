package com.educiot.recruit.data.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 选择专业表
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("e_r_aim_major")
@ApiModel(value="AimMajor对象", description="选择专业表")
public class AimMajor implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "选择专业ID")
    @TableId(value = "aim_major_id", type = IdType.ID_WORKER)
    @JsonSerialize(using= ToStringSerializer.class)
    private Long aimMajorId;

    @ApiModelProperty(value = "学生公关ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long studentPublicRelationId;

    @ApiModelProperty(value = "招生专业ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long majorId;

    @ApiModelProperty(value = "志愿等级")
    private Integer level;

    @ApiModelProperty(value = "是否接受调剂（0：不接受，1：接受）")
    private Integer isAdjust;


}
