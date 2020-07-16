package com.educiot.recruit.data.service.preach;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.educiot.recruit.data.entity.preach.PreachFeedback;
import com.educiot.recruit.data.entity.query.preach.PreachFeedbackAddQuery;
import com.educiot.recruit.data.entity.query.preach.PreachFeedbackPageQuery;
import com.educiot.recruit.data.entity.query.preach.PreachFeedbackQuery;
import com.educiot.recruit.data.entity.vo.preach.PreachFeedbackCountVO;
import com.educiot.recruit.data.entity.vo.preach.PreachFeedbackVO;

import java.util.List;

/**
 * <p>
 * 宣讲反馈表 服务类
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
public interface IPreachFeedbackService extends IService<PreachFeedback> {

    IPage<PreachFeedbackVO> listPage(PreachFeedbackPageQuery query);

    List<PreachFeedbackCountVO> feedBackCount(PreachFeedbackQuery query);

//    PreachFeedback add(LoginUser loginUser, PreachFeedbackAddQuery query);

    PreachFeedback add(Long studentPublicRelationId, Long userId, String userName, PreachFeedbackAddQuery query);

    PreachFeedbackVO detailByStudentIdAndPreachPlanId(Long studentId, Long preachPlanId);

    PreachFeedbackVO detailByStudentPublicRelationIdAndPreachPlanId(Long studentPublicRelationId, Long preachPlanId);
}
