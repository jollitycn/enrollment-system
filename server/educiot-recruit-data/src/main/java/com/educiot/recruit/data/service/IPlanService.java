package com.educiot.recruit.data.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.educiot.common.api.APIRequest;
import com.educiot.common.base.MyExcption;
import com.educiot.common.base.Result;
import com.educiot.recruit.data.entity.Plan;
import com.educiot.recruit.data.entity.query.*;
import com.educiot.recruit.data.entity.vo.ChargerVO;
import com.educiot.recruit.data.entity.vo.PlanIDAndNameVO;
import com.educiot.recruit.data.entity.vo.PlanLoginVO;
import com.educiot.recruit.data.entity.vo.PlanVO;

import java.util.List;

/**
 * <p>
 * 招生计划表  服务类
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
public interface IPlanService extends IService<Plan> {

    /**
     * 分页查询计划列表信息
     * @param listPlanPageQuery 招生计划分页查询条件封装类
     * @return Plan 招生计划列表
     */
    IPage<Plan> listPlanPage(ListPlanPageQuery listPlanPageQuery);

    /**
     * 添加招生计划信息
     *
     * @param addPlanQuery 添加招生计划入参封装类
     * @return Result 放回添加是否成功状态
     */
    Result addPlanInfo(AddPlanQuery addPlanQuery, Long recruitSchoolId, Long userId,String userName,Long apiId,APIRequest apiRequest) throws MyExcption;

    /**
     * 获取招生计划基本信息
     *
     * @param planId 计划ID
     * @return 招生计划基本信息
     */
    PlanVO queryPlanInfo(Long planId);

    /**
     *
     * @param
     * @return
     */
    List<Plan> queryAllPublishPlan(Long schoolId);

    /**
     * 已发布和已完成的计划查询
     * @param schoolId
     * @return
     */
    List<Plan> queryAllFinishedPlan(Long schoolId);

    /**
     * 获取默认招生计划基本信息
     * @param
     * @return 返回默认的招生计划信息
     */
    Result<PlanVO> queryDefaultPlanInfo(Long schoolId);

    /**
     * 获取默认招生计划基本信息
     * @param
     * @return 返回默认的招生计划信息
     */
    Result<PlanVO> queryDefaultPlanInfoWeChat(Long schoolId);

    /**
     * 获取默认招生计划基本信息(登录时)
     *
     * @param
     * @return 返回默认的招生计划信息
     */
    Result<PlanLoginVO> queryDefaultPlanLogin(Long schoolId);

    /**
     * 修改计划的发布状态,及逻辑删除
     * @param updatePlanQuery 修改招生计划的封装入参类
     * @return Result 修改是否成功状态
     */
    Result updateStatus(UpdatePlanQuery updatePlanQuery,Long userId,String userName);

    /**
     * 修改计划的完成状态
     * @param updatePlanQuery 编辑计划的入参封装实体
     * @return 成功状态
     */
    Boolean updateStatusEnd(UpdatePlanQuery updatePlanQuery,Long recruitSchoolId,Long currentSemesterId,Long userId,String userName);

    /**
     * 修改计划的默认状态
     * @param updatePlanQuery 修改计划默认状态的封装实体类
     * @return Result 返回是否修改成功状态
     */
    Result updateIsDefaulted(UpdatePlanIsDefaultecQuery updatePlanQuery, Long  recruitSchoolId);

    /**
     * 编辑招生计划基础信息
     * @param updatePlanInfoQuery 修改计划基本信息的封装实体类
     * @return 返回是否修改成功状态
     */
    Result updatePlanInfo(UpdatePlanInfoQuery updatePlanInfoQuery,Long recruitSchoolId,Long  userId);

    /**
     * 复制招生计划信息
     * @param addCopyPlanQuery 复制招生计划的封装实体类
     * @return 是否复制成功状态
     */
    Result addCopyPlanInfo(AddCopyPlanQuery addCopyPlanQuery,Long recruitSchoolId,Long userId,String userName);

    /**
     * 查询负责人列表信息
     * @param semesterId 学期ID
     * @return 负责人列表
     */
    List<ChargerVO> queryChargerBySemesterId(Long semesterId,Long recruitSchoolId);

    String queryChargerIdByPlanId(Long planId);

    PlanIDAndNameVO queryDefaultPlanInfoPlanIDAndNameVO(Long recruitSchoolId);

    PlanVO queryDefaultPlanInfoBySchool(Long schoolId);
}
