package com.educiot.recruit.data.entity.query;

import com.educiot.recruit.common.base.BaseQuery;
import com.educiot.recruit.common.constant.Constant;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @program: server
 * @description: 学生诉求信息入参封装实体
 * @author: Mr.AI
 * @create: 2020-04-22
 **/

@Data
@ApiModel(value = "学生诉求信息入参封装实体")
@EqualsAndHashCode(callSuper = true)
public class AddRequestQuery extends BaseQuery {

    @ApiModelProperty(value = "诉求ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long requestId;

    @ApiModelProperty(value = "学生公关ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long studentPublicRelationId;

    @ApiModelProperty(value = "诉求类型")
    private String requestType;

    @ApiModelProperty(value = "诉求内容")
    private String requestContent;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = Constant.Sys.LOCALDATETIME_FORMATTER, timezone = "GMT+8")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "回复人ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long replyId;

    @ApiModelProperty(value = "回复人名称")
    private String replyName;

    @ApiModelProperty(value = "回复时间")
    @JsonFormat(pattern = Constant.Sys.LOCALDATETIME_FORMATTER, timezone = "GMT+8")
    private LocalDateTime replyTime;

    @ApiModelProperty(value = "回复内容")
    private String replyContent;

    @ApiModelProperty(value = "诉求处理状态")
    private Integer status;

    @ApiModelProperty(value = "学生基本信息ID")
    private Long studentInfo;

    @ApiModelProperty(value = "学期ID")
    private Long semesterId;

    @ApiModelProperty(value = "招生学校ID")
    private Long recruitSchoolId;
}
