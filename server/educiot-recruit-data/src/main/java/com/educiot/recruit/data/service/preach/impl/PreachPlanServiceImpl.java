package com.educiot.recruit.data.service.preach.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.educiot.recruit.common.base.BasePageQuery;
import com.educiot.recruit.common.base.CodeMsg;
import com.educiot.recruit.common.base.MyExcption;
import com.educiot.recruit.common.base.Result;
import com.educiot.recruit.common.constant.Constant;
import com.educiot.recruit.data.LoginUser;
import com.educiot.recruit.data.common.IdAndName;
import com.educiot.recruit.data.entity.preach.PreachAssistantRelation;
import com.educiot.recruit.data.entity.preach.PreachPlan;
import com.educiot.recruit.data.entity.preach.PreachSchoolRelation;
import com.educiot.recruit.data.entity.query.common.UpdateStatusQuery;
import com.educiot.recruit.data.entity.query.preach.*;
import com.educiot.recruit.data.entity.vo.preach.PreachPlanVO;
import com.educiot.recruit.data.mapper.preach.PreachPlanMapper;
import com.educiot.recruit.data.service.ITaskService;
import com.educiot.recruit.data.service.preach.IPreachAssistantRelationService;
import com.educiot.recruit.data.service.preach.IPreachFeedbackService;
import com.educiot.recruit.data.service.preach.IPreachPlanService;
import com.educiot.recruit.data.service.preach.IPreachSchoolRelationService;
import com.educiot.recruit.data.common.ValidateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 招生宣讲计划 服务实现类
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-13
 */
@Service
public class PreachPlanServiceImpl extends ServiceImpl<PreachPlanMapper, PreachPlan> implements IPreachPlanService {


    @Resource
    private IPreachAssistantRelationService preachAssistantRelationService;

    @Resource
    private IPreachSchoolRelationService preachSchoolRelationService;


    @Resource
    private IPreachFeedbackService preachFeedbackService;

    @Resource
    private ITaskService taskService;


