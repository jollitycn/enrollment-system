package com.educiot.recruit.data.entity.query.preach;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.educiot.recruit.common.base.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@ApiModel(value = "新增宣讲计划反馈")
@EqualsAndHashCode(callSuper = true)
public class PreachFeedbackAddQuery extends BaseQuery {


    @ApiModelProperty(value = "招生宣讲计划id", required = true)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long preachPlanId;


    @ApiModelProperty(value = "宣讲效果（1-5）")
    private Integer performance;

    @ApiModelProperty(value = "反馈内容")
    private String feedbackContent;
}
