package com.educiot.recruit.data.entity.query;

import com.educiot.common.base.BaseQuery;
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
@ApiModel(value = "新增分组信息列表入参实体")
@EqualsAndHashCode(callSuper = true)
public class AddGroupQuery extends BaseQuery {

    @ApiModelProperty(value = "招生区域信息ID", name = "groupInfoId", required = true)
    @NotNull(message = "招生区域信息ID不能为空")
    @Min(value = 1L, message = "招生区域信息ID不合法")
    @Max(value = Long.MAX_VALUE, message = "招生区域信息ID不合法")
    private Long groupInfoId;

    @ApiModelProperty(value = "组别名称", name = "name", required = true)
    @NotBlank(message = "组别名称不能为空")
    private String name;

    @ApiModelProperty(value = "说明", name = "comment")
    private String comment;

    @ApiModelProperty(value = "所属部门（来着api组织结构）", name = "deptId", required = true)
    @NotNull(message = "所属部门ID不能为空")
    private Long deptId;

    @ApiModelProperty(value = "部门名称", name = "deptName", required = true)
    @NotBlank(message = "部门名称不能为空")
    private String deptName;

    @ApiModelProperty(value = "所在区域（来着数据字典）", name = "areaId", required = true)
    @NotNull(message = "所在区域ID不能为空")
    private Long areaId;

    @ApiModelProperty(value = "招生目标数量", name = "targetAmount")
    private Integer targetAmount;

    @ApiModelProperty(value = "学期ID")
    private Long semesterId;
}
