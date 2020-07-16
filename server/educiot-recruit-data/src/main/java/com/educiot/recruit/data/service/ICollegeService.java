package com.educiot.recruit.data.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.educiot.recruit.common.api.DepartInfo;
import com.educiot.recruit.common.base.Result;
import com.educiot.recruit.data.entity.College;
import com.educiot.recruit.data.entity.query.AddCollegeQuery;
import com.educiot.recruit.data.entity.query.UpdateCollegeQuery;
import com.educiot.recruit.data.entity.vo.CollegeAndMajorVO;
import com.educiot.recruit.data.entity.vo.CollegeVO;

import java.util.List;

/**
 * <p>
 * 招生院系表 服务类
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
public interface ICollegeService extends IService<College> {


    /**
     * 获取招生院系列表信息
     * @param collegeId 院系ID
     * @return 返回院系列表信息
     */
    Result<List<CollegeVO>> queryCollegeInfo(String collegeId);

    /**
     * 获取招生院系信息
     * @param collegeId 院系ID
     * @return 返回院系基本信息
     */
    Result<College> queryCollegeInfoById(String collegeId);

    /**
     * 编辑招生院系信息
     * @param updateCollegeQuery 编辑院系封装入参类
     * @return 返回编程是否成功状态
     */
    Result updateCollegeInfo(UpdateCollegeQuery updateCollegeQuery);

    /**
     * 添加招生院系信息
     *
     * @param addCollegeQuery 添加院系基本信息封装类  termCollegeVOS 平台学期接口返回的学期数据列表
     * @return 添加是否成功状态
     */
    Result addCollegeInfo(AddCollegeQuery addCollegeQuery, List<DepartInfo> termCollegeVOS);

    /**
     * 获取招生院系信息以及下面所有的专业信息
     * @param planId 计划ID
     * @return 返回计划下所有的院系，以及院系下所有的专业信息列表
     */
    Result<List<CollegeAndMajorVO>> queryCollegeAndMajor(Long planId,Long recruitSchoolId,Long semesterId);

    /**
     * 根据专业ID查询已报名人数
     * @param majorId 专业ID
     * @param recruitSchoolId 招生学校ID
     * @param semesterId 学期ID
     * @return 报名人数
     */
    int queryMajorAmount(Long majorId,Long recruitSchoolId,Long semesterId);

}
