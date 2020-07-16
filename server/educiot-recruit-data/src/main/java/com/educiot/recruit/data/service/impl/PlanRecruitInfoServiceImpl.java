package com.educiot.recruit.data.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.educiot.common.base.CodeMsg;
import com.educiot.common.base.Result;
import com.educiot.common.constant.Constant;
import com.educiot.recruit.data.entity.PlanRecruitInfo;
import com.educiot.recruit.data.entity.query.AddPlanRecruitInfoQuery;
import com.educiot.recruit.data.entity.query.UpdatePlanRecruitInfoQuery;
import com.educiot.recruit.data.entity.vo.PlanRecruitInfoVO;
import com.educiot.recruit.data.mapper.PlanRecruitInfoMapper;
import com.educiot.recruit.data.service.IPlanRecruitInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * <p>
 * 招生简介表 服务实现类
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
@Service
public class PlanRecruitInfoServiceImpl extends ServiceImpl<PlanRecruitInfoMapper, PlanRecruitInfo> implements IPlanRecruitInfoService {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result addPlanRecruitInfo(AddPlanRecruitInfoQuery addPlanRecruitInfoQuery) {
        LocalDateTime now = LocalDateTime.now();
        PlanRecruitInfo planRecruitInfo = new PlanRecruitInfo();
        if(null==addPlanRecruitInfoQuery){
            return Result.error(CodeMsg.PARAMETER_ISNULL);
        }
        BeanUtils.copyProperties(addPlanRecruitInfoQuery, planRecruitInfo);
        planRecruitInfo.setCreateTime(now);
        planRecruitInfo.setIsDeleted(Constant.SYS_ONE);
        planRecruitInfo.setCreatorId(1L);
        planRecruitInfo.setCreatorName("");
        int i = baseMapper.insert(planRecruitInfo);
        if (i > 0) {
            return Result.success();
        }else{
            return Result.error(CodeMsg.DATA_INSERT_ERROR);
        }
    }

    @Override
    public Result<PlanRecruitInfoVO> queryPlanRecruitInfo(Long planId) {
        if (null == planId || "".equals(planId)) {
            return Result.error(CodeMsg.PARAMETER_ISNULL);
        }
        QueryWrapper<PlanRecruitInfo> planRecruitInfoWrapper = new QueryWrapper<>();
        planRecruitInfoWrapper.eq("plan_id", planId);
        planRecruitInfoWrapper.eq("is_deleted", Constant.SYS_ONE);
        PlanRecruitInfo planRecruitInfo = baseMapper.selectOne(planRecruitInfoWrapper);
        if (null == planRecruitInfo) {
            return Result.error(CodeMsg.PLAN_RECRUIT_INFO_EXIST);
        }
        PlanRecruitInfoVO planRecruitInfoVO=new PlanRecruitInfoVO();
        BeanUtils.copyProperties(planRecruitInfo,planRecruitInfoVO);
        return Result.success(planRecruitInfoVO);
    }



    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result updatePlanRecruitInfo(UpdatePlanRecruitInfoQuery updatePlanRecruitInfoQuery) {
        if(null==updatePlanRecruitInfoQuery){
            return Result.error(CodeMsg.PARAMETER_ISNULL);
        }
        if(null==updatePlanRecruitInfoQuery.getPlanRecruitInfoId() || "".equals(updatePlanRecruitInfoQuery.getPlanRecruitInfoId())){
            return Result.error(CodeMsg.PARAMETER_ISNULL);
        }
        PlanRecruitInfo planRecruitInfo = baseMapper.selectById(updatePlanRecruitInfoQuery.getPlanRecruitInfoId());
        if(null==planRecruitInfo){
            return Result.error(CodeMsg.PLAN_RECRUIT_INFO_EXIST);
        }
        PlanRecruitInfo updatePlanRecruitInfo=new PlanRecruitInfo();
        BeanUtils.copyProperties(updatePlanRecruitInfoQuery,updatePlanRecruitInfo);
        int i = baseMapper.updateById(updatePlanRecruitInfo);
        if (i > 0) {
            return Result.success();
        }else{
            return Result.error(CodeMsg.DATA_UPDATE_ERROR);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result deletePlanRecruitInfo(Long planRecruitInfoId) {
        PlanRecruitInfo planRecruitInfo = baseMapper.selectById(planRecruitInfoId);
        if(null==planRecruitInfo){
            return Result.error(CodeMsg.PLAN_RECRUIT_INFO_EXIST);
        }
        planRecruitInfo.setIsDeleted(Constant.SYS_ZERO);
        int i = baseMapper.updateById(planRecruitInfo);
        if (i > 0) {
            return Result.success();
        } else {
            return Result.error(CodeMsg.DATA_UPDATE_ERROR);
        }
    }

//    @Override
//    public PlanRecruitInfoVO queryPlanRecruitInfoByPlanId(Long planId) {
//        return baseMapper.queryPlanRecruitInfoByPlanId(planId);
//    }
}
