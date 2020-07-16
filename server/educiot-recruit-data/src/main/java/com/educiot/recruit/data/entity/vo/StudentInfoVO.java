package com.educiot.recruit.data.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.educiot.recruit.common.constant.Constant;
import com.educiot.recruit.data.entity.FamilyMember;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.educiot.recruit.common.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @program: server
 * @description: 学生基本信息返回VO
 * @author: Mr.AI
 * @create: 2020-04-22
 **/

@Data
@ApiModel(value = "招生计划信息返回封装类")
@EqualsAndHashCode(callSuper = true)
public class StudentInfoVO extends BaseVO {

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

    @ApiModelProperty(value = "学生微信",name = "wechat")
    private String wechat;

    @ApiModelProperty(value = "家长联系方式")
    private String parentTelephone;

    @ApiModelProperty(value = "学生联系方式")
    private String studentTelephone;

    @ApiModelProperty(value = "家庭省份",name = "familyProvince")
    private String familyProvince;

    @ApiModelProperty(value = "家庭城市",name = "familyCity")
    private String familyCity;

    @ApiModelProperty(value = "家庭区域",name = "familyDistrict")
    private String familyDistrict;

    @ApiModelProperty(value = "详细地址",name = "address")
    private String address;

    @ApiModelProperty(value = "qq号")
    private String qqNo;

    @ApiModelProperty(value = "班级名称")
    private String className;

    @ApiModelProperty(value = "生源学校名称")
    private String sourceSchoolName;

    @ApiModelProperty(value = "报名状态（0：已交订金，1：已交全款，2：已退款，3：强意向生）")
    private Integer registerStatus;

    @ApiModelProperty(value = "公关等级（0：高，1：中，2：低）")
    private Integer level;

    @ApiModelProperty(value = "意向原因")
    private String reason;

    @ApiModelProperty(value = "公关方向")
    private String direction;

    @ApiModelProperty(value = "父亲姓名")
    private String fatherName;

    @ApiModelProperty(value = "父亲联系方式")
    private String fatherTelephone;

    @ApiModelProperty(value = "母亲姓名")
    private String motherName;

    @ApiModelProperty(value = "母亲联系方式")
    private String motherTelephone;

    @ApiModelProperty(value = "登记编号")
    private Integer studentNo;

    @ApiModelProperty(value = "公关ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long studentPublicRelationId;

    @ApiModelProperty(value = "学生家庭信息")
    private List<FamilyMember> familyMembers;

}
