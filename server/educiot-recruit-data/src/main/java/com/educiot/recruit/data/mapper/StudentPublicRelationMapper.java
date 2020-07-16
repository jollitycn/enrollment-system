package com.educiot.recruit.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.educiot.recruit.data.entity.StudentPublicRelation;
import com.educiot.recruit.data.entity.vo.StudentPublicRelationRecordVO;
import com.educiot.recruit.data.entity.vo.StudentPublicRelationVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 意向生登录表 Mapper 接口
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-15
 */
public interface StudentPublicRelationMapper extends BaseMapper<StudentPublicRelation> {


    /**
     * 根据学生基本信息Id查询公关信息中的招生组下拉列表
     * @param studentInfo 学生ID
     * @return 招生组信息
     */
    List<StudentPublicRelationVO> queryPrincipal(@Param("studentInfo")Long studentInfo,@Param("recruitSchoolId")Long recruitSchoolId);

    /**
     * 根据招生学校查询学生登记号，最大值加1
     *
     * @param recruitSchoolId 招生学校ID
     * @return 登记号
     */
    Integer queryStudentNo(@Param("recruitSchoolId") Long recruitSchoolId,@Param("semesterId") Long semesterId);

    /**
     * 查询学生公关信息
     *
     * @param studentInfo 学生信息ID
     * @return 学生公关信息
     */
    StudentPublicRelationRecordVO queryStudentPublicRelation(@Param("studentInfo") Long studentInfo, @Param("recruitSchoolId") Long recruitSchoolId);

    Long getIdByRecruitSchoolId(Long studentInfo, Long recruitSchoolId);

    Long getRecruitSchoolIdByStudentInfo(Long studentInfo, Long recruitSchoolId);

    StudentPublicRelationVO getStudentPublicRelation(@Param("studentInfo")Long studentInfo, @Param("recruitSchoolId")Long recruitSchoolId);
}
