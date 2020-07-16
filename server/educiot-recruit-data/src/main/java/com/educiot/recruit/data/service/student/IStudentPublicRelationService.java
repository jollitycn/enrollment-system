package com.educiot.recruit.data.service.student;

import com.baomidou.mybatisplus.extension.service.IService;
import com.educiot.recruit.common.base.Result;
import com.educiot.recruit.data.entity.StudentPublicRelation;
import com.educiot.recruit.data.entity.query.UpdateStudentPublicRelationStatusQuery;
import com.educiot.recruit.data.entity.vo.StudentPublicRelationRecordVO;
import com.educiot.recruit.data.entity.vo.StudentPublicRelationVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 意向生登录表 服务类
 * </p>
 *
 * @author Mr.AI
 * @since 2020-04-15
 */

@Service
public interface IStudentPublicRelationService extends IService<StudentPublicRelation> {

    /**
     * 添加或编辑学生公关信息
     *
     * @param studentPublicRelation 添加或编辑学生公关信息入参封装实体
     * @return 添加或编辑学生公关信息是否成功装填
     */
    Result saveOrUpdateStudentPublicRelation(StudentPublicRelation studentPublicRelation);

    /**
     *  根据招生学校查询学生登记号，最大值加1
     * @param recruitSchoolId 招生学校ID
     * @return 登记号
     */
    Integer queryStudentNo(Long recruitSchoolId,Long semesterId);

    /**
     * 获取学生公关信息
     *
     * @param studentInfoId 学生基本信息ID
     * @return 返回学生公关信息列表
     */
    StudentPublicRelationRecordVO queryStudentPublicRelation(Long studentInfoId,Long recruitSchoolId);

    /**
     * 修改公关信息中的报名状态
     *
     * @param studentPublicRelation 修改学生公关信息入参封装实体
     * @return 返回修改是否成功状态
     */
    Result updateStudentPublicRelationById(UpdateStudentPublicRelationStatusQuery studentPublicRelation);

//    /**
//     * 获取学生公关信息
//     *
//     * @param studentPublicRelationId 学生公关信息ID
//     * @return 返回学生公关信息
//     */
//    StudentPublicRelation queryStudentPublicRelationById(Long studentPublicRelationId);

    /**
     * 根据学生基本信息Id查询公关信息中的招生组下拉列表
     *
     * @param studentInfo 学生ID
     * @return 招生组信息
     */
    List<StudentPublicRelationVO> queryPrincipal(Long studentInfo, Long recruitSchoolId);

    Long getIdByRecruitSchoolId(Long studentInfo, Long recruitSchoolId);
    Long getRecruitSchoolIdByStudentInfo(Long studentInfo, Long recruitSchoolId);

    StudentPublicRelationVO getStudentPublicRelation(Long studentInfo, Long recruitSchoolId);
}
