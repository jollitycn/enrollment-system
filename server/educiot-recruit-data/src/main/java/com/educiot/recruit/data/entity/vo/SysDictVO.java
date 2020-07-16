package com.educiot.recruit.data.entity.vo;

import com.educiot.common.base.BaseVO;
import com.educiot.common.constant.Constant;
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
 * @author liuhao
 * @program educiot-recruit
 * @description：获取全部数据字典返回实体
 * @date Create in 2020/4/13
 */
@Data
@ApiModel(value = "获取全部数据字典返回实体")
@EqualsAndHashCode(callSuper = true)
public class SysDictVO extends BaseVO {

    @ApiModelProperty(value = "数字字典id")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long dictionaryId;

    @ApiModelProperty(value = "父id")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long parentId;

    @ApiModelProperty(value = "数据编码")
    private String dataCode;

    @ApiModelProperty(value = "数据类型（1：文本，2：布尔值，3：数值，4：日期）")
    private String dataType;

    @ApiModelProperty(value = "数据名称/数据值")
    private String dataValue;

    @ApiModelProperty(value = "数据英文名称")
    private String dataEnglishValue;

    @ApiModelProperty(value = "数据说明")
    private String dataDesc;

    @ApiModelProperty(value = "数据顺序")
    private Integer dataOrder;

    @ApiModelProperty(value = "是否只读（0：否，1：只读）")
    private Integer isReadOnly;

    @ApiModelProperty(value = "状态（0：正常，1：删除）")
    private Integer isDeleted;

    @ApiModelProperty(value = "最后修改人")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long modifyId;

    @ApiModelProperty(value = "修改人名称")
    private String modifyName;

    @ApiModelProperty(value = "修改时间")
    @JsonFormat(pattern = Constant.Sys.LOCALDATETIME_FORMATTER, timezone = "GMT+8")
    private LocalDateTime modifyTime;

    @ApiModelProperty(value = "招生学校ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long recruitSchoolId;

    @ApiModelProperty(value = "下级菜单")
    private List<SysDictVO> subs;
}
