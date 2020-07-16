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
 * 招生咨询登记表
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("e_r_consultation_register")
@ApiModel(value="ConsultationRegister对象", description="招生咨询登记表")
public class ConsultationRegister implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "招生咨询登记ID")
    @TableId(value = "consultation_register_id", type = IdType.ID_WORKER)
    private Long consultationRegisterId;

    @ApiModelProperty(value = "到校咨询ID")
    private Long consultationId;

    @ApiModelProperty(value = "咨询类型（0：个人，1：学校）")
    private Integer consultationType;

    @ApiModelProperty(value = "生源学校ID")
    private Long sourceSchoolId;

    @ApiModelProperty(value = "是否接待日（0：是，1：否）")
    private Integer isOpenday;

    @ApiModelProperty(value = "班级ID")
    private Long classId;

    @ApiModelProperty(value = "联系方式")
    private String telephone;

    @ApiModelProperty(value = "咨询日期")
    private LocalDate consultationDate;

    @ApiModelProperty(value = "到校时间")
    private LocalDateTime attendTime;

    @ApiModelProperty(value = "是否用餐（0：是，1：否）")
    private Integer isLunch;

    @ApiModelProperty(value = "说明")
    private String comment;

    @ApiModelProperty(value = "带队老师")
    private String teacher;

    @ApiModelProperty(value = "人数")
    private Integer amount;

    @ApiModelProperty(value = "创建人（负责人）")
    private Long creatorId;

    @ApiModelProperty(value = "创建人名称（负责人名称）")
    private String creatorName;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "学生公关ID")
    private Long studentPublicRelationId;

    @ApiModelProperty(value = "学生姓名")
    private String studentName;

    @ApiModelProperty(value = "是否签到（0：否，1：是）")
    private Integer isAttend;
}
