package com.educiot.recruit.data.entity.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.educiot.common.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;


/**
 * @program: server
 * @description: 沟通记录返回封装类
 * @author: Mr.AI
 * @create: 2020-04-17
 **/

@Data
@ApiModel(value = "沟通记录返回封装类")
@EqualsAndHashCode(callSuper = true)
public class CommunicationRecordVO extends BaseVO {

    @ApiModelProperty(value = "沟通记录ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long communicationRecordId;

    @ApiModelProperty(value = "学生公关ID")
    private Long studentPublicRelationId;

    @ApiModelProperty(value = "沟通日期")
    private LocalDate communicationDate;

    @ApiModelProperty(value = "沟通方式（0：电话，1：微信，2：见面）")
    private Integer communicationWay;

    @ApiModelProperty(value = "沟通方式中文显示名称")
    private String communicationWayName;

    @ApiModelProperty(value = "沟通结果")
    private String communicationResult;

}
