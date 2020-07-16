package com.educiot.recruit.data.entity.query;

import com.educiot.recruit.common.base.BaseQuery;
import com.educiot.recruit.common.constant.Constant;
import com.educiot.recruit.data.entity.FamilyMember;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @program: server
 * @description: 添加学生基本信息封装实体类
 * @author: Mr.AI
 * @create: 2020-04-23
 **/

@Data
@ApiModel(value = "添加学生基本信息封装实体类")
@EqualsAndHashCode(callSuper = true)
public class AddStudentInfoQuery extends BaseQuery {

    @ApiModelProperty(value = "学生姓名", required = true)
    private String studentName;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = Constant.Sys.LOCALDATETIME_FORMATTER, timezone = "GMT+8")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建人名称")
    private String creatorName;

    @ApiModelProperty(value = "创建人")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long creatorId;

    @ApiModelProperty(value = "性别（0：男，1：女）", required = true)
    private Integer gender;

    @ApiModelProperty(value = "照片url")
    private String imageUrl;

    @ApiModelProperty(value = "民族")
    private String nation;

    @ApiModelProperty(value = "出生年月", required = true)
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private LocalDate doa;

    @ApiModelProperty(value = "身份证号")
    private String idNo;

    @ApiModelProperty(value = "身体状况")
    private String healthStatus;

    @ApiModelProperty(value = "户籍省份", required = true)
    private String province;

    @ApiModelProperty(value = "户籍城市", required = true)
    private String city;

    @ApiModelProperty(value = "户籍区域", required = true)
    private String district;

    @ApiModelProperty(value = "学生微信")
    private String wechat;

    @ApiModelProperty(value = "家长联系方式", required = true)
    private String parentTelephone;

    @ApiModelProperty(value = "学生联系方式")
    private String studentTelephone;

    @ApiModelProperty(value = "家庭省份", required = true)
    private String familyProvince;

    @ApiModelProperty(value = "家庭城市", required = true)
    private String familyCity;

    @ApiModelProperty(value = "家庭区域", required = true)
    private String familyDistrict;

    @ApiModelProperty(value = "详细地址", required = true)
    private String address;

    @ApiModelProperty(value = "招生学校ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long recruitSchoolId;

    @ApiModelProperty(value = "生源学校ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long sourceSchoolId;

    @ApiModelProperty(value = "班级ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long classId;

    @ApiModelProperty(value = "学期")
    private String semester;

    @ApiModelProperty(value = "学期ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long semesterId;

    @ApiModelProperty(value = "家庭信息列表")
    private List<FamilyMember> familyMembers;

}
