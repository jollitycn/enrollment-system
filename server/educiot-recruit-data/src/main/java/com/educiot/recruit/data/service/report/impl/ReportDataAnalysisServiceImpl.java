package com.educiot.recruit.data.service.report.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.educiot.recruit.data.entity.query.RegionChargerQuery;
import com.educiot.recruit.data.entity.vo.*;
import com.educiot.recruit.data.mapper.ReportDataAnalysisMapper;
import com.educiot.recruit.data.service.report.IReportDataAnalysisService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @program: server
 * @description: 后台报表和数据分析 服务实现类
 * @author: Mr.AI
 * @create: 2020-04-26
 **/
@Service
public class ReportDataAnalysisServiceImpl extends ServiceImpl<ReportDataAnalysisMapper, ReportVO> implements IReportDataAnalysisService {

    @Override
    public ReportVO enrollmentProgress(Long recruitSchoolId, Long semesterId, Long groupId) {
        ReportVO reportVO = baseMapper.enrollmentProgress(recruitSchoolId, semesterId, groupId);
        if(null == reportVO){
            return null;
        }
        if(null ==  reportVO.getNumberCompletions()){
            reportVO.setNumberCompletions(0);
        }
        return reportVO;
    }

    @Override
    public List<RegionalAdmissionsProgressVO> regionalAdmissionsProgress(Long recruitSchoolId, Long semesterId) {
        List<RegionalAdmissionsProgressVO> regionalAdmissionsProgressVOS = baseMapper.regionalAdmissionsProgress(recruitSchoolId, semesterId);
        regionalAdmissionsProgressVOS.forEach(RegionalAdmissionsProgressVO -> {
            Integer integer = baseMapper.regionalAdmissionsProgressStudentCount(recruitSchoolId, semesterId, RegionalAdmissionsProgressVO.getGroupId());
            if (integer == null) {
                RegionalAdmissionsProgressVO.setActualNumber(0);
            } else {
                RegionalAdmissionsProgressVO.setActualNumber(integer);
            }
        });
        return regionalAdmissionsProgressVOS;
    }


    @Override
    public List<FacultyAdmissionsProgressVO> facultyAdmissionsProgress(Long recruitSchoolId, Long semesterId) {
        List<FacultyAdmissionsProgressVO> facultyAdmissionsProgressVOS = baseMapper.queryCollegeNameAndAmount(recruitSchoolId, semesterId);
        facultyAdmissionsProgressVOS.forEach(FacultyAdmissionsProgressVO -> {
            Integer integer = baseMapper.queryFacultyAdmissionsProgress(recruitSchoolId, semesterId, FacultyAdmissionsProgressVO.getCollegeId());
            if (null == integer) {
                FacultyAdmissionsProgressVO.setActualNumber(0);
            } else {
                FacultyAdmissionsProgressVO.setActualNumber(integer);
            }
        });
        return facultyAdmissionsProgressVOS;
    }

    @Override
    public ReportVO regionalEnrollmentProgressReport(Long recruitSchoolId, Long semesterId) {
        List<RegionalEnrollmentProgressReportVO> regionalEnrollmentProgressReportVOS = baseMapper.regionalEnrollmentProgressReport(recruitSchoolId, semesterId);
        ReportVO reportVO = new ReportVO();
        List<RegionalEnrollmentProgressReportVO> regionalEnrollmentProgressReportVOS2 = new ArrayList<>();
        regionalEnrollmentProgressReportVOS.forEach(regionalReportVO -> {
            BigDecimal a = BigDecimal.valueOf(regionalReportVO.getPayAmount());
            BigDecimal b = BigDecimal.valueOf(regionalReportVO.getTargetAmount());
            String percent = CalculateUtil(a, b);
            regionalReportVO.setDegreeCompletionAmount(percent);
//            int applyAmount = regionalReportVO.getPayAmount() + regionalReportVO.getRefundAmount();
//            regionalReportVO.setApplyAmount(applyAmount);
            regionalEnrollmentProgressReportVOS2.add(regionalReportVO);
        });
        int payAmount = regionalEnrollmentProgressReportVOS2.stream().collect(Collectors.summingInt(RegionalEnrollmentProgressReportVO::getPayAmount));
        int planAllNumber = regionalEnrollmentProgressReportVOS2.stream().collect(Collectors.summingInt(RegionalEnrollmentProgressReportVO::getTargetAmount));
        reportVO.setPlanAllNumber(planAllNumber);
        reportVO.setNumberCompletions(payAmount);
        BigDecimal a = BigDecimal.valueOf(payAmount);
        BigDecimal b = BigDecimal.valueOf(planAllNumber);
        String percent = CalculateUtil(a, b);
        reportVO.setAllDegreeCompletionAmount(percent);
        reportVO.setRegionalEnrollmentProgressReportVOS(regionalEnrollmentProgressReportVOS2);
        return reportVO;
    }

