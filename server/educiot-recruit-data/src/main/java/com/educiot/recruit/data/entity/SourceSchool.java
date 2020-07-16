package com.educiot.recruit.data.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 生源学校
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("e_r_source_school")
@ApiModel(value="SourceSchool对象", description="生源学校")
public class SourceSchool implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "生源学校")
    @TableId(value = "source_school_id", type = IdType.ID_WORKER)
    private Long sourceSchoolId;

    @ApiModelProperty(value = "招生学校ID")
    private Long recruitSchoolId;

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
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建人名称")
    private String creatorName;

    @ApiModelProperty(value = "创建人")
    private Long creatorId;

    @ApiModelProperty(value = "状态（0：删除，1：正常）")
    private Integer status;

    @ApiModelProperty(value = "导入学校类型")
    private String reserve;

}
