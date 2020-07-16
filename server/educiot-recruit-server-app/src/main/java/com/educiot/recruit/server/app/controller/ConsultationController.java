package com.educiot.recruit.server.app.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.educiot.common.base.CodeMsg;
import com.educiot.common.base.Result;
import com.educiot.recruit.data.entity.Consultation;
import com.educiot.recruit.data.entity.query.AddConsultationRegisterQuery;
import com.educiot.recruit.data.entity.query.AttendListQuery;
import com.educiot.recruit.data.entity.query.ConsultationDayDetailQuery;
import com.educiot.recruit.data.entity.vo.*;
import com.educiot.recruit.data.service.IConsultationRegisterService;
import com.educiot.recruit.data.service.IConsultationService;
import com.educiot.recruit.data.service.IOpendayService;
import com.educiot.recruit.server.app.common.BaseController;
import com.educiot.recruit.server.app.common.LoginUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 到校咨询表 前端控制器
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-17
 */
@Api(tags = {"到校咨询"}, value = "到校咨询")
@RestController
@RequestMapping("/consultation")
public class ConsultationController extends BaseController {

    @Resource
    private IConsultationService consultationService;

    @Resource
    private IOpendayService opendayService;

    @Resource
    private IConsultationRegisterService consultationRegisterService;

    @GetMapping("/getConsultationId")
    @ApiOperation(value = "获取咨询ID", response = ConsultationIdVO.class)
    public Result<?> getConsultationId() {

        QueryWrapper<Consultation> wrapper = new QueryWrapper<>();
        wrapper.eq("semester_id", getLoginUser().getCurrentSemesterId());
        Consultation consultation = consultationService.getOne(wrapper);
        if (consultation == null) {
            return Result.success(null);
        }
        ConsultationIdVO consultationIdVO = new ConsultationIdVO();
        consultationIdVO.setConsultationId(consultation.getConsultationId());
        consultationIdVO.setStartTime(consultation.getStartTime());
        consultationIdVO.setEndTime(consultation.getEndTime());
        return Result.success(consultationIdVO);
    }

    @GetMapping("/getConsultationDayDetail")
    @ApiOperation(value = "获取咨询日程详情", response = ConsultationDayDetailVO.class)
    public Result<?> getConsultationDayDetail(ConsultationDayDetailQuery consultationDayDetailQuery) {

        ConsultationDayDetailVO consultationDayDetailVO = opendayService.getConsultationDayDetail(consultationDayDetailQuery);

        return Result.success(consultationDayDetailVO);
    }

    @GetMapping("/getConsultationDayList/{consultationId}")
    @ApiOperation(value = "获取接待日信息列表", response = ConsultationDayListVO.class)
    public Result<?> getConsultationDayList(@PathVariable Long consultationId) {

        List<ConsultationDayListVO> result = opendayService.getConsultationDayList(consultationId);
        return Result.success(result);
    }

    @GetMapping("/getConsultationCalender/{consultationId}")
    @ApiOperation(value = "获取咨询人数日历", response = ConsultationCalenderVO.class)
    public Result<?> getConsultationCalender(@PathVariable Long consultationId) {

        List<ConsultationCalenderVO> result = consultationRegisterService.getConsultationCalender(consultationId);
        return Result.success(result);
    }

    @PostMapping("/addConsultationRegister")
    @ApiOperation(value = "增加咨询登记")
    public Result<?> addConsultationRegister(@RequestBody AddConsultationRegisterQuery addConsultationRegisterQuery) {

        //判断相同日期同一个学生不能创建两条到校咨询
        Integer integer = consultationRegisterService.queryRegisterCount(addConsultationRegisterQuery.getConsultationDate(), addConsultationRegisterQuery.getStudentPublicRelationId());
        if(integer>0){
            consultationRegisterService.deleteConsultationRegister(addConsultationRegisterQuery.getConsultationDate(), addConsultationRegisterQuery.getStudentPublicRelationId());
        }
        LoginUser loginUser = getLoginUser();
        boolean status = consultationRegisterService.addConsultationRegister(addConsultationRegisterQuery,
                loginUser.getApiInfo().getUserId(), loginUser.getApiInfo().getUserName());
        if (status) {
            return Result.success();
        } else {
            return Result.error(CodeMsg.DATA_INSERT_ERROR);
        }
    }

    @GetMapping("/getConsultationRegisterList")
    @ApiOperation(value = "获取咨询注册列表", response = ConsultationRegisterVO.class)
    public Result<?> getConsultationRegisterList(AttendListQuery attendListQuery) {

        Page<ConsultationRegisterVO> page = new Page<>(attendListQuery.getPageNum(), attendListQuery.getPageSize());

        IPage<ConsultationRegisterVO> result = consultationRegisterService.getConsultationRegisterList(page, attendListQuery);
        return Result.success(result);
    }

    @GetMapping("/getConsultationListByStudent/{studentPublicRelationId}")
    @ApiOperation(value = "根据学生查询咨询注册列表", response = StudentConsultationListVO.class)
    public Result<?> getConsultationListByStudent(@PathVariable Long studentPublicRelationId) {

        List<StudentConsultationListVO> result = consultationRegisterService.getConsultationListByStudent(studentPublicRelationId);

        return Result.success(result);
    }
}
