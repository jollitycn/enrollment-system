package com.educiot.recruit.data.entity.vo.preach;

import com.educiot.common.base.BaseVO;
import com.educiot.common.constant.Constant;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@ApiModel(value = "宣讲计划反馈信息返回封装类")
@EqualsAndHashCode()
public class PreachFeedbackVO extends BaseVO {

    @ApiModelProperty(value = "宣讲反馈ID")
    private String preachFeedbackId;

    @ApiModelProperty(value = "招生宣讲计划ID")
    private String preachPlanId;

    @ApiModelProperty(value = "学生公关ID")
    private String studentPublicRelationId;
//
//    @ApiModelProperty(value = "学生名称")
//    private String studentName;

    @ApiModelProperty(value = "学生名称")
    private String studentName;

    @ApiModelProperty(value = "联系方式")
    private String studentTelephone;

    @ApiModelProperty(value = "宣讲效果（0：好，1：一般，2：差）")
    private Integer performance;

    @ApiModelProperty(value = "反馈内容")
    private String feedbackContent;

    @ApiModelProperty(value = "创建人")
    private String creatorId;

    @ApiModelProperty(value = "创建人名称")
    private String creatorName;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constant.Sys.LOCALDATETIME_FORMATTER)
    private LocalDateTime createTime;


}
