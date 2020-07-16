package com.educiot.recruit.data.entity.query;

import com.educiot.recruit.common.base.BasePageQuery;
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
 * @date 2020/4/22 10:32
 */
@Data
@ApiModel(value = "分页查询生源学校下的班级列表入参实体")
@EqualsAndHashCode(callSuper = true)
public class ListClassPageQuery extends BasePageQuery {

    @ApiModelProperty(value = "生源学校ID", name = "sourceSchoolId", required = true)
    @NotNull(message = "生源学校ID不能为空")
    @Min(value = 1L, message = "生源学校ID最小为1")
    @Max(value = Long.MAX_VALUE, message = "生源学校ID超出最大限制")
    private Long sourceSchoolId;

    @ApiModelProperty(value = "学期ID", name = "semesterId", required = true)
    private Long semesterId;

}
