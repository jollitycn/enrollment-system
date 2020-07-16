package com.educiot.recruit.data.entity.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.educiot.recruit.common.base.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * @program: server
 * @description: 添加学生沟通记录入参封装实体
 * @author: Mr.AI
 * @create: 2020-04-15
 **/
@Data
@ApiModel(value = "添加学生沟通记录入参封装实体")
@EqualsAndHashCode(callSuper = true)
public class AddCommunicationRecordQuery extends BaseQuery{

    @ApiModelProperty(value = "沟通记录ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long communicationRecordId;

    @ApiModelProperty(value = "学生公关ID")
    private Long studentPublicRelationId;

    @ApiModelProperty(value = "沟通日期")
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private LocalDate communicationDate;

    @ApiModelProperty(value = "沟通方式（0：电话，1：微信，2：见面）")
    private Integer communicationWay;

    @ApiModelProperty(value = "沟通结果")
    private String communicationResult;

    @ApiModelProperty(value = "创建人")
    private Long creatorId;

    @ApiModelProperty(value = "创建人名称")
    private String creatorName;


}
