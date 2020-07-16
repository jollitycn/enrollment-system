package com.educiot.recruit.data.entity.vo;

import com.educiot.recruit.common.base.BaseVO;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Pan Juncai
 * @version 1.0
 * @date 2020/4/29 17:02
 */
@Data
@ApiModel(value = "小程序注册获取招生学校信息")
@EqualsAndHashCode(callSuper = true)
public class RecruitSchoolIdAndNameVO extends BaseVO {
    @ApiModelProperty(value = "招生学校ID", name = "recruitSchoolId")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long recruitSchoolId;

    @ApiModelProperty(value = "招生学校名称", name = "recruitSchoolName")
    private String recruitSchoolName;

    @ApiModelProperty(value = "平台招生学校ID", name = "apiId")
    private Long apiId;


}
