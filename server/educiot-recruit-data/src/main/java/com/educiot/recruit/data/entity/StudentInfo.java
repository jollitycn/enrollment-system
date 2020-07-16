package com.educiot.recruit.data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.educiot.common.constant.Constant;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 学生基本信息表
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("e_r_student_info")
@ApiModel(value="StudentInfo对象", description="学生基本信息表")
public class StudentInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "学生基本信息ID")
    @TableId(value = "student_info", type = IdType.ID_WORKER)
    @JsonSerialize(using= ToStringSerializer.class)
    private Long studentInfo;

    @ApiModelProperty(value = "学生姓名")
    private String studentName;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = Constant.Sys.LOCALDATETIME_FORMATTER, timezone = "GMT+8")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建人名称")
    private String creatorName;

    @ApiModelProperty(value = "创建人")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long creatorId;

    @ApiModelProperty(value = "性别（0：男，1：女）")
    private Integer gender;

    @ApiModelProperty(value = "照片url")
    private String imageUrl;

    @ApiModelProperty(value = "民族")
    private String nation;

    @ApiModelProperty(value = "出生年月")
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private LocalDate doa;

    @ApiModelProperty(value = "身份证号")
    private String idNo;

    @ApiModelProperty(value = "身体状况")
    private String healthStatus;

    @ApiModelProperty(value = "户籍省份")
    private String province;

    @ApiModelProperty(value = "户籍城市")
    private String city;

    @ApiModelProperty(value = "户籍区域")
    private String district;

    @ApiModelProperty(value = "学生微信")
    private String wechat;

    @ApiModelProperty(value = "家长联系方式")
    private String parentTelephone;

    @ApiModelProperty(value = "学生联系方式")
    private String studentTelephone;

    @ApiModelProperty(value = "家庭省份")
    private String familyProvince;

    @ApiModelProperty(value = "家庭城市")
    private String familyCity;

    @ApiModelProperty(value = "家庭区域")
    private String familyDistrict;

    @ApiModelProperty(value = "详细地址")
    private String address;

    @ApiModelProperty(value = "qq号")
    private String qqNo;



}
