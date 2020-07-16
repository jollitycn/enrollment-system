package com.educiot.common.api;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Jason Hong
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "AddTermResult")
public class AddTermResult extends APIResult {
    private TermInfo data;
}