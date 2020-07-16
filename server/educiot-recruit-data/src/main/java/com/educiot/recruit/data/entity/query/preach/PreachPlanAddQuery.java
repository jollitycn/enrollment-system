package com.educiot.recruit.data.entity.query.preach;

import com.educiot.recruit.common.base.BaseQuery;
import com.educiot.recruit.common.constant.Constant;
import com.educiot.recruit.data.common.IdAndName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;


@Data
@ApiModel(value = "新增宣讲计划")
@EqualsAndHashCode(callSuper = true)
public class PreachPlanAddQuery  extends BaseQuery {

//    @ApiModelProperty(value = "招生宣讲计划ID")
//    private Long preachPlanId;

//    @ApiModelProperty(value = "年份")
//    private Integer year;

//    @ApiModelProperty(value = "学期" ,example = "")
//    private String semester;

    @ApiModelProperty(value = "协助人ID列表", required = true)
    private List<IdAndName> assistantIds;

    @ApiModelProperty(value = "面向学校ID列表" , required = true)
    private List<Long> sourceSchoolIds;

    @ApiModelProperty(value = "开始时间", example = "", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constant.Sys.LOCALDATETIME_FORMATTER)
    private LocalDateTime preachTime;

    @ApiModelProperty(value = "结束时间", example = "", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constant.Sys.LOCALDATETIME_FORMATTER)
    private LocalDateTime endTime;

    @ApiModelProperty(value = "主讲人ID" ,example = "1" , required = true)
    private Long speakerId;

    @ApiModelProperty(value = "主讲人名称" ,example = "主讲人名称" , required = true)
    private String speakerName;

    @ApiModelProperty(value = "计划人数" ,example = "0")
    private Integer planAmount;

    @ApiModelProperty(value = "联系方式" ,example = "")
    private String telephone;

    @ApiModelProperty(value = "主题" ,example = "")
    private String title;

    @ApiModelProperty(value = "宣讲地点" ,example = "")
    private String address;

    @ApiModelProperty(value = "备注" ,example = "")
    private String comment;

//    @ApiModelProperty(value = "学期接口ID" ,example = "0")
//    private Long semesterApiId;
}
