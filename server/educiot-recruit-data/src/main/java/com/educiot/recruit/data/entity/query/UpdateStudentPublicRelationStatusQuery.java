package com.educiot.recruit.data.entity.query;

import com.educiot.recruit.common.base.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @program: server
 * @description: 学生公关信息修改报名状态封装实体类
 * @author: Mr.AI
 * @create: 2020-04-21
 **/

@Data
@ApiModel(value = "学生公关信息修改报名状态封装实体类")
@EqualsAndHashCode(callSuper = true)
public class UpdateStudentPublicRelationStatusQuery extends BaseQuery {

    @ApiModelProperty(value = "学生公关ID")
    @NotNull(message = "学生公关ID不能为空")
    private Long studentPublicRelationId;

    @ApiModelProperty(value = "报名状态（0：已交订金，1：已交全款，2：已退款，3：强意向生）")
    @NotNull(message = "学生报名状态不能为空")
    private Integer registerStatus;

}
