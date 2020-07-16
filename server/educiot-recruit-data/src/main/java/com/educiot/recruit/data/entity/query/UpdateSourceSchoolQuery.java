package com.educiot.recruit.data.entity.query;

import com.educiot.recruit.common.base.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.*;
import java.util.List;

/**
 * @author Pan Juncai
 * @version 1.0
 * @date 2020/4/10 10:42
 */
@Data
@ApiModel(value = "编辑生源学校信息列表入参实体")
@EqualsAndHashCode(callSuper = true)
public class UpdateSourceSchoolQuery extends BaseQuery {

    @ApiModelProperty(value = "生源学校")
    @NotNull(message = "生源学校ID不能为空")
    @Min(value = 1L, message = "生源学校ID不合法")
    @Max(value = Long.MAX_VALUE, message = "生源学校ID不合法")
    private Long sourceSchoolId;

    @ApiModelProperty(value = "生源学校名称", name = "sourceSchoolName", required = true)
    @NotBlank(message = "生源学校名称不能为空")
    private String sourceSchoolName;

    @ApiModelProperty(value = "所在省份")
    private String province;

    @ApiModelProperty(value = "所在城市")
    private String city;

    @ApiModelProperty(value = "所在区域")
    private String district;

    @ApiModelProperty(value = "生源规模")
    private Integer sourceScale;

    @ApiModelProperty(value = "学校性质（0 公立，1 民营）")
    private Integer relationType;

    @ApiModelProperty(value = "学校资质")
    private String qualification;

    @ApiModelProperty(value = "学校地址")
    private String address;

    @ApiModelProperty(value = "上级主管部门")
    private String superiorDept;

    @ApiModelProperty(value = "说明")
    private String comment;

    @ApiModelProperty(value = "学校类型")
    private List<Long> typeIds;

    @ApiModelProperty(value = "是否公关（0：公关，1：不公关）")
    private Integer isRelation;

    @ApiModelProperty(value = "公关等级（1：重点公关 2：普通公关 3：重新公关）")
    private Integer relationLevel;

    @ApiModelProperty(value = "学校情况说明")
    private String schoolCaseComment;

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

}
