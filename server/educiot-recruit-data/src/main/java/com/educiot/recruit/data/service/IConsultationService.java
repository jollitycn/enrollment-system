package com.educiot.recruit.data.service;

import com.educiot.recruit.data.entity.Consultation;
import com.baomidou.mybatisplus.extension.service.IService;
import com.educiot.recruit.data.entity.query.AddConsultationQuery;
import com.educiot.common.base.Result;

import java.util.List;

/**
 * <p>
 * 到校咨询表 服务类
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
public interface IConsultationService extends IService<Consultation> {

    /**
     * 新增/编辑到校咨询
     * @param addConsultationQuery
     * @return true 添加成功
     */
    Result<?> addOrEditConsultation(Long semesterId,String semester,Long userId,String userName,AddConsultationQuery addConsultationQuery);

    /**
     * 查询所有的到校咨询计划
     * @return 咨询计划列表
     */
    List<Consultation> queryAllConsultation(Long recruitSchoolId);
}
