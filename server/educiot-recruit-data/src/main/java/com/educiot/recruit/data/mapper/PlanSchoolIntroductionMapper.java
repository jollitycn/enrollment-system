package com.educiot.recruit.data.mapper;

import com.educiot.recruit.common.base.Result;
import com.educiot.recruit.data.entity.PlanSchoolIntroduction;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.educiot.recruit.data.entity.ext.PlanSchoolIntroductionExt;

import java.util.List;

/**
 * <p>
 * 招生计划学校简介 Mapper 接口
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
public interface PlanSchoolIntroductionMapper extends BaseMapper<PlanSchoolIntroduction> {

    List listRecruitSchoolByStudent(Long studentInfo);

    PlanSchoolIntroductionExt getByRecruitSchoolId(String recruitSchoolId);
}
