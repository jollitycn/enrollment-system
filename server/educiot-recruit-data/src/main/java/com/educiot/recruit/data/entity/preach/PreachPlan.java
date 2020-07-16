package com.educiot.recruit.data.entity.preach;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.educiot.recruit.common.constant.Constant;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 招生宣讲计划
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("e_r_preach_plan")
@ApiModel(value = "PreachPlan对象", description = "招生宣讲计划")
public class PreachPlan implements Serializable {

    @ApiModelProperty(value = "状态", example = "0")
    private int status = 0;

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "招生宣讲计划ID")
    @TableId(value = "preach_plan_id", type = IdType.ID_WORKER)
    private Long preachPlanId;

//    @ApiModelProperty(value = "年份")
//    private Integer year;

//    @ApiModelProperty(value = "学期")
//    private String semester;

    @ApiModelProperty(value = "宣讲时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constant.Sys.LOCALDATETIME_FORMATTER)
    private LocalDateTime preachTime;

    @ApiModelProperty(value = "结束时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constant.Sys.LOCALDATETIME_FORMATTER)
    private LocalDateTime endTime;

    @ApiModelProperty(value = "主讲人ID")
    private Long speakerId;

    @ApiModelProperty(value = "主讲人名称")
    private String speakerName;

    @ApiModelProperty(value = "计划人数")
    private Integer planAmount;

    @ApiModelProperty(value = "联系方式")
    private String telephone;

    @ApiModelProperty(value = "主题")
    private String title;

    @ApiModelProperty(value = "宣讲地点")
    private String address;

    @ApiModelProperty(value = "备注")
    private String comment;

    @ApiModelProperty(value = "创建人")
    private Long creatorId;

    @ApiModelProperty(value = "创建人名称")
    private String creatorName;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constant.Sys.LOCALDATETIME_FORMATTER)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "学期接口ID")
    private Long semesterId;


    @ApiModelProperty(value = "学期名称")
    private String semester;

    @ApiModelProperty(value = "状态名称")
    public String getStatusName() {
        String value = "";
        switch (status) {
            case 0:
                value = "删除";
                break;
            case 1:
                value = "草稿";
                break;
            case 2:
                value = "发布";
                break;
            case 3:
                value = "完成";
                break;
            default:
                break;
        }
        return value;
    }

    public enum StatusEnum {
        delete(0, "删除"),
        draft(1, "草稿"),
        publish(2, "发布"),
        finish(3, "完成");

        /**
         * 状态
         */
        private final Integer status;
        /**
         * 说明
         */
        private final String desc;

        StatusEnum(Integer status, String desc) {
            this.status = status;
            this.desc = desc;
        }
    }
}
