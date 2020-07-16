package com.educiot.recruit.data.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 招生组表
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("e_r_group")
@ApiModel(value="Group对象", description="招生组表")
public class Group implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "招生组ID")
    @TableId(value = "group_id", type = IdType.ID_WORKER)
    private Long groupId;

    @ApiModelProperty(value = "招生组信息ID")
    private Long groupInfoId;

    @ApiModelProperty(value = "组别名称")
    private String name;

    @ApiModelProperty(value = "创建人")
    private Long creatorId;

    @ApiModelProperty(value = "创建人名称")
    private String creatorName;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "说明")
    private String comment;

    @ApiModelProperty(value = "所属部门（来着api组织结构）")
    private Long deptId;

    @ApiModelProperty(value = "部门名称")
    private String deptName;

    @ApiModelProperty(value = "所在区域（来着数据字典）")
    private Long areaId;

    @ApiModelProperty(value = "状态（0：删除，1：正常）")
    private Integer status;

    @ApiModelProperty(value = "招生目标数量")
    private Integer targetAmount;
}
