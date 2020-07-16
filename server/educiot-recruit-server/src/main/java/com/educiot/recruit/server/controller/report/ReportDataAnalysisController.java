package com.educiot.recruit.server.controller.report;

import com.educiot.recruit.data.entity.query.AdmissionsReportQuery;
import com.educiot.recruit.data.entity.query.RegionChargerQuery;
import com.educiot.recruit.data.entity.vo.*;
import com.educiot.recruit.data.service.report.IReportDataAnalysisService;
import com.educiot.recruit.common.base.Result;
import com.educiot.recruit.server.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @program: server
 * @description: 报表和数据分析 前端控制器
 * @author: Mr.AI
 * @create: 2020-04-26
 **/

@Api(tags = {"报表和数据分析"}, value = "ReportDataAnalysisController")
@RestController
@RequestMapping("/report")
public class ReportDataAnalysisController extends BaseController {

    @Resource
    private IReportDataAnalysisService reportDataAnalysisService;

    @GetMapping("/enrollmentProgress")
    @ApiOperation(value = "总体招生进度报表")
    public Result<ReportVO> enrollmentProgress() {
        ReportVO reportVO = reportDataAnalysisService.enrollmentProgress(getLoginUser().getCurrentSchoolId(),getLoginUser().getCurrentSemesterId(),null);
        return Result.success(reportVO);
    }

    @GetMapping("/regionalAdmissionsProgress")
    @ApiOperation(value = "区域招生进度")
    public Result<List<RegionalAdmissionsProgressVO>> regionalAdmissionsProgress() {
        List<RegionalAdmissionsProgressVO> regionalAdmissionsProgressVOS = reportDataAnalysisService.regionalAdmissionsProgress(getLoginUser().getCurrentSchoolId(),getLoginUser().getCurrentSemesterId());
        return Result.success(regionalAdmissionsProgressVOS);
    }

    @GetMapping("/facultyAdmissionsProgress")
    @ApiOperation(value = "院系招生进度")
    public Result<List<FacultyAdmissionsProgressVO>> facultyAdmissionsProgress() {
        List<FacultyAdmissionsProgressVO> facultyAdmissionsProgressVOS = reportDataAnalysisService.facultyAdmissionsProgress(getLoginUser().getCurrentSchoolId(),getLoginUser().getCurrentSemesterId());
        return Result.success(facultyAdmissionsProgressVOS);
    }

    @PostMapping("/admissionsReport")
    @ApiOperation(value = "招生报表")
    public Result<RegionAllVO> admissionsReport(@Valid @RequestBody RegionChargerQuery regionChargerQuery) {
        RegionAllVO regionAllVO = reportDataAnalysisService.admissionsReport(regionChargerQuery, getLoginUser().getCurrentSchoolId(),regionChargerQuery.getSemesterId());
        return Result.success(regionAllVO);
    }

    @GetMapping("/regionalEnrollmentProgressReport")
    @ApiOperation(value = "区域招生进度报表")
    public Result<ReportVO> regionalEnrollmentProgressReport(@Valid @RequestParam("semesterId") Long semesterId) {
        ReportVO reportVO = reportDataAnalysisService.regionalEnrollmentProgressReport(getLoginUser().getCurrentSchoolId(),semesterId);
        return Result.success(reportVO);
    }

    @GetMapping("/studentSourceReport")
    @ApiOperation(value = "生源报表")
    public Result<SourceSchoolReportAllVO> studentSourceReport(@Valid @RequestParam("semesterId") Long semesterId) {
        SourceSchoolReportAllVO sourceSchoolReportVOS = reportDataAnalysisService.studentSourceReport(getLoginUser().getCurrentSchoolId(),semesterId);
        return Result.success(sourceSchoolReportVOS);
    }

    @GetMapping("/channelReport")
    @ApiOperation(value = "渠道报表")
    public Result<ChannelReportAllVO> channelReport(@Valid @RequestParam("semesterId") Long semesterId) {
        ChannelReportAllVO channelReportAllVO = reportDataAnalysisService.channelReport(getLoginUser().getCurrentSchoolId(), semesterId);
        return Result.success(channelReportAllVO);
    }
}
