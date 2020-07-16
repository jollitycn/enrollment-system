package com.educiot.recruit.data.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.educiot.recruit.common.base.BaseVO;
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
@ApiModel(value = "宣讲计划信息返回封装类")
@EqualsAndHashCode()
public class DutyVO extends BaseVO {

//    @ApiModelProperty(value = "年份")
//    private Integer year;

//    @ApiModelProperty(value = "学期")
//    private String semester;

    @ApiModelProperty(value = "值班计划ID")
    @TableId(value = "id", type = IdType.ID_WORKER)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "开始时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constant.Sys.LOCALDATETIME_FORMATTER)
    private LocalDateTime startTime;

    @ApiModelProperty(value = "类型（1,宣讲计划，2、到校咨询）")
    private Integer type;


    @ApiModelProperty(value = "类型（1,宣讲计划，2、到校咨询）")
    public String getTypeName(){
        String statusStr = type.toString();
        String value = "";
        switch (statusStr) {
            case "0":
                value = "到校咨询-接待日";
                break;
            case "1":
                value = "到校咨询-日常";
                break;
            case "2":
                value = "宣讲计划-主讲";
                break;
            case "3":
                value = "宣讲计划-协助";
                break;
            default:
                break;
        }
        return value;
    }

    @ApiModelProperty(value = "结束时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constant.Sys.LOCALDATETIME_FORMATTER)
    private LocalDateTime endTime;

    @ApiModelProperty(value = "值班日期")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private LocalDateTime dutyDate;

    @ApiModelProperty(value = "负责人ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    @ApiModelProperty(value = "负责人名称")
    private String userName;

    @ApiModelProperty(value = "是否冲突（1,冲突，2、正常）")
    private boolean isConflict;
}