package com.educiot.recruit.data.service;

import com.educiot.common.api.MajorListResult;
import com.educiot.recruit.data.entity.Major;
import com.baomidou.mybatisplus.extension.service.IService;
import com.educiot.recruit.data.entity.query.AddMajorInfoQuery;
import com.educiot.recruit.data.entity.query.UpdateMajorInfoQuery;
import com.educiot.common.base.Result;
import com.educiot.recruit.data.entity.vo.MajorAmountVO;
import com.educiot.recruit.data.entity.vo.MajorVO;

import java.util.List;

/**
 * <p>
 * 招生专业表 服务类
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
public interface IMajorService extends IService<Major> {

    /**
     * 添加招生专业信息
     * @param addMajorInfoQuery 添加专业信息封装实体类
     * @return 返回是否添加成功装填
     */
    Result addMajorInfo(AddMajorInfoQuery addMajorInfoQuery,Long userId,String userName);

    /**
     * 获取院系下面的所有招生专业信息
     * @param collegeId 院系ID
     * @return 返回院系下所有的招生专业信息
     */
    Result<List<Major>> queryMajorInfoByCollegeId(String collegeId);

    /**
     * 查看招生专业信息
     * @param majorId 专业ID
     * @return 返回对应的专业信息
     */
    Result<MajorVO> queryMajorInfoByMajorId(String majorId,Long recruitSchoolId,Long semesterId);

    /**
     * 编辑招生专业信息
     * @param updateMajorInfoQuery 编辑招生专业封装实体类
     * @return 返回编辑是否成功状态
     */
    Result updateMajorInfo(UpdateMajorInfoQuery updateMajorInfoQuery);

    /**
     * 删除专业信息
     * @param majorId 专业ID
     * @return 返回是否删除成功状态
     */
    Result deleteMajorInfo(Long majorId);

    /**
     * 查询平台专业信息列表（包含目标人数）
     * @param majorListResult 平台专业信息列表
     * @return 专业信息列表
     */
    List<MajorAmountVO> queryMajorInfoByAPI(MajorListResult majorListResult, Long recruitSchoolId,Long currentSemesterId );
}
