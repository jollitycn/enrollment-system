package com.educiot.recruit.data.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 成绩表
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("e_r_score")
@ApiModel(value="Score对象", description="成绩表")
public class Score implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "成绩ID")
    @TableId(value = "score_id", type = IdType.ID_WORKER)
    @JsonSerialize(using= ToStringSerializer.class)
    private Long scoreId;

    @ApiModelProperty(value = "学生在校信息ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long inSchoolId;

    @ApiModelProperty(value = "科目")
    private String course;

    @ApiModelProperty(value = "成绩")
    private Double score;


}
