package com.educiot.recruit.data.entity.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.educiot.common.base.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * @author liuhao
 * @program educiot-recruit
 * @description：新增咨询反馈
 * @date Create in 2020/4/16
 */
@Data
@ApiModel(value = "新增咨询反馈入参实体类")
@EqualsAndHashCode(callSuper = true)
public class AddConsultationFeedbackQuery extends BaseQuery {

    @ApiModelProperty(value = "到校咨询ID")
    private Long consultationId;

    @ApiModelProperty(value = "咨询日期")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private LocalDate consultationDate;

    @ApiModelProperty(value = "反馈内容")
    private String consultationContent;

    @ApiModelProperty(value = "总结")
    private String summary;
}
