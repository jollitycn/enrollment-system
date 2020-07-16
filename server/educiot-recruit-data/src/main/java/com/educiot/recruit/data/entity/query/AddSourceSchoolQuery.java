package com.educiot.recruit.data.entity.query;

import com.educiot.recruit.common.base.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.*;
import java.util.List;

/**
 * @author Pan Juncai
 * @version 1.0
 * @date 2020/4/10 10:42
 */
@Data
@ApiModel(value = "新增生源学校信息列表入参实体")
@EqualsAndHashCode(callSuper = true)
public class AddSourceSchoolQuery extends BaseQuery {
    @ApiModelProperty(value = "生源学校名称", name = "sourceSchoolName", required = true)
    @NotBlank(message = "生源学校名称不能为空")
    private String sourceSchoolName;

    @ApiModelProperty(value = "所在省份", name = "province", required = true)
    @NotBlank(message = "所在省份不能为空")
    private String province;

    @ApiModelProperty(value = "所在城市", name = "city", required = true)
    @NotBlank(message = "所在城市不能为空")
    private String city;

    @ApiModelProperty(value = "所在区域", name = "district", required = true)
    @NotBlank(message = "所在区域不能为空")
    private String district;

    @ApiModelProperty(value = "生源规模", name = "sourceScale", required = true)
    @NotNull(message = "生源规模不能为空")
    @Min(value = 0, message = "生源规模不合法")
    @Max(value = Integer.MAX_VALUE, message = "生源规模不合法")
    private Integer sourceScale;

    @ApiModelProperty(value = "学校性质（0 公立，1 民营）", name = "relationType", required = true)
    @NotNull(message = "学校性质不能为空")
    @Min(value = 0, message = "学校性质（0 公立，1 民营）")
    @Max(value = 1, message = "学校性质（0 公立，1 民营）")
    private Integer relationType;

    @ApiModelProperty(value = "学校资质", name = "qualification", required = true)
    @NotBlank(message = "学校资质不能为空")
    private String qualification;

    @ApiModelProperty(value = "学校地址", name = "address", required = true)
    @NotBlank(message = "学校地址不能为空")
    private String address;

    @ApiModelProperty(value = "上级主管部门", name = "superiorDept", required = true)
    @NotBlank(message = "上级主管部门不能为空")
    private String superiorDept;

    @ApiModelProperty(value = "说明", name = "comment")
    private String comment;

    @ApiModelProperty(value = "学校类型", name = "typeIds", required = true)
    @NotNull(message = "学校类型不能为空")
    @Size(min = 1, message = "学校类型不能为空")
    private List<Long> typeIds;
}
