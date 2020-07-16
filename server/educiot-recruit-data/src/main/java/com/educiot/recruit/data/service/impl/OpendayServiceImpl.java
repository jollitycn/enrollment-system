package com.educiot.recruit.data.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.educiot.recruit.data.entity.Consultation;
import com.educiot.recruit.data.entity.ConsultationFeedback;
import com.educiot.recruit.data.entity.ConsultationRegister;
import com.educiot.recruit.data.entity.Openday;
import com.educiot.recruit.data.entity.query.AddConsultationDayQuery;
import com.educiot.recruit.data.entity.query.ConsultationDayDetailQuery;
import com.educiot.recruit.data.entity.vo.ConsultationDayDetailVO;
import com.educiot.recruit.data.entity.vo.ConsultationDayListVO;
import com.educiot.recruit.data.mapper.OpendayMapper;
import com.educiot.recruit.data.service.IConsultationFeedbackService;
import com.educiot.recruit.data.service.IConsultationRegisterService;
import com.educiot.recruit.data.service.IConsultationService;
import com.educiot.recruit.data.service.IOpendayService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.educiot.recruit.common.base.CodeMsg;
import com.educiot.recruit.data.LoginUser;
import com.educiot.recruit.common.base.Result;
import com.educiot.recruit.common.constant.Constant;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 接待日信息表 服务实现类
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-13
 */
@Service
public class OpendayServiceImpl extends ServiceImpl<OpendayMapper, Openday> implements IOpendayService {

    @Resource
    private IConsultationService consultationService;

    @Resource
    private IConsultationRegisterService consultationRegisterService;

    @Resource
    private IConsultationFeedbackService consultationFeedbackService;

    /**
     * 新增/编辑咨询日程
     * @param loginUser
     * @param addConsultationDayQuery
     * @return true 添加成功
     */
    @Override
    public Result<?> addOrEditConsultationDay(LoginUser loginUser, AddConsultationDayQuery addConsultationDayQuery) {

        //如果没有id新增咨询日程
        if (addConsultationDayQuery.getOpendayId() == null) {

            //判断当前日期是否有咨询日程
            QueryWrapper<Openday> wrapper = new QueryWrapper<>();
            wrapper.eq("consultation_id", addConsultationDayQuery.getConsultationId());
            wrapper.eq("openday_date", addConsultationDayQuery.getOpendayDate());
            int count = count(wrapper);
            if (count > 0) {
                return Result.error(CodeMsg.REPEAT__OPENDAY);
            }

            Openday openday = new Openday();
            BeanUtils.copyProperties(addConsultationDayQuery, openday);

            //填充创建信息
            openday.setCreateTime(LocalDateTime.now());
            openday.setCreatorId(loginUser.getApiInfo().getUserId());
            openday.setCreatorName(loginUser.getApiInfo().getUserName());

            boolean status = save(openday);
            if (status) {
                return Result.success();
            } else {
                return Result.error(CodeMsg.DATA_INSERT_ERROR);
            }

        } else {
            Openday openday = getById(addConsultationDayQuery.getOpendayId());
            if (openday == null) {
                return Result.error(CodeMsg.DATA_UPDATE_ERROR);
            } else {
                BeanUtils.copyProperties(addConsultationDayQuery, openday);

                //填充创建信息
                openday.setCreateTime(LocalDateTime.now());
                openday.setCreatorId(loginUser.getApiInfo().getUserId());
                openday.setCreatorName(loginUser.getApiInfo().getUserName());

                boolean status = updateById(openday);
                if (status) {
                    return Result.success();
                } else {
                    return Result.error(CodeMsg.DATA_INSERT_ERROR);
                }
            }
        }

    }

    /**
     * 获取咨询日程详情
     * @param consultationDayDetailQuery
     * @return ConsultationDayDetailVO
     */
    @Override
    public ConsultationDayDetailVO getConsultationDayDetail(ConsultationDayDetailQuery consultationDayDetailQuery) {

        ConsultationDayDetailVO consultationDayDetailVO = new ConsultationDayDetailVO();

        QueryWrapper<Openday> wrapper = new QueryWrapper<>();
        wrapper.eq("openday_date", consultationDayDetailQuery.getDate());
        wrapper.eq("consultation_id", consultationDayDetailQuery.getConsultationId());
        Openday openday = getOne(wrapper);

        //判断是否设置咨询日，如果没有返回日常接待人信息
        if (null == openday) {
            Consultation consultation = consultationService.getById(consultationDayDetailQuery.getConsultationId());
            consultationDayDetailVO.setReceptionistId(consultation.getDailyReceptionistId());
            consultationDayDetailVO.setReceptionistName(consultation.getDailyReceptionistName());
            consultationDayDetailVO.setOpendayDate(consultationDayDetailQuery.getDate());
            consultationDayDetailVO.setTelephone(consultation.getTelephone());
            consultationDayDetailVO.setSchedule(consultation.getSchedule());
            consultationDayDetailVO.setAddress(consultation.getAddress());
            consultationDayDetailVO.setIsOpenday(Constant.SYS_ZERO);
        } else {
            BeanUtils.copyProperties(openday, consultationDayDetailVO);
            consultationDayDetailVO.setOpendayDate(consultationDayDetailQuery.getDate());
        }

        //获取该咨询日实际人数
        QueryWrapper<ConsultationRegister> registerQueryWrapper = new QueryWrapper<>();
        registerQueryWrapper.eq("consultation_id", consultationDayDetailQuery.getConsultationId());
        registerQueryWrapper.eq("consultation_date", consultationDayDetailQuery.getDate());
        registerQueryWrapper.eq("is_attend", Constant.SYS_ONE);
        int amount = consultationRegisterService.count(registerQueryWrapper);
        consultationDayDetailVO.setActualAmount(amount);

        //获取是否有总结
        QueryWrapper<ConsultationFeedback> feedbackQueryWrapper = new QueryWrapper<>();
        feedbackQueryWrapper.eq("consultation_date", consultationDayDetailQuery.getDate());
        feedbackQueryWrapper.eq("consultation_id", consultationDayDetailQuery.getConsultationId());
        ConsultationFeedback consultationFeedback = consultationFeedbackService.getOne(feedbackQueryWrapper);
        if (consultationFeedback != null) {
            consultationDayDetailVO.setIsSummary(Constant.SYS_ONE);
            consultationDayDetailVO.setSummary(consultationFeedback.getSummary());
            consultationDayDetailVO.setConsultationContent(consultationFeedback.getConsultationContent());
        } else {
            consultationDayDetailVO.setIsSummary(Constant.SYS_ZERO);
        }

        return consultationDayDetailVO;

    }

    /**
     * 获取接待日信息列表
     * @param consultationId
     * @return List<ConsultationDayListVO>
     */
    @Override
    public List<ConsultationDayListVO> getConsultationDayList(Long consultationId) {

        //获取接待日列表
        QueryWrapper<Openday> wrapper = new QueryWrapper<>();
        wrapper.eq("consultation_id", consultationId);
        List<Openday> opendayList = list(wrapper);

        //生成返回列表
        List<ConsultationDayListVO> result = new ArrayList<>();
        for (Openday openday : opendayList) {
            ConsultationDayListVO consultationDayListVO = new ConsultationDayListVO();
            BeanUtils.copyProperties(openday, consultationDayListVO);
            result.add(consultationDayListVO);
        }
        return result;
    }
}
