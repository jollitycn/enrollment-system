package com.educiot.recruit.data.entity.query;

import com.educiot.common.base.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: server
 * @description: 分页查询计划列表入参实体
 * @author: Mr.AI
 * @create: 2020-04-10 14:20
 **/
@Data
@ApiModel(value = "分页查询计划列表入参实体")
@EqualsAndHashCode(callSuper = true)
public class ListPlanPageQuery extends BasePageQuery {

    /**
     * 年份/年度
     */
    @ApiModelProperty(value = "学期",name = "semester")
    protected String semester;

    /**
     * 标题
     */
    @ApiModelProperty(value = "标题",name = "title")
    protected String title;

    /**
     * 招生学校
     */
    @ApiModelProperty(value = "招生学校",name = "recruitSchoolId")
    protected Long recruitSchoolId;

}
