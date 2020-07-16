package com.educiot.recruit.data.service;

import com.educiot.recruit.data.entity.PlanSchoolIntroduction;
import com.baomidou.mybatisplus.extension.service.IService;
import com.educiot.recruit.data.entity.ext.PlanSchoolIntroductionExt;
import com.educiot.recruit.data.entity.query.AddPlanSchoolIntroductionQuery;
import com.educiot.recruit.data.entity.query.UpdatePlanSchoolIntroductionQuery;
import com.educiot.recruit.common.base.Result;

import java.util.List;

/**
 * <p>
 * 招生计划学校简介 服务类
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
public interface IPlanSchoolIntroductionService extends IService<PlanSchoolIntroduction> {

    /**
     * 添加招生计划学校信息
     * @param addPlanSchoolIntroductionQuery 添加招生计划学校入参封装类
     * @return 返回添加是否成功状态
     */
    Result addPlanSchoolIntroduction(AddPlanSchoolIntroductionQuery addPlanSchoolIntroductionQuery);

    /**
     *  获取招生计划学校信息
     * @param planSchoolIntroductionId 招生计划学校ID
     * @return 返回招生计划学校信息
     */
    Result<PlanSchoolIntroduction> queryPlanSchoolIntroduction(String planSchoolIntroductionId);

    /**
     * 修改招生计划学校信息
     * @param updatePlanSchoolIntroductionQuery 编辑计划招生学校入参封装类
     * @return 返回编辑是否成功状态
     */
    Result updatePlanSchoolIntroduction(UpdatePlanSchoolIntroductionQuery updatePlanSchoolIntroductionQuery);

    /**
     * 删除招生计划学校信息
     * @param planSchoolIntroductionId 计划招生学校ID
     * @return 返回删除计划招生学校是否成功状态
     */
    Result deletePlanSchoolIntroduction(Long planSchoolIntroductionId);


    List listRecruitSchoolByStudent(Long studentInfo);

    PlanSchoolIntroductionExt getByRecruitSchoolId(String recruitSchoolId);
}
