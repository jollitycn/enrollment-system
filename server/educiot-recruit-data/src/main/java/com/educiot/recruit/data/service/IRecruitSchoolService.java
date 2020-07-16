package com.educiot.recruit.data.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.educiot.recruit.common.base.Result;
import com.educiot.recruit.data.entity.RecruitSchool;
import com.educiot.recruit.data.entity.query.AddRecruitSchoolQuery;
import com.educiot.recruit.data.entity.query.UpdateRecruitSchoolQuery;
import com.educiot.recruit.data.entity.vo.RecruitSchoolIdAndNameVO;
import com.educiot.recruit.data.entity.vo.RecruitSchoolVO;

import java.util.List;

/**
 * <p>
 * 招生学校 服务类
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
public interface IRecruitSchoolService extends IService<RecruitSchool> {

    /**
     * 添加招生学校信息
     *
     * @param addRecruitSchoolQuery 添加招生学校封装实体类
     * @return Result 返回添加是否成功状态
     */
    Result<RecruitSchool> addRecruiutSchool(AddRecruitSchoolQuery addRecruitSchoolQuery);

    /**
     * 获取招生学校信息
     *
     * @param recruitSchoolId 招生学校ID
     * @return RecruitSchoolVO 返回招生学校基本信息
     */
    Result<RecruitSchoolVO> queryRecruitSchoolInfo(String recruitSchoolId);


    /**
     * 获取招生学校信息
     *
     * @param recruitSchoolId 招生学校ID
     * @return RecruitSchoolVO 返回招生学校基本信息
     */
    RecruitSchoolVO getById(String recruitSchoolId);

    /**
     * 编辑招生学校信息
     *
     * @param updateRecruitSchoolQuery 编辑招生学校入参封装实体类
     * @return 返回编辑是否成功状态
     */
    Result<RecruitSchool> updateRecruitSchoolInfo(UpdateRecruitSchoolQuery updateRecruitSchoolQuery);

    /**
     * 删除招生学校信息
     *
     * @param recruitSchoolId 招生学校ID
     * @return 返回是否删除成功状态
     */
    Result deleteRecruitSchoolInfo(Long recruitSchoolId);

    /**
     * 根据ApiId查询是否存在学校信息
     *
     * @param apiId 平台接口Id
     * @return 返回招生学校基本信息
     */
    Result<RecruitSchool> queryByApiId(Long apiId);

    /**
     * 获取招生学校集合：recruitSchoolId为null则为查询所有
     *
     * @param recruitSchoolId 招生学校id
     * @return java.util.List&lt;com.educiot.recruit.data.entity.vo.RecruitSchoolIdAndNameVO&gt;
     * @author Pan Juncai
     * @date 2020/4/29 17:08
     */
    List<RecruitSchoolIdAndNameVO> listRecruitSchool(Long recruitSchoolId);

    List<RecruitSchoolIdAndNameVO> listRecruitSchoolWechat();

    /**
     * 【小程序】 根据选择的学生查询其下的招生学校
     *
     * @param studentInfo 学生id
     * @return java.util.List&lt;com.educiot.recruit.data.entity.vo.RecruitSchoolVO&gt;
     * @author Pan Juncai
     * @date 2020/5/8 16:53
     */
    List<RecruitSchoolVO> listRecruitSchoolByStudent(Long studentInfo);

    Result<RecruitSchool> queryByApiIdForlogin(Long schoolId);

    RecruitSchoolIdAndNameVO getRecruitSchoolIdAndNameVOById(Long recruitSchoolId);

}
