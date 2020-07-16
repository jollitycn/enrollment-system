package com.educiot.common.api;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Jason Hong
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "OrgStructResult")
public class OrgStructResult extends APIResult {
    private OrgStruct data;
}