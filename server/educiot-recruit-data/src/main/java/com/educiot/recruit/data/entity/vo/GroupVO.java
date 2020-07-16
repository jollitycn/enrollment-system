package com.educiot.recruit.data.entity.vo;

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

/**
 * @author Pan Juncai
 * @version 1.0
 * @date 2020/4/10 10:21
 */
@Data
@ApiModel(value = "招生分组信息返回实体VO")
@EqualsAndHashCode(callSuper = true)
public class GroupVO extends BaseVO {
    @ApiModelProperty(value = "招生组ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long groupId;

    @ApiModelProperty(value = "组别名称")
    private String name;

    @ApiModelProperty(value = "创建人名称")
    private String creatorName;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = Constant.Sys.LOCALDATETIME_FORMATTER, timezone = "GMT+8")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "小组人数")
    private Integer countManager;

    @ApiModelProperty(value = "学校负责数")
    private Integer countSourceSchool;
}
