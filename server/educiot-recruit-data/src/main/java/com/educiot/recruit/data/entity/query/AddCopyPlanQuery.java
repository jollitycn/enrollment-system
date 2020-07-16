package com.educiot.recruit.data.entity.query;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.educiot.recruit.common.base.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * @program: server
 * @description: 复制招生计划信息入参实体类
 * @author: Mr.AI
 * @create: 2020-04-13
 **/

@Data
@ApiModel(value = "复制招生计划信息入参实体类")
@EqualsAndHashCode(callSuper = true)
public class AddCopyPlanQuery extends BaseQuery {

    @ApiModelProperty(value = "拷贝ID",name = "copyId",required = true)
    private Long copyId;

    @ApiModelProperty(value = "学期")
    private String semester;

    @ApiModelProperty(value = "创建人")
    private Long creatorId;

    @ApiModelProperty(value = "创建人名称")
    private String creatorName;

    @ApiModelProperty(value = "学期ID")
    @NotNull(message = "学期ID不能为空")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long semesterId;

    @ApiModelProperty(value = "学期开始使劲")
    private LocalDate semesterStart;

    @ApiModelProperty(value = "学期结束时间")
    private LocalDate semesterEnd;

}
