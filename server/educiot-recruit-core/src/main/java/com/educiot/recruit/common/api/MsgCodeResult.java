package com.educiot.recruit.common.api;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Jason Hong
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MsgCodeResult extends APIResult {
    private MsgCode data;
}
