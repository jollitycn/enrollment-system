package com.educiot.recruit.data.entity.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.educiot.common.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * @author liuhao
 * @date Created in 2020/3/20
 */
@Data
@ApiModel(value = "系统地区返回VO")
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysRegionVO extends BaseVO implements Serializable {

    @ApiModelProperty(value = "地区ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long regionId;

    @ApiModelProperty(value = "父ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long parentId;

    @ApiModelProperty(value = "简称")
    private String shortName;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "全称")
    private String mergerName;

    @ApiModelProperty(value = "层级（1：省，2：市，3：区县）")
    private Integer level;

    @ApiModelProperty(value = "拼音")
    private String pinyin;

    @ApiModelProperty(value = "长途区号")
    private String code;

    @ApiModelProperty(value = "邮编")
    private String zipCode;

    @ApiModelProperty(value = "下级列表")
    private List<SysRegionVO> subs;

}
