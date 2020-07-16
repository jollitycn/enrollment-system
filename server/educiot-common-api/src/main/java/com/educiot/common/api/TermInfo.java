package com.educiot.common.api;

import com.educiot.common.base.BaseVO;
import com.educiot.common.util.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
