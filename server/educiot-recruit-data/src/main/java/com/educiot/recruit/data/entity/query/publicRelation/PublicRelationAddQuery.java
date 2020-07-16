package com.educiot.recruit.data.entity.query.publicRelation;

import com.educiot.recruit.common.base.BaseQuery;
import com.educiot.recruit.common.constant.Constant;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;


@Data
@ApiModel(value = "新增学校公关")
@EqualsAndHashCode(callSuper = true)
public class PublicRelationAddQuery  extends BaseQuery {


    @ApiModelProperty(value = "招生组生源学校关联ID",required = true)
    @JsonSerialize(using= ToStringSerializer.class )
    private Long groupSourceRelationId;

    @ApiModelProperty(value = "招生组ID",required = true)
    @JsonSerialize(using= ToStringSerializer.class)
    private Long groupId;

    @ApiModelProperty(value = "生源学校",required = true)
    @JsonSerialize(using= ToStringSerializer.class)
    private Long sourceSchoolId;

    @ApiModelProperty(value = "开始时间", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constant.Sys.LOCALDATETIME_FORMATTER)
    private LocalDateTime startTime;

    @ApiModelProperty(value = "结束时间", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constant.Sys.LOCALDATETIME_FORMATTER)
    private LocalDateTime endTime;

    @ApiModelProperty(value = "沟通人")
    private String contacter;

    @ApiModelProperty(value = "联系方式")
    private String telephone;

    @ApiModelProperty(value = "学校拜访内容")
    private String content;
}
