package com.educiot.recruit.data.service.preach;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.educiot.common.base.BasePageQuery;
import com.educiot.common.base.MyExcption;
import com.educiot.common.base.Result;
import com.educiot.recruit.data.entity.preach.PreachPlan;
import com.educiot.recruit.data.entity.query.common.UpdateStatusQuery;
import com.educiot.recruit.data.entity.query.preach.*;
import com.educiot.recruit.data.entity.vo.preach.PreachPlanVO;

/**
 * <p>
 * 招生宣讲计划 服务类
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
public interface IPreachPlanService extends IService<PreachPlan> {

    Result<?> update(Long userId, String userName, PreachPlanUpdateQuery query) throws MyExcption;


    Result<?> delete(Long userId,String userName, Long id);

    Result<?> add(Long userId, String userName, Long semesterId, String semesterName, PreachPlanAddQuery query) throws MyExcption;

    IPage<PreachPlanVO> listPage(PreachPlanPageQuery query, Long semsterId) throws MyExcption;

    boolean updateStatus(UpdateStatusQuery query);

    PreachPlanVO detail(Long id);

    IPage<PreachPlanVO> pageAllPreachPlanByChargerId(Long userId, Long semesterId, PreachPlanAllByUserIdPageQuery query) throws MyExcption;

    IPage<PreachPlanVO> pagePreachPlanByChargerId(Long userId, Long semesterId, PreachPlanByUserIdPageQuery query) throws MyExcption;

    IPage<PreachPlanVO> listPageByStudentPublicRelationId(Long studentPublicRelationId, BasePageQuery query);

    boolean publish(UpdateStatusQuery query);
}
