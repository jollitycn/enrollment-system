package com.educiot.server.common;

import com.educiot.common.api.APIRequest;
import com.educiot.common.base.MyExcption;

/**
 * 基础controller工具
 *
 * @author Pan Juncai
 * @version 1.00.1.8.O
 * @date 2019/8/23 19:59
 */
public class BaseController extends com.educiot.common.api.base.BaseController {

    protected APIRequest getAPIRequest() throws MyExcption {
        return null;
    }

    protected void destoryAPIRequest() {

    }
}