    public static String CalculateUtil(BigDecimal a, BigDecimal b) {
        String percent =
                b == null ? "-" :
                        b.compareTo(new BigDecimal(0)) == 0 ? "-" :
                                a == null ? "0.0%" :
                                        a.multiply(new BigDecimal(100)).divide(b, 1, BigDecimal.ROUND_HALF_UP) + "%";
        return percent;
    }

    @Override
    public SourceSchoolReportAllVO studentSourceReport(Long recruitSchoolId, Long semesterId) {
        SourceSchoolReportAllVO sourceSchoolReportAllVO = new SourceSchoolReportAllVO();

        List<SourceSchoolReportVO> sourceSchoolReportVOS = baseMapper.studentSourceReport(recruitSchoolId, semesterId);
//        sourceSchoolReportVOS.forEach(sourceSchoolReportVO -> {
//            int i = sourceSchoolReportVO.getPayAmount() + sourceSchoolReportVO.getRefundAmount();
//            sourceSchoolReportVO.setApplyAmount(i);
//        });
        Integer payAmountAll = sourceSchoolReportVOS.stream().collect(Collectors.summingInt(SourceSchoolReportVO::getPayAmount));
        Integer applyAmountAll = sourceSchoolReportVOS.stream().collect(Collectors.summingInt(SourceSchoolReportVO::getApplyAmount));
        Integer refundAmountAll = sourceSchoolReportVOS.stream().collect(Collectors.summingInt(SourceSchoolReportVO::getRefundAmount));
        sourceSchoolReportAllVO.setSourceSchoolReportVOS(sourceSchoolReportVOS);
        //交费人数总计
        sourceSchoolReportAllVO.setPayAmountAll(payAmountAll);
        //报名人数总计
        sourceSchoolReportAllVO.setApplyAmountAll(applyAmountAll);
        //退费人数总计
        sourceSchoolReportAllVO.setRefundAmountAll(refundAmountAll);
        return sourceSchoolReportAllVO;
    }

    @Override
    public List<PrincipalProgressVO> principalProgress(Long recruitSchoolId, Long semesterId, Long groupId) {
        return baseMapper.principalProgress(recruitSchoolId, semesterId, groupId);
    }

    @Override
    public List<EnrollmentAnalysisVO> enrollmentAnalysis(Long recruitSchoolId, Long semesterId, Long groupId) {
        return baseMapper.enrollmentAnalysis(recruitSchoolId, semesterId, groupId);
    }

    @Override
    public List<RegistrationStageVO> registrationStage(Long recruitSchoolId, Long semesterId, Long groupId) {
        return baseMapper.registrationStage(recruitSchoolId, semesterId, groupId);
    }

    @Override
    public List<ProfessionalRegistrationVO> professionalRegistration(Long recruitSchoolId, Long semesterId, Long groupId) {
        return baseMapper.professionalRegistration(recruitSchoolId, semesterId, groupId);
    }

