package com.educiot.recruit.server.wechat.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.educiot.recruit.common.base.CodeMsg;
import com.educiot.recruit.common.base.Result;
import com.educiot.recruit.data.entity.Consultation;
import com.educiot.recruit.data.entity.query.AddRegisterQuery;
import com.educiot.recruit.data.entity.query.ConsultationDayDetailQuery;
import com.educiot.recruit.data.entity.vo.*;
import com.educiot.recruit.data.service.IConsultationRegisterService;
import com.educiot.recruit.data.service.IConsultationService;
import com.educiot.recruit.data.service.IOpendayService;
import com.educiot.recruit.server.wechat.common.BaseController;
import com.fasterxml.jackson.annotation.JsonFormat;
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
 * @since 2020-04-29
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

        MiniProgramLoginVO miniProgramLoginVO = getLoginVO();
        if (miniProgramLoginVO == null || miniProgramLoginVO.getPlan() == null) {
            return Result.success(null);
        }
        QueryWrapper<Consultation> wrapper = new QueryWrapper<>();
        wrapper.eq("semester_id", getLoginVO().getPlan().getSemesterId());
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

    @GetMapping("/getMyRegister")
    @ApiOperation(value = "获取我的预约日期")
    public Result<?> getMyRegister() {
        Long studentPublicRelationId = getLoginVO().getStudentPublicRelationId();
        List<String> result = consultationRegisterService.getMyRegister(studentPublicRelationId);
        return Result.success(result);
    }

    @PostMapping("/addRegister")
    @ApiOperation(value = "申请到校咨询")
    public Result<?> addRegister(@RequestBody AddRegisterQuery addRegisterQuery) {
        Long studentPublicRelationId = getLoginVO().getStudentPublicRelationId();
        boolean status = consultationRegisterService.addRegister(addRegisterQuery, studentPublicRelationId, getLoginVO().getPlan().getSemesterId());
        if (status) {
            return Result.success();
        } else {
            return Result.error(CodeMsg.CONSULTATION_REGISTER_EXIST);
        }
    }

    @DeleteMapping("/deleteRegister/{date}")
    @ApiOperation(value = "删除到校咨询")
    public Result<?> deleteRegister(@PathVariable @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") String date) {

        Long studentPublicRelationId = getLoginVO().getStudentPublicRelationId();

        boolean status = consultationRegisterService.deleteRegister(date, studentPublicRelationId);

        if (status) {
            return Result.success();
        } else {
            return Result.error(CodeMsg.DATA_DELETE_ERROR);
        }
    }

    @GetMapping("/getConsultationCalender/{consultationId}")
    @ApiOperation(value = "获取咨询人数日历", response = ConsultationCalenderVO.class)
    public Result<?> getConsultationCalender(@PathVariable Long consultationId) {

        List<ConsultationCalenderVO> result = consultationRegisterService.getConsultationCalender(consultationId);
        return Result.success(result);
    }

}
