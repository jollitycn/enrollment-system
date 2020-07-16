package com.educiot.recruit.data.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.educiot.common.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: server
 * @description: 学生报名进度返回封装实体VO
 * @author: Mr.AI
 * @create: 2020-04-21
 **/

@Data
@ApiModel(value = "学生报名进度返回封装实体VO")
@EqualsAndHashCode(callSuper = true)
public class ProgressVO extends BaseVO{

    @ApiModelProperty(value = "学生报名进度ID")
    @TableId(value = "progress_id", type = IdType.ID_WORKER)
    @JsonSerialize(using= ToStringSerializer.class)
    private Long progressId;

    @ApiModelProperty(value = "学生公关ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long studentPublicRelationId;

    @ApiModelProperty(value = "进度类型（0：报名登记，1：交费定位，2：接收通知，3：入学报名）")
    private Integer progressType;

    @ApiModelProperty(value = "进度内容")
    private String progressContent;

    @ApiModelProperty(value = "报名状态")
    private String registerStatus;

}
