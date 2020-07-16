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
 * 省市区县
 * </p>
 *
 * @author LiuHao
 * @since 2020-06-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("e_r_province")
@ApiModel(value="Province对象", description="省市区县")
public class Province implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER)
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "-1：顶级节点")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long parentid;

    @ApiModelProperty(value = "1：省2：市3：区4：县")
    private Integer type;

    private Integer level;

    @ApiModelProperty(value = "备用，不要处理")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long owner;


}
