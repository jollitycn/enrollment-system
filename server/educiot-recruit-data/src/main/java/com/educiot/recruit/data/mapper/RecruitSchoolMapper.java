package com.educiot.recruit.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.educiot.recruit.data.entity.RecruitSchool;
import com.educiot.recruit.data.entity.vo.RecruitSchoolIdAndNameVO;
import com.educiot.recruit.data.entity.vo.RecruitSchoolVO;

import java.util.List;

/**
 * <p>
 * 招生学校 Mapper 接口
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
public interface RecruitSchoolMapper extends BaseMapper<RecruitSchool> {

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

    RecruitSchool queryByApiIdForlogin(Long apiId);

    RecruitSchoolIdAndNameVO getRecruitSchoolIdAndNameVOById(Long recruitSchoolId);

}
