package com.educiot.recruit.data.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.educiot.recruit.data.entity.ConsultationRegister;
import com.baomidou.mybatisplus.extension.service.IService;
import com.educiot.recruit.data.entity.query.AddConsultationRegisterQuery;
import com.educiot.recruit.data.entity.query.AddRegisterQuery;
import com.educiot.recruit.data.entity.query.AttendListQuery;
import com.educiot.recruit.data.entity.query.AttendQuery;
import com.educiot.recruit.data.entity.vo.*;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * 招生咨询登记表 服务类
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
public interface IConsultationRegisterService extends IService<ConsultationRegister> {

    /**
     * 获取咨询人数日历
     * @param consultationId
     * @return List<ConsultationCalenderVO>
     */
    List<ConsultationCalenderVO> getConsultationCalender(Long consultationId);


    /**
     * 获取咨询签到列表
     * @param page
     * @param consultationDayDetailQuery
     * @return IPage<AttendListVO>
     */
    IPage<AttendListVO> getAttendList(Page<AttendListVO> page, AttendListQuery consultationDayDetailQuery);

    /**
     * 签到
     * @param attendQuery
     * @param userId
     * @param userName
     * @return true 签到成功
     */
    boolean attend(AttendQuery attendQuery, Long userId, String userName);


    /**
     * 增加咨询登记
     * @param addConsultationRegisterQuery
     * @param userId
     * @param userName
     * @return true 增加成功
     */
    boolean addConsultationRegister(AddConsultationRegisterQuery addConsultationRegisterQuery, Long userId, String userName);

    /**
     * 获取咨询注册列表
     * @param page
     * @param attendListQuery
     * @return IPage<ConsultationRegisterVO>
     */
    IPage<ConsultationRegisterVO> getConsultationRegisterList(Page<ConsultationRegisterVO> page, AttendListQuery attendListQuery);

    /**
     * 获取我的预约日期
     * @param studentPublicRelationId
     * @return List<String>
     */
    List<String> getMyRegister(Long studentPublicRelationId);

    /**
     * 申请到校咨询
     * @param addRegisterQuery
     * @param studentPublicRelationId
     * @param currentSemesterId
     * @return true 添加成功
     */
    boolean addRegister(AddRegisterQuery addRegisterQuery, Long studentPublicRelationId, Long currentSemesterId);

    /**
     * 删除到校咨询
     * @param date
     * @param studentPublicRelationId
     * @return true 删除成功
     */
    boolean deleteRegister(String date, Long studentPublicRelationId);

    /**
     * 根据学生查询咨询注册列表
     * @param studentPublicRelationId
     * @return List<StudentConsultationListVO>-
     */
    List<StudentConsultationListVO> getConsultationListByStudent(Long studentPublicRelationId);

    /**
     * 查询到校咨询是否已存在
     * @param consultationDate
     * @param studentPublicRelationId
     * @return
     */
    Integer queryRegisterCount(LocalDate consultationDate, Long studentPublicRelationId);

    /**
     * 删除旧的到校咨询
     * @return
     */
    Integer deleteConsultationRegister(LocalDate consultationDate, Long studentPublicRelationId);

    List<ConsultationRegisterCountVO> getRegisterCount(Long consultationId);
}
