package com.educiot.recruit.data.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.educiot.recruit.data.entity.ConsultationRegister;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.educiot.recruit.data.entity.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 招生咨询登记表 Mapper 接口
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-13
 */
public interface ConsultationRegisterMapper extends BaseMapper<ConsultationRegister> {

    /**
     * 获取咨询人数日历
     * @param consultationId
     * @return List<ConsultationCalenderVO>
     */
    List<ConsultationCalenderVO> getConsultationCalender(@Param("consultationId") Long consultationId);

    /**
     * 获取咨询签到列表
     * @param page
     * @param consultationId
     * @param date
     * @return List<ConsultationRegister>
     */
    List<ConsultationRegister> getAttendList(Page<AttendListVO> page,
                                      @Param("consultationId") Long consultationId,
                                      @Param("date") String date);

    /**
     * 获取咨询注册列表
     * @param page
     * @param consultationId
     * @param date
     * @param consultationType
     * @return IPage<ConsultationRegisterVO>
     */
    IPage<ConsultationRegisterVO> getConsultationRegisterList(Page<ConsultationRegisterVO> page,
                                                              @Param("consultationId") Long consultationId,
                                                              @Param("date") String date,
                                                              @Param("consultationType") Integer consultationType);

    /**
     * 根据学生查询咨询注册列表
     * @param studentPublicRelationId
     * @return List<StudentConsultationListVO>-
     */
    List<StudentConsultationListVO> getConsultationListByStudent(@Param("studentPublicRelationId") Long studentPublicRelationId);

    List<ConsultationRegisterCountVO> getRegisterCount(@Param("consultationId") Long consultationId);
}
