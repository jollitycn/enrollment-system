package com.educiot.recruit.data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 招生目标分解(树形表) 
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("e_r_target_decomposition")
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="TargetDecomposition对象", description="招生目标分解(树形表) ")
public class TargetDecomposition implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "目标分解ID")
    @TableId(value = "target_decomposition_id", type = IdType.INPUT)
    private String targetDecompositionId;

    @ApiModelProperty(value = "目标ID")
    private Long targetId;

    @ApiModelProperty(value = "父节点ID")
    private String parentId;

    @ApiModelProperty(value = "类型（0：渠道，1：专业）")
    private Integer type;

    @ApiModelProperty(value = "节点ID（平台接口ID）")
    private String nodeId;

    @ApiModelProperty(value = "节点名称")
    private String nodeName;

    @ApiModelProperty(value = "目标数量")
    private Integer targetAmount;

    @ApiModelProperty(value = "说明")
    private String comment;

}
