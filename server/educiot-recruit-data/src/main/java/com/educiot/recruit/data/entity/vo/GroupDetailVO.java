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
import java.util.List;

/**
 * @author Pan Juncai
 * @version 1.0
 * @date 2020/4/15 16:31
 */
@Data
@ApiModel(value = "分组详情返回实体VO")
@EqualsAndHashCode(callSuper = true)
public class GroupDetailVO extends BaseVO {
    @ApiModelProperty(value = "招生组ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long groupId;

    @ApiModelProperty(value = "招生组信息ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long groupInfoId;

    @ApiModelProperty(value = "组别名称")
    private String name;

    @ApiModelProperty(value = "创建人名称")
    private String creatorName;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = Constant.Sys.LOCALDATETIME_FORMATTER, timezone = "GMT+8")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "说明")
    private String comment;

    @ApiModelProperty(value = "所属部门（来着api组织结构）")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long deptId;

    @ApiModelProperty(value = "部门名称")
    private String deptName;

    @ApiModelProperty(value = "所在区域（来着数据字典）")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long areaId;

    @ApiModelProperty(value = "招生目标数量")
    private Integer targetAmount;

    @ApiModelProperty(value = "负责人信息")
    private List<ChargerVO> chargerList;

    @ApiModelProperty(value = "负责学校信息")
    private List<SourceSchoolVO> sourceSchoolList;
}
