package com.educiot.recruit.data.entity.query;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.educiot.recruit.common.base.BaseQuery;
import com.educiot.recruit.common.constant.Constant;
import com.educiot.recruit.data.entity.Score;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @program: server
 * @description: 学生在校信息封装实体
 * @author: Mr.AI
 * @create: 2020-04-21
 **/

@Data
@ApiModel(value = "学生在校信息封装实体")
@EqualsAndHashCode(callSuper = true)
public class AddInSchoolQuery extends BaseQuery {

    @ApiModelProperty(value = "学生在校信息ID")
    @TableId(value = "in_school_id", type = IdType.ID_WORKER)
    @JsonSerialize(using= ToStringSerializer.class)
    private Long inSchoolId;

    @ApiModelProperty(value = "学生基本信息ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long studentInfo;

    @ApiModelProperty(value = "原学籍号")
    private String schoolNo;

    @ApiModelProperty(value = "特长")
    private String talent;

    @ApiModelProperty(value = "创建人")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long creatorId;

    @ApiModelProperty(value = "创建人名称")
    private String creatorName;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = Constant.Sys.LOCALDATETIME_FORMATTER, timezone = "GMT+8")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "学生最近成绩信息")
    private List<Score> scores;

    @ApiModelProperty(value = "生源学校ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long sourceSchoolId;

    @ApiModelProperty(value = "班级ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long classId;

    @ApiModelProperty(value = "学生公关ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long studentPublicRelationId;

    @ApiModelProperty(value = "招生学校ID")
    private Long recruitSchoolId;

}
