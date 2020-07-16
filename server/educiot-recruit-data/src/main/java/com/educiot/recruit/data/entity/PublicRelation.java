package com.educiot.recruit.data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * 学校公关表
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("e_r_public_relation")
@ApiModel(value = "PublicRelation对象", description = "学校公关表")
public class PublicRelation implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "招生组生源学校关联ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long groupSourceRelationId;

    @ApiModelProperty(value = "学校公关ID")
    @TableId(value = "public_relation", type = IdType.ID_WORKER)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long publicRelation;
    @ApiModelProperty(value = "招生组ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long groupId;
    @ApiModelProperty(value = "生源学校")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long sourceSchoolId;
    @ApiModelProperty(value = "开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime startTime;
    @ApiModelProperty(value = "结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime endTime;
    @ApiModelProperty(value = "创建人（负责人）")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long creatorId;

    @ApiModelProperty(value = "沟通人")
    private String contacter;

    @ApiModelProperty(value = "联系方式")
    private String telephone;

    @ApiModelProperty(value = "学校拜访内容")
    private String content;
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建人名称（负责人名称）")
    private String creatorName;

    public static class Columns {
        public static final String PUBLIC_RELATION = "public_relation";
//        public static final String STATUS = "status";
    }


}
