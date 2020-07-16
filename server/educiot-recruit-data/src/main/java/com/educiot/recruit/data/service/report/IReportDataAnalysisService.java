package com.educiot.recruit.data.service.report;

import com.baomidou.mybatisplus.extension.service.IService;
import com.educiot.recruit.data.entity.query.RegionChargerQuery;
import com.educiot.recruit.data.entity.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @program: server
 * @description: 后台报表和数据分析 服务类
 * @author: Mr.AI
 * @create: 2020-04-26
 **/
public interface IReportDataAnalysisService extends IService<ReportVO> {


    /**
     * 总体招生进度报表展示
     * @param semesterId 学期Id
     * @return 总体招生进度数据
     */
    ReportVO enrollmentProgress(Long recruitSchoolId,Long semesterId,Long groupId);

    /**
     * 区域招生进度报表
     * @param semesterId 学期Id
     * @return 区域招生信息
     */
    List<RegionalAdmissionsProgressVO> regionalAdmissionsProgress(Long recruitSchoolId,Long semesterId);

    /**
     * 院系招生进度报表
     * @param semesterId 学期ID
     * @return 院系招生进度信息
     */
    List<FacultyAdmissionsProgressVO> facultyAdmissionsProgress(Long recruitSchoolId,Long semesterId);

    /**
     * 区域招生进度报表
     * @param semesterId 学期ID
     * @return 区域招生报表信息
     */
    ReportVO regionalEnrollmentProgressReport(Long recruitSchoolId,Long semesterId);

    /**
     *  数据分析中的生源报表
     * @param semesterId 学期ID
     * @return 生源报表信息
     */
    SourceSchoolReportAllVO studentSourceReport(Long recruitSchoolId,Long semesterId);

    /**
     * APP招生负责人报表数据分析
     * @param semesterId
     * @return 负责人招生数据
     */
    List<PrincipalProgressVO> principalProgress(Long recruitSchoolId,Long semesterId,Long groupId);

    /**
     * APP生源学校招生数据分析
     * @param semesterId 学期ID
     * @return 生源学校招生数据
     */
    List<EnrollmentAnalysisVO> enrollmentAnalysis(Long recruitSchoolId,Long semesterId,Long groupId);

    /**
     * APP报名阶段人数统计分析
     * @param semesterId 学期ID
     * @return 报名阶段数据
     */
    List<RegistrationStageVO> registrationStage(Long recruitSchoolId,Long semesterId,Long groupId);

    /**
     *  各专业报名分析数据统计
     * @param recruitSchoolId 招生学校ID
     * @param semesterId 学期ID
     * @return 各专业招生人数数据
     */
    List<ProfessionalRegistrationVO> professionalRegistration(Long recruitSchoolId,Long semesterId,Long groupId);

    /**
     * 渠道报表数据统计分析
     * @param recruitSchoolId 招生学校ID
     * @param semesterId 学期Id
     * @return 渠道报表数据
     */
    ChannelReportAllVO channelReport(Long recruitSchoolId,Long semesterId);

    /**
     * 招生报表数据分析统计
     * @param regionChargerQuery 时间段查询条件
     * @return 招生报表信息
     */
    RegionAllVO admissionsReport(RegionChargerQuery regionChargerQuery,Long recruitSchoolId,Long semesterId);

    /**
     * 查询所有的已发布的计划
     * @return
     */
    List<PlanReportVO> queryAllPublishPlanAPP(Long recruitSchoolId);

}
