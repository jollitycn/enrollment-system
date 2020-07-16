package com.educiot.recruit.data.common;

import com.educiot.common.base.CodeMsg;
import com.educiot.common.base.MyExcption;
import com.educiot.recruit.data.entity.query.preach.PreachPlanAddQuery;

/**
 * @author Administrator
 */
public class ValidateUtil {
    public static void validateSemester(Long semesterId) throws MyExcption {
        if (semesterId == null) {
            throw new MyExcption(CodeMsg.SEMESTER_NOT_FOUND);
        }
    }
    public static void checkSpeakerAndAssistant(PreachPlanAddQuery query) throws MyExcption {
        for (IdAndName ass : query.getAssistantIds()) {
            if (ass.getId().equals(query.getSpeakerId())) {
                throw new MyExcption(CodeMsg.PREACH_PLAN_SPEAKERID_ASSISTANTID_DUPLICATE);
            }
        }
    }
}
