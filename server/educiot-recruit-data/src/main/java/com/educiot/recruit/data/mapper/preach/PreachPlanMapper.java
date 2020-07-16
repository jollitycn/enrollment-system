package com.educiot.recruit.data.mapper.preach;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.educiot.recruit.data.entity.preach.PreachPlan;
import com.educiot.recruit.data.entity.query.common.UpdateStatusQuery;
import com.educiot.recruit.data.entity.query.preach.PreachPlanPageQuery;
import com.educiot.recruit.data.entity.vo.preach.PreachPlanVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 招生宣讲计划 Mapper 接口
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-13
 */
public interface PreachPlanMapper extends BaseMapper<PreachPlan> {

    List<PreachPlanVO> listPage(IPage<PreachPlanVO> page, @Param("param") PreachPlanPageQuery param, Long semesterId);

    int updateStatus(@Param("param") UpdateStatusQuery param);

    List<PreachPlanVO> pageAllPreachPlanByUserId(IPage<PreachPlanVO> page, Long userId, Long semesterId);

    List<PreachPlanVO> pagePreachPlanByUserId(IPage<PreachPlanVO> page, Long userId, Long semesterId);

    List<PreachPlanVO> listPageByStudentPublicRelationId(IPage<PreachPlanVO> page, Long studentPublicRelationId);
}
