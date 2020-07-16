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
 * 家庭成员表
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("e_r_family_member")
@ApiModel(value="FamilyMember对象", description="家庭成员表")
public class FamilyMember implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "家庭成员ID")
    @TableId(value = "family_member_id", type = IdType.ID_WORKER)
    @JsonSerialize(using= ToStringSerializer.class)
    private Long familyMemberId;

    @ApiModelProperty(value = "学生基本信息ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long studentInfo;

    @ApiModelProperty(value = "关系（0：父亲，1：母亲）")
    private Integer relation;

    @ApiModelProperty(value = "家长姓名")
    private String name;

    @ApiModelProperty(value = "工作单位")
    private String company;

    @ApiModelProperty(value = "职务")
    private String position;

    @ApiModelProperty(value = "联系方式")
    private String telephone;

    @ApiModelProperty(value = "户籍所在地")
    private String location;


}
