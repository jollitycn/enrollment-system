package com.educiot.recruit.common.api;

import com.educiot.common.api.base.BaseVO;
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
