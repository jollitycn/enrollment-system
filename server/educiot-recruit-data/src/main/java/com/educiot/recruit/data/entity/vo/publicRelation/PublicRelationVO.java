package com.educiot.recruit.data.entity.vo.publicRelation;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.educiot.recruit.common.base.BaseVO;
import com.educiot.recruit.common.constant.Constant;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@ApiModel(value = "学校公关信息视图类")
@EqualsAndHashCode()
public class PublicRelationVO extends BaseVO {

    @ApiModelProperty(value = "学校公关ID")
    @TableId(value = "public_relation", type = IdType.ID_WORKER)
    @JsonSerialize(using= ToStringSerializer.class)
    private Long publicRelation;

    @ApiModelProperty(value = "招生组生源学校关联ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long groupSourceRelationId;

    @ApiModelProperty(value = "招生组ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long groupId;

    @ApiModelProperty(value = "生源学校")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long sourceSchoolId;

    @ApiModelProperty(value = "开始时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constant.Sys.LOCALDATETIME_FORMATTER)
    private LocalDateTime startTime;

    @ApiModelProperty(value = "结束时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constant.Sys.LOCALDATETIME_FORMATTER)
    private LocalDateTime endTime;

    @ApiModelProperty(value = "沟通人")
    private String contacter;

    @ApiModelProperty(value = "联系方式")
    private String telephone;

    @ApiModelProperty(value = "学校拜访内容")
    private String content;

    @ApiModelProperty(value = "创建人（负责人）")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long creatorId;

    @ApiModelProperty(value = "是否本人")
    private Boolean isMine;


    @ApiModelProperty(value = "创建人名称（负责人名称）")
    private String creatorName;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;


}