    @Override
    public ChannelReportAllVO channelReport(Long recruitSchoolId, Long semesterId) {
        List<ChannelReportVO> channelReportVOS = baseMapper.channelReport(recruitSchoolId, semesterId);
        ChannelReportVO channelReportVOOnLine = channelReportVOS.stream().filter(ChannelReportVO -> "线上总计".equals(ChannelReportVO.getChannelName())).findAny().orElse(null);
        ChannelReportVO channelReportVOOfLine = channelReportVOS.stream().filter(ChannelReportVO -> "线下总计".equals(ChannelReportVO.getChannelName())).findAny().orElse(null);
        ChannelReportAllVO channelReportAllVO = new ChannelReportAllVO();
        channelReportAllVO.setChannelReportVOS(channelReportVOS);
        channelReportAllVO.setName("总计");
        if (null != channelReportVOOnLine && null != channelReportVOOfLine) {
            channelReportAllVO.setApplyAmountAll(channelReportVOOnLine.getApplyAmount() + channelReportVOOfLine.getApplyAmount());
            channelReportAllVO.setPayAmountAll(channelReportVOOnLine.getPayAmount() + channelReportVOOfLine.getPayAmount());
            channelReportAllVO.setRefundAmountAll(channelReportVOOnLine.getRefundAmount() + channelReportVOOfLine.getRefundAmount());
            channelReportAllVO.setTargetAmountAll(channelReportVOOnLine.getTargetAmount() + channelReportVOOfLine.getTargetAmount());
        }
        return channelReportAllVO;
    }

    @Override
    public RegionAllVO admissionsReport(RegionChargerQuery regionChargerQuery, Long recruitSchoolId, Long semesterId) {
        RegionAllVO regionAllVO = new RegionAllVO();
        List<RegionVO> regionVOS = baseMapper.queryRegion(recruitSchoolId, semesterId);
        //拜访学校数
        int visitedSchoolNumber = 0;
        //来校参观学校数
        int schoolNumber = 0;
        //来校参观人数
        int peopleNumber = 0;
        //报名人数小计
        int payAmount = 0;
        //退费人数
        int refundAmount = 0;
        //报名总人数
        int totalAmount = 0;
        //根据时间查询的报名人数
        int timeAmount = 0;
        for (RegionVO regionVO : regionVOS) {
            List<RegionChargerVO> regionChargerVOS = baseMapper.queryRegionCharger(regionChargerQuery, regionVO.getGroupId(),semesterId);
            if (regionChargerVOS.size() > 0) {
                regionVO.setRegionChargerVOS(regionChargerVOS);
                RegionChargerVO regionChargerVO = regionChargerVOS.stream().filter(RegionChargerVO -> "小计".equals(RegionChargerVO.getName())).findAny().orElse(null);
                if (regionChargerVO != null) {
                    visitedSchoolNumber += regionChargerVO.getVisitedSchoolNumber();
                    schoolNumber += regionChargerVO.getSchoolNumber();
                    peopleNumber += regionChargerVO.getPeopleNumber();
                    payAmount += regionChargerVO.getPayAmount();
                    refundAmount += regionChargerVO.getRefundAmount();
                    totalAmount += regionChargerVO.getTotalAmount();
                    timeAmount += regionChargerVO.getTimeAmount();
                }
            }
        }
        regionAllVO.setRegionVOS(regionVOS);
        regionAllVO.setVisitedSchoolNumber(visitedSchoolNumber);
        regionAllVO.setSchoolNumber(schoolNumber);
        regionAllVO.setPeopleNumber(peopleNumber);
        regionAllVO.setPayAmount(payAmount);
        regionAllVO.setRefundAmount(refundAmount);
        regionAllVO.setTotalAmount(totalAmount);
        regionAllVO.setTimeAmount(timeAmount);
        regionAllVO.setName("总计");
//        regionVOS.forEach(RegionVO -> {
//            List<RegionChargerVO> regionChargerVOS = baseMapper.queryRegionCharger(regionChargerQuery, RegionVO.getGroupId());
//            RegionChargerVO regionChargerVO = regionChargerVOS.stream().filter(RegionChargerVO -> "小计".equals(RegionChargerVO.getName())).findAny().orElse(null);
//            visitedSchoolNumber[0] += regionChargerVO.getVisitedSchoolNumber();
//            schoolNumber[0] += regionChargerVO.getSchoolNumber();
//        });
//        System.out.println(visitedSchoolNumber[0]);
        return regionAllVO;
    }

    @Override
    public List<PlanReportVO> queryAllPublishPlanAPP(Long recruitSchoolId) {
        return baseMapper.queryAllPublishPlanAPP(recruitSchoolId);
    }
}
