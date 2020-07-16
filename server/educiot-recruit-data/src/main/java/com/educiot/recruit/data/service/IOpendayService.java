package com.educiot.recruit.data.service;

import com.educiot.recruit.data.entity.Openday;
import com.baomidou.mybatisplus.extension.service.IService;
import com.educiot.recruit.data.entity.query.AddConsultationDayQuery;
import com.educiot.recruit.data.entity.query.ConsultationDayDetailQuery;
import com.educiot.recruit.data.entity.vo.ConsultationDayDetailVO;
import com.educiot.recruit.data.entity.vo.ConsultationDayListVO;
import com.educiot.common.base.Result;

import java.util.List;

/**
 * <p>
 * 接待日信息表 服务类
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
public interface IOpendayService extends IService<Openday> {

    /**
     * 新增/编辑咨询日程
     * @param addConsultationDayQuery
     * @return true 添加成功
     */
    Result<?> addOrEditConsultationDay(Long userId,String userName, AddConsultationDayQuery addConsultationDayQuery);

    /**
     * 获取咨询日程详情
     * @param consultationDayDetailQuery
     * @return ConsultationDayDetailVO
     */
    ConsultationDayDetailVO getConsultationDayDetail(ConsultationDayDetailQuery consultationDayDetailQuery);

    /**
     * 获取接待日信息列表
     * @param consultationId
     * @return List<ConsultationDayListVO>
     */
    List<ConsultationDayListVO> getConsultationDayList(Long consultationId);
}
