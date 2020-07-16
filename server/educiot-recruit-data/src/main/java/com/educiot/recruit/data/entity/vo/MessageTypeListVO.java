package com.educiot.recruit.data.entity.vo;

import com.educiot.recruit.common.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author liuhao
 * @program educiot-recruit
 * @description：信息类型
 * @date Create in 2020/5/22
 */
@Data
@ApiModel(value = "消息列表返回实体VO")
@EqualsAndHashCode(callSuper = true)
public class MessageTypeListVO extends BaseVO {

    @ApiModelProperty("消息code")
    private String dataCode;

    @ApiModelProperty("消息值")
    private String dataValue;

}
