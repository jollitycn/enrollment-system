package com.educiot.recruit.data.entity.query;

import com.educiot.common.base.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author liuhao
 * @program educiot-recruit
 * @description：获取数据字典列表
 * @date Create in 2020/4/13
 */
@Data
@ApiModel(value = "获取数据字典列表入参实体")
@EqualsAndHashCode(callSuper = true)
public class ListDictionaryQuery extends BaseQuery {

    @ApiModelProperty(value = "父节点ID")
    private Long parentId;

    @ApiModelProperty(value = "招生学校ID")
    private Long recruitSchoolId;
}
