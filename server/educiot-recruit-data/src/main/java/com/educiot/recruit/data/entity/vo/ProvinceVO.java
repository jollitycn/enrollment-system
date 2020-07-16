package com.educiot.recruit.data.entity.vo;

import com.educiot.recruit.data.entity.Province;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author liuhao
 * @date Created in 2020/3/20
 */
@Data
@ApiModel(value = "系统地区返回VO")
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProvinceVO extends Province {

    @ApiModelProperty(value = "下级列表")
    private List<ProvinceVO> subs;

}
