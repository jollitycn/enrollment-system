package com.educiot.recruit.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.educiot.recruit.data.entity.Plan;
import com.educiot.recruit.data.entity.query.ListPlanPageQuery;
import com.educiot.recruit.data.entity.vo.ChargerVO;
import com.educiot.recruit.data.entity.vo.PlanIDAndNameVO;
import com.educiot.recruit.data.entity.vo.PlanVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 招生计划表  Mapper 接口
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
public interface PlanMapper extends BaseMapper<Plan> {

    /**
     * 分页查询计划列表信息
     * @param page 分页信息
     * @param param 查询条件
     * @return
     */
    List<Plan> listPlanPage(IPage<Plan> page, @Param("param") ListPlanPageQuery param);

    /**
     * 查询招生计划基本信息
     * @param planId
     * @return
     */
    PlanVO queryPlanInfo(@Param("planId") Long planId);

    /**
     * 查询负责人列表信息
     * @param semesterId 学期ID
     * @return 负责人列表
     */
    List<ChargerVO> queryChargerBySemesterId(@Param("semesterId") Long semesterId,@Param("recruitSchoolId")Long recruitSchoolId);

    String queryChargerIdByPlanId(@Param("planId")Long planId);

    PlanIDAndNameVO queryDefaultPlanInfo(Long recruitSchoolId);

    PlanIDAndNameVO queryDefaultPlanInfoWeChat(Long recruitSchoolId);
}
