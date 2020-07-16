package com.educiot.recruit.data.entity.query;

import com.educiot.common.base.BaseQuery;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.List;

/**
 * @program: server
 * @description: 编辑学生基本信息入参封装实体
 * @author: Mr.AI
 * @create: 2020-04-15
 **/
@Data
@ApiModel(value = "编辑学生基本信息入参封装实体")
@EqualsAndHashCode(callSuper = true)
public class UpdateStudentInfoQuery extends BaseQuery{

    @ApiModelProperty(value = "学生基本信息ID")
    private Long studentInfo;

    @ApiModelProperty(value = "学生姓名")
    private String studentName;

    @ApiModelProperty(value = "创建人名称")
    private String creatorName;

    @ApiModelProperty(value = "创建人")
    private Long creatorId;

    @ApiModelProperty(value = "性别（0：男，1：女）")
    private Integer gender;

    @ApiModelProperty(value = "照片url")
    private String imageUrl;

    @ApiModelProperty(value = "民族")
    private String nation;

    @ApiModelProperty(value = "出生年月")
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

    @ApiModelProperty(value = "家庭成员信息列表",name = "addFamilyMemberQueries")
    private List<AddFamilyMemberQuery> addFamilyMemberQueries;

    @ApiModelProperty(value = "生源学校ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long sourceSchoolId;

    @ApiModelProperty(value = "班级ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long classId;

    @ApiModelProperty(value = "学生公关ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long studentPublicRelationId;

    @ApiModelProperty(value = "招生学校ID")
    private Long recruitSchoolId;

}
