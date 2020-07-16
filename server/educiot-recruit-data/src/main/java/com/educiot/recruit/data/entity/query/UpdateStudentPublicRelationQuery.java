package com.educiot.recruit.data.entity.query;

import com.educiot.common.base.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @program: server
 * @description: 编辑学生公关信息入参封装实体类
 * @author: Mr.AI
 * @create: 2020-04-15
 **/

@Data
@ApiModel(value = "编辑学生公关信息入参封装实体类")
@EqualsAndHashCode(callSuper = true)
public class UpdateStudentPublicRelationQuery extends BaseQuery{

    @ApiModelProperty(value = "学生公关ID")
    @NotNull(message = "学生公关ID不能为空")
    private Long studentPublicRelationId;

    @ApiModelProperty(value = "学生基本信息ID")
    private Long studentInfo;

    @ApiModelProperty(value = "招生学校ID")
    private Long recruitSchoolId;

    @ApiModelProperty(value = "生源学校ID")
    private Long sourceSchoolId;

    @ApiModelProperty(value = "班级ID")
    private Long classId;

    @ApiModelProperty(value = "公关等级（0：高，1：中，2：低）")
    private Integer level;

    @ApiModelProperty(value = "意向原因")
    private String reason;

    @ApiModelProperty(value = "公关方向")
    private String direction;

    @ApiModelProperty(value = "公关类型")
    private String relationType;

    @ApiModelProperty(value = "负责人")
    private Long creatorId;

    @ApiModelProperty(value = "负责人名称")
    private String creatorName;

    @ApiModelProperty(value = "报名状态（0：已交订金，1：已交全款，2：已退款，3：强意向生）")
    private Integer registerStatus;

    @ApiModelProperty(value = "学期")
    private String semester;

    @ApiModelProperty(value = "招生组ID")
    private Long groupId;

    @ApiModelProperty(value = "学期ID")
    private Long semesterId;

}
