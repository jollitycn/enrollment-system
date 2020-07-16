package com.educiot.recruit.data.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
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
 * 接待日信息表
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("e_r_openday")
@ApiModel(value="Openday对象", description="接待日信息表")
public class Openday implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "接待日信息ID")
    @TableId(value = "openday_id", type = IdType.ID_WORKER)
    private Long opendayId;

    @ApiModelProperty(value = "到校咨询ID")
    private Long consultationId;

    @ApiModelProperty(value = "接待人ID")
    private Long receptionistId;

    @ApiModelProperty(value = "接待人名称")
    private String receptionistName;

    @ApiModelProperty(value = "接待人数")
    private Integer amount;

    @ApiModelProperty(value = "接待日期")
    private LocalDate opendayDate;

    @ApiModelProperty(value = "联系方式")
    private String telephone;

    @ApiModelProperty(value = "接待行程")
    private String schedule;

    @ApiModelProperty(value = "创建人")
    private Long creatorId;

    @ApiModelProperty(value = "创建人名称")
    private String creatorName;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "是否是接待日（0：否，1：是）")
    private Integer isOpenday;

    @ApiModelProperty(value = "接待地址")
    private String address;
}
