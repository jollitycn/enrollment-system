package com.educiot.recruit.data.entity.query;

import com.educiot.common.base.BaseQuery;
import com.educiot.common.constant.Constant;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author liuhao
 * @program educiot-recruit
 * @description：咨询登记
 * @date Create in 2020/4/29
 */
@Data
@ApiModel(value = "咨询登记入参实体类")
@EqualsAndHashCode(callSuper = true)
public class AddConsultationRegisterQuery extends BaseQuery {

    @ApiModelProperty(value = "咨询类型（0：个人，1：学校）", required = true)
    private Integer consultationType;

    @ApiModelProperty(value = "到校咨询ID", required = true)
    private Long consultationId;

    @ApiModelProperty(value = "学校ID", required = true)
    private Long sourceSchoolId;

    @ApiModelProperty(value = "联系方式", required = true)
    private String telephone;

    @ApiModelProperty(value = "到校时间 yyyy-MM-dd HH:mm:ss", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constant.Sys.LOCALDATETIME_FORMATTER)
    private LocalDateTime attendTime;

    @ApiModelProperty(value = "咨询日期 yyyy-MM-dd", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private LocalDate consultationDate;

    @ApiModelProperty(value = "是否用餐（0：是，1：否）", required = true)
    private Integer isLunch;

    @ApiModelProperty(value = "班级ID")
    private Long classId;

    @ApiModelProperty(value = "学生公关ID")
    private Long studentPublicRelationId;

    @ApiModelProperty(value = "学生姓名")
    private String studentName;

    @ApiModelProperty(value = "带队老师")
    private String teacher;

    @ApiModelProperty(value = "人数")
    private Integer amount;

    @ApiModelProperty(value = "说明")
    private String comment;

}
