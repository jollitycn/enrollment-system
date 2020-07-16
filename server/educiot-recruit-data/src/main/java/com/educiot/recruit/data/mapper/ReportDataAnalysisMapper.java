package com.educiot.recruit.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.educiot.recruit.data.entity.query.RegionChargerQuery;
import com.educiot.recruit.data.entity.vo.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @program: server
 * @description: 后台报表和数据分析 Mapper 接口
 * @author: Mr.AI
 * @create: 2020-04-26
 **/
public interface ReportDataAnalysisMapper extends BaseMapper<ReportVO> {

    /**
     * 查询所有的已发布的计划
     * @return
     */
    List<PlanReportVO> queryAllPublishPlanAPP(@Param("recruitSchoolId")Long recruitSchoolId);

    /**
     * 总体招生进度报表展示
     * @param semesterId 学期Id
     * @return 总体招生进度数据
     */
    ReportVO enrollmentProgress(@Param("recruitSchoolId")Long recruitSchoolId,@Param("semesterId") Long semesterId,@Param("groupId")Long groupId);

    /**
     * 区域招生进度报表
     * @param semesterId 学期Id
     * @return 区域招生信息
     */
    List<RegionalAdmissionsProgressVO> regionalAdmissionsProgress(@Param("recruitSchoolId")Long recruitSchoolId,@Param("semesterId")Long semesterId);

    /**
     *
     * @param recruitSchoolId 招生学校ID
     * @param semesterId 学期ID
     * @param groupId 分组ID
     * @return 实际招生人数
     */
    Integer regionalAdmissionsProgressStudentCount(@Param("recruitSchoolId")Long recruitSchoolId,@Param("semesterId")Long semesterId,@Param("groupId")Long groupId);

    /**
     * 院系招生进度报表
     * @param semesterId 学期ID
     * @return 院系招生进度报表
     */
    List<FacultyAdmissionsProgressVO> queryCollegeNameAndAmount(@Param("recruitSchoolId")Long recruitSchoolId,@Param("semesterId")Long semesterId);

    /**
     * 院系招生进度报表
     * @param recruitSchoolId 招生学校ID
     * @param semesterId 学期ID
     * @param collegeId 院校ID
     * @return 院系招生进度报表
     */
    Integer queryFacultyAdmissionsProgress(@Param("recruitSchoolId")Long recruitSchoolId,@Param("semesterId")Long semesterId,@Param("collegeId")Long collegeId);

    /**
     * 区域招生进度报表
     * @param semesterId 学期ID
     * @return 区域招生进度报表信息
     */
    List<RegionalEnrollmentProgressReportVO> regionalEnrollmentProgressReport(@Param("recruitSchoolId")Long recruitSchoolId,@Param("semesterId")Long semesterId);

    /**
     *  数据分析中的生源报表
     * @param semesterId 学期ID
     * @return 生源报表信息
     */
    List<SourceSchoolReportVO> studentSourceReport(@Param("recruitSchoolId")Long recruitSchoolId,@Param("semesterId")Long semesterId);

    /**
     * APP招生负责人报表数据分析
     * @param semesterId
     * @return 负责人招生数据
     */
    List<PrincipalProgressVO> principalProgress(@Param("recruitSchoolId")Long recruitSchoolId,@Param("semesterId")Long semesterId,@Param("groupId")Long groupId);

    /**
     * APP生源学校报名人数统计分析
     * @param semesterId 学期Id
     * @return 生源学校报名数据
     */
    List<EnrollmentAnalysisVO> enrollmentAnalysis(@Param("recruitSchoolId")Long recruitSchoolId,@Param("semesterId")Long semesterId,@Param("groupId")Long groupId);

    /**
     * APP报名阶段人数统计分析
     * @param semesterId 学期ID
     * @return 报名阶段数据
     */
    List<RegistrationStageVO> registrationStage(@Param("recruitSchoolId")Long recruitSchoolId,@Param("semesterId")Long semesterId,@Param("groupId")Long groupId);

    /**
     *  各专业报名分析数据统计
     * @param recruitSchoolId 招生学校ID
     * @param semesterId 学期ID
     * @return 各专业招生人数数据
     */
    List<ProfessionalRegistrationVO> professionalRegistration(@Param("recruitSchoolId")Long recruitSchoolId,@Param("semesterId")Long semesterId,@Param("groupId")Long groupId);

    /**
     * 渠道报表数据统计分析
     * @param recruitSchoolId 招生学校ID
     * @param semesterId 学期Id
     * @return 渠道报表数据
     */
    List<ChannelReportVO> channelReport(@Param("recruitSchoolId")Long recruitSchoolId,@Param("semesterId")Long semesterId);

    /**
     * 查询招生组信息
     * @param recruitSchoolId 招生学校ID
     * @param semesterId 学期ID
     * @return 招生组信息
     */
    List<RegionVO> queryRegion(@Param("recruitSchoolId")Long recruitSchoolId,@Param("semesterId")Long semesterId);

    /**
     * 查询招生报表区域下的负责人信息
     * @param regionChargerQuery 时间段查询条件
     * @param groupId 招生组ID
     * @return 区域下的负责人信息
     */
    List<RegionChargerVO> queryRegionCharger(@Param("regionChargerQuery")RegionChargerQuery regionChargerQuery,@Param("groupId")Long groupId,@Param("semesterId")Long semesterId);
}
