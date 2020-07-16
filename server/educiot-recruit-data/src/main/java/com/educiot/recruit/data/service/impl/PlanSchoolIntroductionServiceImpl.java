package com.educiot.recruit.data.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.educiot.recruit.data.entity.PlanSchoolIntroduction;
import com.educiot.recruit.data.entity.ext.PlanSchoolIntroductionExt;
import com.educiot.recruit.data.entity.query.AddPlanSchoolIntroductionQuery;
import com.educiot.recruit.data.entity.query.UpdatePlanSchoolIntroductionQuery;
import com.educiot.recruit.data.mapper.PlanSchoolIntroductionMapper;
import com.educiot.recruit.data.service.IPlanSchoolIntroductionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.educiot.recruit.common.base.CodeMsg;
import com.educiot.recruit.common.base.Result;
import com.educiot.recruit.common.constant.Constant;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 招生计划学校简介 服务实现类
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
@Service
public class PlanSchoolIntroductionServiceImpl extends ServiceImpl<PlanSchoolIntroductionMapper, PlanSchoolIntroduction> implements IPlanSchoolIntroductionService {

    @Override
    public Result addPlanSchoolIntroduction(AddPlanSchoolIntroductionQuery addPlanSchoolIntroductionQuery) {
        LocalDateTime now = LocalDateTime.now();
        PlanSchoolIntroduction planSchoolIntroduction = new PlanSchoolIntroduction();
        if (null == addPlanSchoolIntroductionQuery) {
            return Result.error(CodeMsg.PARAMETER_ISNULL);
        }
        BeanUtils.copyProperties(addPlanSchoolIntroductionQuery, planSchoolIntroduction);
        planSchoolIntroduction.setCreateTime(now);
        planSchoolIntroduction.setIsDeleted(Constant.SYS_ONE);
        int i = baseMapper.insert(planSchoolIntroduction);
        if (i > 0) {
            return Result.success();
        } else {
            return Result.error(CodeMsg.DATA_INSERT_ERROR);
        }
    }

    @Override
    public Result<PlanSchoolIntroduction> queryPlanSchoolIntroduction(String planId) {
        if (null == planId) {
            return Result.error(CodeMsg.PARAMETER_ISNULL);
        }
        QueryWrapper<PlanSchoolIntroduction> recruitSchoolWrapper = new QueryWrapper<>();
        recruitSchoolWrapper.eq("plan_id", planId);
        recruitSchoolWrapper.eq("is_deleted", Constant.SYS_ONE);
        PlanSchoolIntroduction planSchoolIntroduction = baseMapper.selectOne(recruitSchoolWrapper);
        if (null == planSchoolIntroduction) {
            return Result.error(CodeMsg.RECRUIT_SCHOOL_NOT_EXIST);
        }
        return Result.success(planSchoolIntroduction);
    }

    @Override
    public Result updatePlanSchoolIntroduction(UpdatePlanSchoolIntroductionQuery updatePlanSchoolIntroductionQuery) {
        PlanSchoolIntroduction planSchoolIntroduction = baseMapper.selectById(updatePlanSchoolIntroductionQuery.getPlanSchoolIntroductionId());
        if (null == planSchoolIntroduction) {
            return Result.error(CodeMsg.RECRUIT_SCHOOL_NOT_EXIST);
        }
        if (planSchoolIntroduction.getIsDeleted() == Constant.SYS_ZERO) {
            return Result.error(CodeMsg.RECRUIT_SCHOOL_NOT_EXIST);
        }
        PlanSchoolIntroduction updatePlanSchoolIntroduction = new PlanSchoolIntroduction();
        BeanUtils.copyProperties(updatePlanSchoolIntroductionQuery, updatePlanSchoolIntroduction);
        int i = baseMapper.updateById(updatePlanSchoolIntroduction);
        if (i > 0) {
            return Result.success();
        } else {
            return Result.error(CodeMsg.DATA_UPDATE_ERROR);
        }
    }

    @Override
    public Result deletePlanSchoolIntroduction(Long planSchoolIntroductionId) {
        PlanSchoolIntroduction planSchoolIntroduction = baseMapper.selectById(planSchoolIntroductionId);
        if (null == planSchoolIntroduction) {
            return Result.error(CodeMsg.RECRUIT_SCHOOL_NOT_EXIST);
        }
        if (planSchoolIntroduction.getIsDeleted() == Constant.SYS_ZERO) {
            return Result.error(CodeMsg.RECRUIT_SCHOOL_NOT_EXIST);
        }
        planSchoolIntroduction.setIsDeleted(Constant.SYS_ZERO);
        int i = baseMapper.updateById(planSchoolIntroduction);
        if (i > 0) {
            return Result.success();
        } else {
            return Result.error(CodeMsg.DATA_DELETE_ERROR);
        }
    }

    @Override
    public List listRecruitSchoolByStudent(Long studentInfo) {
        if (null == studentInfo) {
            return Lists.newArrayList();
        }
        return baseMapper.listRecruitSchoolByStudent(studentInfo);
    }

    @Override
    public PlanSchoolIntroductionExt getByRecruitSchoolId(String recruitSchoolId) {
        return baseMapper.getByRecruitSchoolId(recruitSchoolId);
    }


}
