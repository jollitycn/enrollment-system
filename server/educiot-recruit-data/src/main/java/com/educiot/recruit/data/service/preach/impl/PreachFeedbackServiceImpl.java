package com.educiot.recruit.data.service.preach.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.educiot.recruit.data.entity.preach.PreachFeedback;
import com.educiot.recruit.data.entity.query.preach.PreachFeedbackAddQuery;
import com.educiot.recruit.data.entity.query.preach.PreachFeedbackPageQuery;
import com.educiot.recruit.data.entity.query.preach.PreachFeedbackQuery;
import com.educiot.recruit.data.entity.vo.preach.PreachFeedbackCountVO;
import com.educiot.recruit.data.entity.vo.preach.PreachFeedbackVO;
import com.educiot.recruit.data.mapper.preach.PreachFeedbackMapper;
import com.educiot.recruit.data.service.preach.IPreachFeedbackService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 宣讲反馈表 服务实现类
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-13
 */
@Service
public class PreachFeedbackServiceImpl extends ServiceImpl<PreachFeedbackMapper, PreachFeedback> implements IPreachFeedbackService {
    @Override
    public IPage<PreachFeedbackVO> listPage(PreachFeedbackPageQuery query) {
        IPage<PreachFeedbackVO> page = new Page<>(query.getPageNum(), query.getPageSize());
        List<PreachFeedbackVO> list = baseMapper.listPage(page, query);
        page.setRecords(list);
        return page;
    }

    @Override
    public List<PreachFeedbackCountVO> feedBackCount(PreachFeedbackQuery query) {
        List<PreachFeedbackCountVO> list = baseMapper.feedBackCount(query);
        return list;
    }
//
//
//    @Override
//    public PreachFeedback add(LoginUser loginUser, PreachFeedbackAddQuery query) {
//        return add(loginUser.getStudentPublicRelationId(),loginUser.getApiInfo().getUserId(), loginUser.getApiInfo().getUserName(), query);
//    }

    @Override
    public PreachFeedback add(Long studentPublicRelationId, Long userId, String userName, PreachFeedbackAddQuery query) {
        LocalDateTime now = LocalDateTime.now();
        PreachFeedback bean = new PreachFeedback();
        BeanUtils.copyProperties(query, bean);
        bean.setCreateTime(now);
        bean.setCreatorId(userId);
        bean.setCreatorName(userName);
        bean.setStudentPublicRelationId(studentPublicRelationId);
        //TODO  判断是否由公关记录获取当前学生的信息;
        //根据当前登录用户获取公关信息;
        // bean.setStatus(1);
        // bean.set(loginUser.getCurrentSemesterId());
        long result = baseMapper.insert(bean);
        return bean;
    }

    /**
     * 根据学生id，宣讲计划id获取宣讲反馈信息
     *
     * @param studentId
     * @param preachPlanId
     * @return
     */
    @Override
    public PreachFeedbackVO detailByStudentIdAndPreachPlanId(Long studentId, Long preachPlanId) {
        QueryWrapper<PreachFeedback> wrapper = new QueryWrapper<>();
        wrapper.eq(PreachFeedback.Columns.PREACH_PLAN_ID, preachPlanId);
        wrapper.eq(PreachFeedback.Columns.CREATOR_ID, studentId);
        PreachFeedback bean = baseMapper.selectOne(wrapper);
        PreachFeedbackVO vo = null;
        if (bean != null) {
            vo = new PreachFeedbackVO();
            BeanUtils.copyProperties(bean, vo);
        }
        return vo;
    }

    @Override
    public PreachFeedbackVO detailByStudentPublicRelationIdAndPreachPlanId(Long studentPublicRelationId, Long preachPlanId) {
        QueryWrapper<PreachFeedback> wrapper = new QueryWrapper<>();
        wrapper.eq(PreachFeedback.Columns.PREACH_PLAN_ID, preachPlanId);
        wrapper.eq(PreachFeedback.Columns.STUDENT_PUBLIC_RELATION_ID, studentPublicRelationId);
        PreachFeedback bean = baseMapper.selectOne(wrapper);
        PreachFeedbackVO vo = null;
        if (bean != null) {
            vo = new PreachFeedbackVO();
            BeanUtils.copyProperties(bean, vo);
        }
        return vo;
    }
}
