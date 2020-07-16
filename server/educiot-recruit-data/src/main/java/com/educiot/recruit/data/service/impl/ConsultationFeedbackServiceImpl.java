package com.educiot.recruit.data.service.impl;

import com.educiot.recruit.data.entity.ConsultationFeedback;
import com.educiot.recruit.data.entity.query.AddConsultationFeedbackQuery;
import com.educiot.recruit.data.mapper.ConsultationFeedbackMapper;
import com.educiot.recruit.data.service.IConsultationFeedbackService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.educiot.recruit.data.LoginUser;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 * 咨询反馈表 服务实现类
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-13
 */
@Service
public class ConsultationFeedbackServiceImpl extends ServiceImpl<ConsultationFeedbackMapper, ConsultationFeedback> implements IConsultationFeedbackService {

    /**
     * 新增咨询反馈
     * @param addConsultationFeedbackQuery
     * @param loginUser
     * @return true 添加成功
     */
    @Override
    public boolean addConsultationFeedback(AddConsultationFeedbackQuery addConsultationFeedbackQuery, LoginUser loginUser) {

        ConsultationFeedback consultationFeedback = new ConsultationFeedback();
        BeanUtils.copyProperties(addConsultationFeedbackQuery, consultationFeedback);

        //填充创建信息
        consultationFeedback.setCreateTime(LocalDateTime.now());
        consultationFeedback.setCreatorId(loginUser.getApiInfo().getUserId());
        consultationFeedback.setCreatorName(loginUser.getApiInfo().getUserName());

        return save(consultationFeedback);
    }
}
