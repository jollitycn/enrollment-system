package com.educiot.recruit.data.mapper;

import com.educiot.recruit.data.entity.Consultation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 到校咨询表 Mapper 接口
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-13
 */
public interface ConsultationMapper extends BaseMapper<Consultation> {


    /**
     * 查询所有计划的到校咨询
     * @param recruitSchoolId
     * @return
     */
    List<Consultation> queryAllConsultation(@Param("recruitSchoolId") Long recruitSchoolId);
}
