package com.educiot.recruit.data.entity.vo.preach;

import com.educiot.common.base.BaseVO;
import com.educiot.common.base.KeyValue;
import com.educiot.common.constant.Constant;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

@Data
@ApiModel(value = "宣讲计划信息返回封装类")
@EqualsAndHashCode()
public class PreachPlanVO extends BaseVO {

    @ApiModelProperty(value = "协助人ID列表")
    private List<String> assistantIds;

    @ApiModelProperty(value = "面向学校id列表")
    private List<String> sourceSchoolIds;

    @ApiModelProperty(value = "协助人列表")
    private List<KeyValue<String, String>> assistants;

    @ApiModelProperty(value = "面向学校列表")
    private List<KeyValue<String, String>> sourceSchools;

//    @ApiModelProperty(value = "年份")
//    private Integer year;

//    @ApiModelProperty(value = "学期")
//    private String semester;

    @ApiModelProperty(value = "招生宣讲计划ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long preachPlanId;

    @ApiModelProperty(value = "宣讲时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constant.Sys.LOCALDATETIME_FORMATTER)
    private LocalDateTime preachTime;
    @ApiModelProperty(value = "结束时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constant.Sys.LOCALDATETIME_FORMATTER)
    private LocalDateTime endTime;
    @ApiModelProperty(value = "主讲人ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long speakerId;

    @ApiModelProperty(value = "主讲人名称")
    private String speakerName;

    @ApiModelProperty(value = "计划人数")
    private Integer planAmount;

    @ApiModelProperty(value = "签到人数")
    private Integer signAmount;

    @ApiModelProperty(value = "联系方式")
    private String telephone;

    @ApiModelProperty(value = "主题")
    private String title;

    @ApiModelProperty(value = "宣讲地点")
    private String address;

    @ApiModelProperty(value = "备注")
    private String comment;

    @ApiModelProperty(value = "创建人")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long creatorId;

    @ApiModelProperty(value = "创建人名称")
    private String creatorName;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;


    @ApiModelProperty(value = "宣讲评价结果")
    private PreachFeedbackVO preachFeedback;


    @ApiModelProperty(value = "状态名称")
    public String getStatusName() {
        String statusStr = status.toString();
        String value = "";
        switch (statusStr) {
            case "0":
                value = "删除";
                break;
            case "1":
                value = "草稿";
                break;
            case "2":
                value = "发布";
                break;
            case "3":
                value = "完成";
                break;
            default:
                break;
        }
        return value;
    }


    @ApiModelProperty(value = "状态", example = "0")
    private Long status = 0L;


    @ApiModelProperty(value = "是否相关人", example = "false")
    private Boolean isRelation = false;
}

