package com.educiot.recruit.server.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.educiot.recruit.common.base.CodeMsg;
import com.educiot.recruit.common.base.MyExcption;
import com.educiot.recruit.common.base.Result;
import com.educiot.recruit.data.LoginUser;
import com.educiot.recruit.data.entity.Consultation;
import com.educiot.recruit.data.entity.ConsultationFeedback;
import com.educiot.recruit.data.entity.query.*;
import com.educiot.recruit.data.entity.vo.*;
import com.educiot.recruit.data.service.IConsultationFeedbackService;
import com.educiot.recruit.data.service.IConsultationRegisterService;
import com.educiot.recruit.data.service.IConsultationService;
import com.educiot.recruit.data.service.IOpendayService;
import com.educiot.recruit.server.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;



/**
 * <p>
 * 到校咨询表 前端控制器
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
@Api(tags = {"到校咨询"}, value = "到校咨询")
@RestController
@RequestMapping("/consultation")
public class ConsultationController extends BaseController{

    @Resource
    private IConsultationService consultationService;

    @Resource
    private IOpendayService opendayService;

    @Resource
    private IConsultationFeedbackService consultationFeedbackService;

    @Resource
    private IConsultationRegisterService consultationRegisterService;

    @GetMapping("/getDefaultPlanId")
    @ApiOperation(value = "获取默认计划id")
    public Result<?> getDefaultPlanId() throws MyExcption {

        Long defaultPlanId = getLoginUser().getCurrentSchoolPlanId();
        if (defaultPlanId == null) {
            return Result.success(null);
        }
        return Result.success(defaultPlanId);
    }

        @GetMapping("/getConsultationId/{semesterId}")
        @ApiOperation(value = "获取咨询ID")
        public Result<?> getConsultationId(@PathVariable Long semesterId) throws MyExcption {

            QueryWrapper<Consultation> wrapper = new QueryWrapper<>();
//            wrapper.eq("semester_id", getLoginUser().getCurrentSemesterId());
            wrapper.eq("semester_id", semesterId);
            Consultation consultation = consultationService.getOne(wrapper);
            if (consultation == null) {
                return Result.success(null);
            }
            return Result.success(consultation.getConsultationId().toString());
        }

    @PostMapping("/addOrEditConsultation")
    @ApiOperation(value = "新增/编辑咨询")
    public Result<?> addOrEditConsultation(@RequestBody AddConsultationQuery addConsultationQuery) throws MyExcption {

        LoginUser loginUser = getLoginUser();

        return consultationService.addOrEditConsultation(loginUser, addConsultationQuery);
    }

    @GetMapping("/getConsultationDetail/{consultationId}")
    @ApiOperation(value = "获取到校咨询详情", response = ConsultationDetailVO.class)
    public Result<?> getConsultationDetail(@PathVariable Long consultationId) {

        Consultation consultation = consultationService.getById(consultationId);
        ConsultationDetailVO consultationDetailVO = new ConsultationDetailVO();
        BeanUtils.copyProperties(consultation, consultationDetailVO);
        return Result.success(consultationDetailVO);
    }

    @PostMapping("/addOrEditConsultationDay")
    @ApiOperation(value = "新增/编辑咨询日程")
    public Result<?> addConsultationDay(@RequestBody AddConsultationDayQuery addConsultationDayQuery) {

        //获取登录用户信息
        LoginUser loginUser = getLoginUser();

        return opendayService.addOrEditConsultationDay(loginUser, addConsultationDayQuery);

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

    @GetMapping("/getAttendList")
    @ApiOperation(value = "获取咨询签到列表", response = AttendListVO.class)
    public Result<?> getAttendList(AttendListQuery attendListQuery) {

        Page<AttendListVO> page = new Page<>(attendListQuery.getPageNum(), attendListQuery.getPageSize());
        IPage<AttendListVO> result = consultationRegisterService.getAttendList(page, attendListQuery);
        return Result.success(result);
    }

    @PostMapping("/addConsultationFeedback")
    @ApiOperation(value = "新增咨询反馈")
    public Result<?> addConsultationFeedback(@RequestBody AddConsultationFeedbackQuery addConsultationFeedbackQuery) {

        //获取登录用户信息
        LoginUser loginUser = getLoginUser();

        //判断是否重复添加
        QueryWrapper<ConsultationFeedback> wrapper = new QueryWrapper<>();
        wrapper.eq("consultation_date", addConsultationFeedbackQuery.getConsultationDate());
        wrapper.eq("consultation_id", addConsultationFeedbackQuery.getConsultationId());
        int count = consultationFeedbackService.count(wrapper);
        if (count > 0) {
            return Result.error(CodeMsg.REPEAT_DAY);
        }

        boolean status = consultationFeedbackService.addConsultationFeedback(addConsultationFeedbackQuery, loginUser);

        if (status) {
            return Result.success();
        } else {
            return Result.error(CodeMsg.DATA_INSERT_ERROR);
        }
    }

    @PostMapping("/attend")
    @ApiOperation("签到列表")
    public Result<?> attend(@RequestBody AttendQuery attendQuery) {

        //获取登录用户信息
        LoginUser loginUser = getLoginUser();

        boolean status = consultationRegisterService.attend(attendQuery, loginUser.getApiInfo().getUserId(), loginUser.getApiInfo().getUserName());
        
        if (status) {
            return Result.success();
        } else {
            return Result.error(CodeMsg.ALREADY_ATTEND);
        }
    }

    @GetMapping("/getRegisterCount/{consultationId}")
    @ApiOperation(value = "获取咨询人数", response = ConsultationRegisterCountVO.class)
    public Result<?> getRegisterCount(@PathVariable Long consultationId) {

        List<ConsultationRegisterCountVO> result = consultationRegisterService.getRegisterCount(consultationId);

        return Result.success(result);
    }
}
