package com.educiot.recruit.data.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.educiot.common.base.BaseVO;
import com.educiot.common.constant.Constant;
import com.educiot.common.util.DateUtils;
import com.educiot.recruit.data.entity.CommunicationRecord;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @program: server
 * @description: 学生公关信息和沟通记录封装实体VO
 * @author: Mr.AI
 * @create: 2020-05-09
 **/
@Data
@ApiModel(value = "学生公关信息和沟通记录封装实体VO")
@EqualsAndHashCode(callSuper = true)
public class StudentPublicRelationRecordVO extends BaseVO {

    @ApiModelProperty(value = "学生公关ID")
    @TableId(value = "student_public_relation_id", type = IdType.ID_WORKER)
    @JsonSerialize(using= ToStringSerializer.class)
    private Long studentPublicRelationId;

    @ApiModelProperty(value = "学生基本信息ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long studentInfo;

    @ApiModelProperty(value = "招生学校ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long recruitSchoolId;

    @ApiModelProperty(value = "生源学校ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long sourceSchoolId;

    @ApiModelProperty(value = "班级ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long classId;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = Constant.Sys.LOCALDATETIME_FORMATTER, timezone = "GMT+8")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "状态（0：删除，1：正常）")
    private Integer status;

    @ApiModelProperty(value = "公关等级（0：高，1：中，2：低）")
    private Integer level;

    @ApiModelProperty(value = "意向原因")
    private String reason;

    @ApiModelProperty(value = "公关方向")
    private String direction;

    @ApiModelProperty(value = "公关类型0：宣讲，1：班主任指导，2：电话，3：现场咨询）")
    private String relationType;

    @ApiModelProperty(value = "负责人")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long creatorId;

    @ApiModelProperty(value = "负责人名称")
    private String creatorName;

    @ApiModelProperty(value = "报名状态（0：已交订金，1：已交全款，2：已退款，3：强意向生，4：已报名）")
    private Integer registerStatus;

    @ApiModelProperty(value = "学期")
    private String semester;

    @ApiModelProperty(value = "招生组ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long groupId;

    @ApiModelProperty(value = "学期ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long semesterId;

    @ApiModelProperty(value = "登记编号")
    private Integer studentNo;

    @ApiModelProperty(value = "状态改变时间")
    @JsonFormat(pattern= DateUtils.DATE_FMT_3,timezone = "GMT+8")
    private LocalDate statusChangeTime;

    @ApiModelProperty(value = "区域名称")
    private String name;

    @ApiModelProperty(value = "沟通记录")
    private List<CommunicationRecord> communicationRecords;

}
