package com.educiot.recruit.data.service.student;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.educiot.common.base.Result;
import com.educiot.recruit.data.entity.Plan;
import com.educiot.recruit.data.entity.StudentInfo;
import com.educiot.recruit.data.entity.query.*;
import com.educiot.recruit.data.entity.vo.ListSourceSchoolVO;
import com.educiot.recruit.data.entity.vo.MiniProgramLoginVO;
import com.educiot.recruit.data.entity.vo.StudentInfoStatusVO;
import com.educiot.recruit.data.entity.vo.StudentInfoVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * <p>
 * 学生基本信息表 服务类
 * </p>
 *
 * @author Mr.AI
 * @since 2020-04-13
 */

@Service
public interface IStudentInfoService extends IService<StudentInfo> {

    /**
     * 添加学生基本信息
     *
     * @param addStudentInfoQuery 添加学生基本信息入参封装类
     * @return 返回添加是否成功状态
     */
    Result addStudentInfo(AddStudentInfoQuery addStudentInfoQuery)  throws Exception;


    /**
     * 编辑学生基本信息
     *
     * @param updateStudentInfoQuery 编辑学生基本信息入参封装类
     * @return 返回编辑是否成功状态
     */
    Result updateStudentInfo(UpdateStudentInfoQuery updateStudentInfoQuery);

    /**
     * 编辑APP学生基本信息
     *
     * @param updateStudentInfoQuery 编辑学生基本信息入参封装类
     * @return 返回编辑是否成功状态
     */
    Result updateStudentInfoAPP(UpdateStudentInfoQuery updateStudentInfoQuery)  throws Exception;

    /**
     * 获取学生基本信息
     *
     * @param studentInfoId 学生基本信息ID
     * @return 返回学生基本信息
     */
    StudentInfo queryStudentInfo(Long studentInfoId);

    /**
     * 获取学生基本信息APP
     *
     * @param studentInfoId 学生基本信息ID
     * @return 返回学生基本信息
     */
    StudentInfoVO queryStudentInfoAPP(Long studentInfoId,Long schoolId);

    /**
     * 查询所有学生基本信息列表
     *
     * @param param 分页查询所有学生信息列表入参封装类
     * @return 学生基本信息列表
     */
    IPage<StudentInfoVO> listStudentInfoPage(ListStudentPageQuery param);

    /**
     * 查询所有学生基本信息列表
     *
     * @param param 分页查询所有学生信息列表入参封装类
     * @return 学生基本信息列表
     */
    IPage<StudentInfoVO> listStudentInfoPageForFamilyMember(ListStudentPageQuery param,Long chargerId);

    /**
     * 查询学生数量
     * @param param 查询条件
     * @return
     */
    int listStudentCount(@Param("param") ListStudentPageQuery param);

    /**
     * 查询学生基本信息填写完成状态
     *
     * @param studentInfoId 学生基本信息ID
     * @return
     */
    Boolean queryStudentInfoStatu(Long studentInfoId);

    /**
     * 查询学生联系方式填写完成状态
     *
     * @param studentInfoId 学生基本信息ID
     * @return
     */
    Boolean queryContactDetailStatu(Long studentInfoId);

    /**
     * 查询学生所有必填项的填写完成状态
     * @param studentInfoId 学生ID
     * @return
     */
    Boolean queryRequiredFieldStatu(Long studentInfoId);

    /**
     * 查询app端负责人下的所有生源学校
     *
     * @return 生源学校列表信息
     */
    List<ListSourceSchoolVO> listSourceSchool(Long chargerId);

    /**
     * 查询当前招生学校和当前学期默认招生计划
     *
     * @param recruitSchoolId 招生学校ID
     * @return 默认计划信息
     */
    Plan queryDefaultPlanWeChat(Long recruitSchoolId);

    /**
     * 添加学生基本信息和家庭信息
     *
     * @param addStudentInfoQuery 入参实体
     * @return 保存是否成功状态
     */
    Result addStudentInfoAPP(AddStudentInfoQuery addStudentInfoQuery) throws Exception;

    /**
     * 【小程序】切换学生
     *
     * @param studentInfo 切换的学生
     * @param loginVO     登录用户
     * @return com.educiot.common.api.base.Result
     * @author Pan Juncai
     * @date 2020/5/8 15:45
     */
    Result switchStudent(Long studentInfo, MiniProgramLoginVO loginVO);

    /**
     * 【小程序】切换招生学校
     *
     * @param recruitSchoolId 切换的招生学校
     * @param loginVO         登录用户
     * @return com.educiot.common.api.base.Result
     * @author Pan Juncai
     * @date 2020/5/8 15:45
     */
    Result switchRecruitSchool(Long recruitSchoolId, MiniProgramLoginVO loginVO);

    /**
     * 获取学生基本信息填写状态
     * @param studentInfoId 学生基本信息ID
     * @param schoolId 招生学校ID
     * @return 填写状态
     */
    StudentInfoStatusVO queryStudentInfoStatus(Long studentInfoId,Long schoolId);

    /**
     * 判断学生是否重复
     * @param studentQuery
     * @return
     */
    String queryStudentCount(StudentQuery studentQuery);

    IPage<StudentInfoVO> listStudentInfoPageByClassId(ListStudentPageByClassIdQuery param,Long semesterId);
}
