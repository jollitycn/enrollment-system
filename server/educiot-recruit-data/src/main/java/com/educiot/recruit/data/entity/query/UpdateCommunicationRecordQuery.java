package com.educiot.recruit.data.entity.query;

import com.educiot.common.base.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * @program: server
 * @description: 编辑学生沟通记录入参封装实体
 * @author: Mr.AI
 * @create: 2020-04-15
 **/

@Data
@ApiModel(value = "编辑学生沟通记录入参封装实体")
@EqualsAndHashCode(callSuper = true)
public class UpdateCommunicationRecordQuery extends BaseQuery{

    @ApiModelProperty(value = "沟通记录ID")
    @NotNull(message = "沟通记录ID不能为空")
    private Long communicationRecordId;

    @ApiModelProperty(value = "学生公关ID")
    private Long studentPublicRelationId;

    @ApiModelProperty(value = "沟通日期")
    private LocalDate communicationDate;

    @ApiModelProperty(value = "沟通方式（0：电话，1：微信，2：见面）")
    private Integer communicationWay;

    @ApiModelProperty(value = "沟通结果")
    private String communicationResult;

}
