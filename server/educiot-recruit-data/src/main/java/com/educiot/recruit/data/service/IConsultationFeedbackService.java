package com.educiot.recruit.data.service;

import com.educiot.recruit.data.entity.ConsultationFeedback;
import com.baomidou.mybatisplus.extension.service.IService;
import com.educiot.recruit.data.entity.query.AddConsultationFeedbackQuery;

/**
 * <p>
 * 咨询反馈表 服务类
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
public interface IConsultationFeedbackService extends IService<ConsultationFeedback> {

    /**
     * 新增咨询反馈
     * @param addConsultationFeedbackQuery
     * @return true 添加成功
     */
    boolean addConsultationFeedback(AddConsultationFeedbackQuery addConsultationFeedbackQuery,Long userId,String userName);
}
