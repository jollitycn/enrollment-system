package com.educiot.recruit.common.api;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author Jason Hong
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MajorListResult extends APIResult {
    private List<MajorInfo> data;
}
