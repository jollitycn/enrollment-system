package com.educiot.recruit.data.entity.vo;

import com.educiot.common.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author liuhao
 * @program educiot-recruit
 * @description：入学通知
 * @date Create in 2020/5/12
 */
@Data
@ApiModel(value = "入学通知返回实体VO")
@EqualsAndHashCode(callSuper = true)
public class AdmissionVO extends BaseVO {

    @ApiModelProperty("入学通知内容")
    private String content;

    @ApiModelProperty("确认状态（0：未确认，1：已确认）")
    private Integer confirmStatus;

}
