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
 * @date 2020/4/16 9:40
 */
@Data
@ApiModel(value = "分组-负责人关联关系入参实体")
@EqualsAndHashCode(callSuper = true)
public class ChargerQuery extends BaseQuery {
    @ApiModelProperty(value = "招生组ID", name = "groupId", required = true)
    @NotNull(message = "招生组ID不能为空")
    @Min(value = 1L, message = "招生组ID最小为1")
    @Max(value = Long.MAX_VALUE, message = "招生组ID超出最大限制")
    private Long groupId;

    @ApiModelProperty(value = "接口ID", name = "apiId", required = true)
    @NotNull(message = "负责人接口ID不能为空")
    private Long apiId;

    @ApiModelProperty(value = "姓名", name = "name", required = true)
    @NotBlank(message = "姓名不能为空")
    private String name;

    @ApiModelProperty(value = "账号", name = "account", required = true)
    @NotBlank(message = "账号不能为空")
    private String account;

    @ApiModelProperty(value = "手机号", name = "telephone", required = true)
    @NotBlank(message = "手机号不能为空")
    private String telephone;

    @ApiModelProperty(value = "是否是组长（0：否，1：是）", name = "isLeader", required = true)
    @NotNull(message = "招生组ID不能为空")
    @Min(value = 0, message = "是否是组长（0：否，1：是）")
    @Max(value = 1, message = "是否是组长（0：否，1：是）")
    private Integer isLeader;

    @ApiModelProperty(value = "学期ID", name = "semesterId", required = true)
    @NotNull(message = "负责人接口ID不能为空")
    private Long semesterId;
}
