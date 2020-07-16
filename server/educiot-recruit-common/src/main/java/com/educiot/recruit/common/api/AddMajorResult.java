package com.educiot.recruit.common.api;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Jason Hong
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AddMajorResult extends APIResult {
    /**
     *
     */
    private MajorInfo data;
}
