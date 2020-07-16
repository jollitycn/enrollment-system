package com.educiot.recruit.common.api;

import com.educiot.recruit.common.base.BaseVO;
import com.educiot.recruit.common.constant.Constant;
import com.educiot.recruit.common.util.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Date;

/**
 * @author Jason Hong
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TermInfo extends BaseVO {
    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("name")
    private String name;
    @ApiModelProperty("start")
    @JsonFormat(pattern = DateUtils.DATE_FMT_3, timezone = "GMT+8")
    private java.sql.Date start;
    @ApiModelProperty("end")
    @JsonFormat(pattern = DateUtils.DATE_FMT_3, timezone = "GMT+8")
    private java.sql.Date end;
}
