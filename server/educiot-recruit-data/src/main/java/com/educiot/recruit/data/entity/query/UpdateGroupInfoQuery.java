package com.educiot.recruit.data.entity.query;

import com.educiot.recruit.common.base.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Pan Juncai
 * @version 1.0
 * @date 2020/4/10 10:42
 */
@Data
@ApiModel(value = "修改招生区域信息列表入参实体")
@EqualsAndHashCode(callSuper = true)
public class UpdateGroupInfoQuery extends BaseQuery {

    @ApiModelProperty(value = "招生区域信息ID", name = "groupInfoId", required = true)
    @NotNull(message = "招生区域信息ID不能为空")
    @Min(value = 1L, message = "招生区域信息ID不合法")
    @Max(value = Long.MAX_VALUE, message = "招生组信息ID不合法")
    private Long groupInfoId;

    @ApiModelProperty(value = "学期ID", name = "semesterId", required = true)
    @NotNull(message = "学期ID不能为空")
    @Max(value = Long.MAX_VALUE, message = "学期ID不合法")
    private Long semesterId;

    @ApiModelProperty(value = "学期", name = "semester", required = true)
    @NotBlank(message = "学期不能为空")
    private String semester;

    @ApiModelProperty(value = "标题", name = "title", required = true)
    @NotBlank(message = "标题不能为空")
    private String title;

    @ApiModelProperty(value = "说明", name = "comment")
    private String comment;
}
