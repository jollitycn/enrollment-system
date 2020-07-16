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

/**
 * @program: server
 * @description: 诉求信息封装返回实体
 * @author: Mr.AI
 * @create: 2020-04-22
 **/

@Data
@ApiModel(value = "招生计划信息返回封装类")
@EqualsAndHashCode(callSuper = true)
public class RequestVO extends BaseVO {

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

    @ApiModelProperty(value = "生源学校名称")
    private String sourceSchoolName;

    @ApiModelProperty(value = "班级名称")
    private String className;

    @ApiModelProperty(value = "学生姓名")
    private String studentName;

    @ApiModelProperty(value = "学生联系方式")
    private String studentTelephone;

}