    @Override
    public Result delete(LoginUser loginUser, Long id) {
        return delete(loginUser.getApiInfo().getUserId(), loginUser.getApiInfo().getUserName(), id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result<?> update(LoginUser loginUser, PreachPlanUpdateQuery query) throws MyExcption {
        return update(loginUser.getApiInfo().getUserId(), loginUser.getApiInfo().getUserName(), query);
    }

    private void removeAndAddRelations(PreachPlanUpdateQuery query) {
        QueryWrapper<PreachAssistantRelation> parWrapper = new QueryWrapper<>();
        parWrapper.eq(PreachSchoolRelation.Columns.PREACH_PLAN_ID, query.getPreachPlanId());
        preachAssistantRelationService.remove(parWrapper);

        List<PreachAssistantRelation> pars = new ArrayList<>();
        for (IdAndName item :
                query.getAssistantIds()) {
            if (item != null) {
                PreachAssistantRelation par = new PreachAssistantRelation();
                par.setPreachPlanId(query.getPreachPlanId());
                par.setChargerId(item.getId());
                par.setChargerName(item.getName());
                pars.add(par);
            }
        }
        preachAssistantRelationService.saveBatch(pars);

        QueryWrapper<PreachSchoolRelation> psrWrapper = new QueryWrapper<>();
        psrWrapper.eq(PreachSchoolRelation.Columns.PREACH_PLAN_ID, query.getPreachPlanId());
        preachSchoolRelationService.remove(psrWrapper);
        List<PreachSchoolRelation> psrs = new ArrayList<>();
        for (Long item : query.getSourceSchoolIds()) {
            PreachSchoolRelation psr = new PreachSchoolRelation();
            psr.setPreachPlanId(query.getPreachPlanId());
            psr.setSourceSchoolId(item);
            psrs.add(psr);
            //preachSchoolRelationService.save(psr);
        }

        preachSchoolRelationService.saveBatch(psrs);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result<?> add(LoginUser loginUser, Long semesterId, String semesterName, PreachPlanAddQuery query) throws MyExcption {
        return add(loginUser.getApiInfo().getUserId(), loginUser.getApiInfo().getUserName(), semesterId, semesterName, query);
    }

    @Override
    public Result<?> update(Long userId, String userName, PreachPlanUpdateQuery query) throws MyExcption {
        ValidateUtil.checkSpeakerAndAssistant(query);
        QueryWrapper<PreachPlan> wrapper = new QueryWrapper<>();
        wrapper.eq(PreachSchoolRelation.Columns.PREACH_PLAN_ID, query.getPreachPlanId());
        wrapper.ne(PreachSchoolRelation.Columns.STATUS, Constant.SYS_ZERO);
        PreachPlan bean = baseMapper.selectOne(wrapper);
        if (null == bean) {
            return Result.error(CodeMsg.ID_NOT_EXIST);
        }
        BeanUtils.copyProperties(query, bean);
        bean.setCreatorId(userId);
        bean.setCreatorName(userName);
        baseMapper.updateById(bean);
        removeAndAddRelations(query);
        taskService.checkPreachDate(bean);
        return Result.success(bean);
    }

    @Override
    public Result<?> delete(Long userId, String userName, Long id) {
        PreachPlan bean = baseMapper.selectById(id);
        if (null == bean) {
            return Result.error(CodeMsg.ID_NOT_EXIST);
        }
        if (bean.getStatus() == Constant.SYS_ZERO) {
            return Result.error(CodeMsg.ID_NOT_EXIST);
        }
        bean.setStatus(Constant.SYS_ZERO);
        baseMapper.updateById(bean);
        return Result.success();
    }

    @Override
    public Result<?> add(Long userId, String userName, Long semesterId, String semesterName, PreachPlanAddQuery query) throws MyExcption {
        ValidateUtil.validateSemester(semesterId);
        LocalDateTime now = LocalDateTime.now();
        PreachPlan bean = new PreachPlan();
        BeanUtils.copyProperties(query, bean);
        ValidateUtil.checkSpeakerAndAssistant(query);
        bean.setCreateTime(now);
        bean.setCreatorId(userId);
        bean.setCreatorName(userName);
        bean.setStatus(1);
        bean.setSemesterId(semesterId);
        bean.setSemester(semesterName);
        baseMapper.insert(bean);
        PreachPlanUpdateQuery updateQuery = new PreachPlanUpdateQuery();
        BeanUtils.copyProperties(query, updateQuery);
        updateQuery.setPreachPlanId(bean.getPreachPlanId());
        removeAndAddRelations(updateQuery);
        //taskService.checkPreachDate(bean);
        return Result.success();
    }


    @Override
    public IPage<PreachPlanVO> listPage(PreachPlanPageQuery query, Long semesterId) throws MyExcption {
        ValidateUtil.validateSemester(semesterId);
        IPage<PreachPlanVO> page = new Page<>(query.getPageNum(), query.getPageSize());
        List<PreachPlanVO> list = baseMapper.listPage(page, query, semesterId);
        page.setRecords(list);
        return page;

    }

    @Override
    public boolean updateStatus(UpdateStatusQuery query) {
        int result = baseMapper.updateStatus(query);
        return result > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean publish(UpdateStatusQuery query) {
        updateStatus(query);
        PreachPlan plan = baseMapper.selectById(query.getId());
        return taskService.checkPreachDate(plan);
    }


    @Override
    public PreachPlanVO detail(Long id) {
        PreachPlan bean = baseMapper.selectById(id);
        PreachPlanVO vo = new PreachPlanVO();
        BeanUtils.copyProperties(bean, vo);
        // vo.setPreachPlanId(bean.getPreachPlanId());
        vo.setSourceSchoolIds(preachSchoolRelationService.listByPreachPlanId(id));
        vo.setSourceSchools(preachSchoolRelationService.listKeyValueByPreachPlanId(id));
        vo.setAssistantIds(preachAssistantRelationService.listByPreachPlanId(id));
        vo.setAssistants(preachAssistantRelationService.listKeyValueByPreachPlanId(id));
        return vo;
    }

    @Override
    public IPage<PreachPlanVO> pageAllPreachPlanByChargerId(Long userId, Long semesterId, PreachPlanAllByUserIdPageQuery query) throws MyExcption {
        ValidateUtil.validateSemester(semesterId);
        IPage<PreachPlanVO> page = new Page<>(query.getPageNum(), query.getPageSize());
        List<PreachPlanVO> list = baseMapper.pageAllPreachPlanByUserId(page, userId, semesterId);
        setRelations(null, list);
        page.setRecords(list);
        return page;
    }


    @Override
    public IPage<PreachPlanVO> pagePreachPlanByChargerId(Long userId, Long semesterId, PreachPlanByUserIdPageQuery query) throws MyExcption {
        ValidateUtil.validateSemester(semesterId);
        IPage<PreachPlanVO> page = new Page<>(query.getPageNum(), query.getPageSize());
        List<PreachPlanVO> list = baseMapper.pagePreachPlanByUserId(page, userId, semesterId);
        setRelations(null, list);
        page.setRecords(list);
        return page;
    }

    @Override
    public IPage<PreachPlanVO> listPageByStudentPublicRelationId(Long studentPublicRelationId, BasePageQuery query) {
        IPage<PreachPlanVO> page = new Page<>(query.getPageNum(), query.getPageSize());
        List<PreachPlanVO> list = baseMapper.listPageByStudentPublicRelationId(page, studentPublicRelationId);
        setRelations(studentPublicRelationId, list);
        page.setRecords(list);
        return page;
    }


    private void setRelations(Long studentPublicRelationId, List<PreachPlanVO> list) {
        for (PreachPlanVO vo :
                list) {
            vo.setSourceSchoolIds(preachSchoolRelationService.listByPreachPlanId(vo.getPreachPlanId()));
            vo.setAssistantIds(preachAssistantRelationService.listByPreachPlanId(vo.getPreachPlanId()));
            vo.setPreachFeedback(preachFeedbackService.detailByStudentPublicRelationIdAndPreachPlanId(studentPublicRelationId, vo.getPreachPlanId()));
        }
    }
}
