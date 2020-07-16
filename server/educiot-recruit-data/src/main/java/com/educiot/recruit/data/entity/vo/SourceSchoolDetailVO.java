package com.educiot.recruit.data.entity.vo;

import com.educiot.recruit.common.base.BaseVO;
import com.educiot.recruit.common.constant.Constant;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 生源学校信息详情
 *
 * @author Pan Juncai
 * @version 1.0
 * @date 2020/4/16 10:57
 */
@Data
@ApiModel(value = "生源学校信息详情返回实体")
@EqualsAndHashCode(callSuper = true)
public class SourceSchoolDetailVO extends BaseVO {
    @ApiModelProperty(value = "生源学校")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long sourceSchoolId;

    @ApiModelProperty(value = "生源学校名称")
    private String sourceSchoolName;

    @ApiModelProperty(value = "所在省份")
    private String province;

    @ApiModelProperty(value = "所在城市")
    private String city;

    @ApiModelProperty(value = "所在区域")
    private String district;

    @ApiModelProperty(value = "是否公关（0：公关，1：不公关）")
    private Integer isRelation;

    @ApiModelProperty(value = "公关等级（1：重点公关 2：普通公关 3：重新公关）")
    private Integer relationLevel;

    @ApiModelProperty(value = "学校性质（0 公立，1 民营）")
    private Integer relationType;

    @ApiModelProperty(value = "生源规模")
    private Integer sourceScale;

    @ApiModelProperty(value = "学校资质")
    private String qualification;

    @ApiModelProperty(value = "学校地址")
    private String address;

    @ApiModelProperty(value = "上级主管部门")
    private String superiorDept;

    @ApiModelProperty(value = "学校情况说明")
    private String schoolCaseComment;

    @ApiModelProperty(value = "说明")
    private String comment;

    @ApiModelProperty(value = "中招人数")
    private Integer recruitAmount;

    @ApiModelProperty(value = "中招方式")
    private String recruitWay;

    @ApiModelProperty(value = "年级主任")
    private String gradeDirectorName;

    @ApiModelProperty(value = "年级主任电话")
    private String gradeDirectorTel;

    @ApiModelProperty(value = "班级数量")
    private Integer classAmount;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = Constant.Sys.LOCALDATETIME_FORMATTER, timezone = "GMT+8")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建人名称")
    private String creatorName;

    @ApiModelProperty(value = "学校类型集合")
    private List<SysDictVO> schoolTypeList;
}
