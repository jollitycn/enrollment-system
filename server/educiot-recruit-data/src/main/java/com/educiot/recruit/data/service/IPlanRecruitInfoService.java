package com.educiot.recruit.data.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.educiot.common.base.Result;
import com.educiot.recruit.data.entity.PlanRecruitInfo;
import com.educiot.recruit.data.entity.query.AddPlanRecruitInfoQuery;
import com.educiot.recruit.data.entity.query.UpdatePlanRecruitInfoQuery;
import com.educiot.recruit.data.entity.vo.PlanRecruitInfoVO;

/**
 * <p>
 * 招生简介表 服务类
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
public interface IPlanRecruitInfoService extends IService<PlanRecruitInfo> {

    /**
     * 添加招生简介信息
     * @param addPlanRecruitInfoQuery 添加招生简介入参封装实体类
     * @return 添加是否成功状态
     */
    Result addPlanRecruitInfo(AddPlanRecruitInfoQuery addPlanRecruitInfoQuery);

    /**
     * 获取招生简介信息
     *
     * @param planId 计划ID
     * @return 放回招生简介信息
     */
    Result<PlanRecruitInfoVO> queryPlanRecruitInfo(Long planId);

    /**
     * 编辑招生简介信息
     * @param updatePlanRecruitInfoQuery 编辑招生简介入参封装实体类
     * @return 返回编辑是否成功状态
     */
    Result updatePlanRecruitInfo(UpdatePlanRecruitInfoQuery updatePlanRecruitInfoQuery);

    /**
     * 删除招生简介信息
     *
     * @param planRecruitInfoId 招生简介ID
     * @return 饭后是否删除成功状态
     */
    Result deletePlanRecruitInfo(Long planRecruitInfoId);

//    Result queryPlanSchoolInfo(Long planId);

//    PlanRecruitInfoVO queryPlanRecruitInfoByPlanId(Long planId);
}
