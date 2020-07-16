package com.educiot.recruit.data.entity.query;

import com.educiot.recruit.common.base.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author liuhao
 * @program educiot-recruit
 * @description：签到
 * @date Create in 2020/4/17
 */
@Data
@ApiModel(value = "咨询签到入参实体类")
@EqualsAndHashCode(callSuper = true)
public class AttendQuery extends BaseQuery {

    @ApiModelProperty("招生咨询登记ID列表")
    private List<Long> consultationRegisterIdList;

}
