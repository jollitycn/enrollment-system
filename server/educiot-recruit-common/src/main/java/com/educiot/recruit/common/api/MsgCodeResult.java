package com.educiot.recruit.common.api;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Jason Hong
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString( callSuper = true)
public class MsgCodeResult extends APIResult {
    private MsgCode data;
}
