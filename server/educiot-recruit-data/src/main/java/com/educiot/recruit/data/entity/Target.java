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
 * 目标
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("e_r_target")
@ApiModel(value="Target对象", description="目标")
public class Target implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "目标ID")
    @TableId(value = "target_id", type = IdType.ID_WORKER)
    private Long targetId;

    @ApiModelProperty(value = "学期")
    private String semester;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "目标数量")
    private Integer targetAmount;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "创建人")
    private String creatorName;

    @ApiModelProperty(value = "创建人id")
    private Long creatorId;

    @ApiModelProperty(value = "最后修改人id")
    private Long modifyId;

    @ApiModelProperty(value = "最后修改人")
    private String modifyName;

    @ApiModelProperty(value = "最后修改时间")
    private Date modifyTime;

    @ApiModelProperty(value = "学期ID")
    private Long semesterId;

    @ApiModelProperty(value = "招生学校ID")
    private Long recruitSchoolId;
}
