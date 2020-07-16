package com.educiot.recruit.data.entity.vo;

import com.educiot.common.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: server
 * @description: APP学生基本信息返回填写状态封装实体VO
 * @author: Mr.AI
 * @create: 2020-05-12
 **/
@Data
@ApiModel(value = "意向生id和姓名返回实体VO")
@EqualsAndHashCode(callSuper = true)
public class StudentInfoStatusVO extends BaseVO {

    @ApiModelProperty(value = "选择专业装填")
    private Boolean aimMajor;

    @ApiModelProperty(value = "在校信息状态")
    private Boolean inSchool;

    @ApiModelProperty(value = "公关信息状态")
    private Boolean publicRelation;

    @ApiModelProperty(value = "招生渠道状态")
    private Boolean recruitWay;

}
