package com.educiot.recruit.data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 学期表
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("e_r_semester")
@ApiModel(value="Semester对象", description="学期表")
public class Semester implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "学期ID")
    @TableId(value = "semester_id", type = IdType.ID_WORKER)
    private Long semesterId;

    @ApiModelProperty(value = "招生学校ID")
    private Long recruitSchoolId;

    @ApiModelProperty(value = "学期接口ID")
    private Long semesterApiId;

    @ApiModelProperty(value = "学期名称")
    private String semesterName;

    @ApiModelProperty(value = "同步时间")
    private Date syncTime;


}
