package com.educiot.common.api;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author Jason Hong
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TermInfoResult extends APIResult {
    private List<TermInfo> data;
}
