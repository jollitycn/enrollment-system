package com.educiot.recruit.server.app.controller.report;

import com.educiot.recruit.common.base.Result;
import com.educiot.recruit.data.entity.query.PlanReportQuery;
import com.educiot.recruit.data.entity.vo.*;
import com.educiot.recruit.data.service.report.IReportDataAnalysisService;
import com.educiot.recruit.server.app.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @program: server
 * @description: APP首页报表和数据分析 前端控制器
 * @author: Mr.AI
 * @create: 2020-04-26
 **/

@Api(tags = {"APP首页报表和数据分析"}, value = "ReportDataAnalysisAppController")
@RestController
@RequestMapping("/report")
public class ReportDataAnalysisAppController extends BaseController {

    @Resource
    private IReportDataAnalysisService reportDataAnalysisService;

    @GetMapping("/enrollmentProgress")
    @ApiOperation(value = "总体招生进度报表")
    public Result<ReportVO> enrollmentProgress(PlanReportQuery planReportQuery) {
        ReportVO reportVO = reportDataAnalysisService.enrollmentProgress(getLoginUser().getCurrentSchoolId(), planReportQuery.getSemesterId(), getLoginUser().getCharger().getGroupId());
        return Result.success(reportVO);
    }

    @GetMapping("/principalProgress")
    @ApiOperation(value = "负责人招人进度")
    public Result<List<PrincipalProgressVO>> principalProgress() {
        List<PrincipalProgressVO> principalProgressVOS = reportDataAnalysisService.principalProgress(getLoginUser().getCurrentSchoolId(), getLoginUser().getCurrentSemesterId(), getLoginUser().getCharger().getGroupId());
        return Result.success(principalProgressVOS);
    }

    @GetMapping("/enrollmentAnalysis")
    @ApiOperation(value = "报名生源学校分析")
    public Result<List<EnrollmentAnalysisVO>> enrollmentAnalysis(PlanReportQuery planReportQuery) {
        List<EnrollmentAnalysisVO> enrollmentAnalysisVOS = reportDataAnalysisService.enrollmentAnalysis(getLoginUser().getCurrentSchoolId(), planReportQuery.getSemesterId(), getLoginUser().getCharger().getGroupId());
        return Result.success(enrollmentAnalysisVOS);
    }

    @GetMapping("/registrationStage")
    @ApiOperation(value = "报名阶段分析")
    public Result<List<RegistrationStageVO>> registrationStage(PlanReportQuery planReportQuery) {
        List<RegistrationStageVO> registrationStageVOS = reportDataAnalysisService.registrationStage(getLoginUser().getCurrentSchoolId(), planReportQuery.getSemesterId(), getLoginUser().getCharger().getGroupId());
        return Result.success(registrationStageVOS);
    }

    @GetMapping("/professionalRegistration")
    @ApiOperation(value = "各专业报名分析")
    public Result<List<ProfessionalRegistrationVO>> professionalRegistration(PlanReportQuery planReportQuery) {
        List<ProfessionalRegistrationVO> professionalRegistrationVOS = reportDataAnalysisService.professionalRegistration(getLoginUser().getCurrentSchoolId(),planReportQuery.getSemesterId(), getLoginUser().getCharger().getGroupId());
        return Result.success(professionalRegistrationVOS);
    }

    @GetMapping("/queryAllPublishPlanAPP")
    @ApiOperation("获取所有已发布计划信息")
    public Result queryAllPublishPlanAPP(){
        List<PlanReportVO> planReportVOS = reportDataAnalysisService.queryAllPublishPlanAPP(getLoginUser().getCurrentSchoolId());
        return Result.success(planReportVOS);
    }

}
