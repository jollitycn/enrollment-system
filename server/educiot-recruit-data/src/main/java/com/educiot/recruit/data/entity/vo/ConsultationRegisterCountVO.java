package com.educiot.recruit.data.entity.vo;

import com.educiot.recruit.common.base.BaseVO;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**ri
 * @author liuhao
 * @program educiot-recruit
 * @description：咨询人数统计vo
 * @date Create in 2020/6/30
 */
@Data
@ApiModel(value = "咨询人数统计VO")
@EqualsAndHashCode(callSuper = true)
public class ConsultationRegisterCountVO extends BaseVO {

    @ApiModelProperty(value = "咨询日期")
    @JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8")
    private LocalDate consultationDate;

    @ApiModelProperty(value = "个人咨询人数")
    private Integer personalCount;

    @ApiModelProperty(value = "学校咨询人数")
    private Integer schoolCount;
    
}
