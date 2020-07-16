package com.educiot.recruit.data.common.util;

import com.educiot.recruit.common.base.CodeMsg;
import com.educiot.recruit.common.base.MyExcption;

/**
 * @author Administrator
 */
public class ValidateUtil {
    public static void validateSemester(Long semesterId) throws MyExcption {
        if (semesterId == null) {
            throw new MyExcption(CodeMsg.SEMESTER_NOT_FOUND);
        }
    }
}
