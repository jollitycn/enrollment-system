package com.educiot.recruit.data.entity.query;

import com.educiot.common.base.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: server
 * @description: 诉求管理信息查询参数封装类
 * @author: Mr.AI
 * @create: 2020-04-22
 **/

@Data
@ApiModel(value = "诉求管理信息查询参数封装类")
@EqualsAndHashCode(callSuper = true)
public class RequestQuery extends BaseQuery {

    @ApiModelProperty(value = "学生公关ID")
    private Long studentPublicRelationId;

    @ApiModelProperty(value = "招生学校ID")
    private Long recruitSchoolId;

    @ApiModelProperty(value = "诉求状态")
    private Integer status;

    @ApiModelProperty(value = "学生基本信息Id")
    private Long studentInfo;

    @ApiModelProperty(value = "负责人ID")
    private Long creatorId;

}
