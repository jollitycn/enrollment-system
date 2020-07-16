package com.educiot.common.api;

import com.educiot.common.base.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Jason Hong
 */
@Data

@EqualsAndHashCode(callSuper = false)

public class MsgCode extends BaseVO {
    private String code;
}
