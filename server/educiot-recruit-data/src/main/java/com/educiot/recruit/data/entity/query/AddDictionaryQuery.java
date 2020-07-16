package com.educiot.recruit.data.entity.query;

import com.educiot.common.base.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author liuhao
 * @program educiot-recruit
 * @description：新增数据字典
 * @date Create in 2020/4/13
 */
@Data
@ApiModel(value = "新增数据字典入参实体")
@EqualsAndHashCode(callSuper = true)
public class AddDictionaryQuery extends BaseQuery {

    @ApiModelProperty(value = "父id", required = true)
    private Long parentId;

    @ApiModelProperty(value = "数据编码", required = true)
    private String dataCode;

    @ApiModelProperty(value = "数据类型（1：文本，2：布尔值，3：数值，4：日期）", required = true)
    private String dataType;

    @ApiModelProperty(value = "数据名称/数据值", required = true)
    private String dataValue;

    @ApiModelProperty(value = "数据英文名称", required = true)
    private String dataEnglishValue;

    @ApiModelProperty(value = "数据说明")
    private String dataDesc;

    @ApiModelProperty(value = "数据顺序")
    private Integer dataOrder;

    @ApiModelProperty(value = "是否只读（0：否，1：只读）")
    private Integer isReadOnly;

}
