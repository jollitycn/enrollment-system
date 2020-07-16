package com.educiot.recruit.data.entity.query;

import com.educiot.recruit.common.base.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author Pan Juncai
 * @version 1.0
 * @date 2020/4/15 18:11
 */
@Data
@ApiModel(value = "分组-负责学校关联关系入参实体")
@EqualsAndHashCode(callSuper = true)
public class GroupSourceRelationQuery extends BaseQuery {
    @ApiModelProperty(value = "招生组ID")
    @NotNull(message = "招生组ID不能为空")
    @Min(value = 1L, message = "招生组ID最小为1")
    @Max(value = Long.MAX_VALUE, message = "招生组ID超出最大限制")
    private Long groupId;

    @ApiModelProperty(value = "生源学校ID")
    @NotNull(message = "生源学校ID不能为空")
    @Min(value = 1L, message = "生源学校ID最小为1")
    @Max(value = Long.MAX_VALUE, message = "生源学校ID超出最大限制")
    private Long sourceSchoolId;

    @ApiModelProperty(value = "学期id")
    private Long semesterId;

}
