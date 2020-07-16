package com.educiot.recruit.data.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.educiot.recruit.common.api.APIRequest;
import com.educiot.recruit.common.api.DepartInfoResult;
import com.educiot.recruit.common.base.CodeMsg;
import com.educiot.recruit.common.base.MyExcption;
import com.educiot.recruit.common.base.Result;
import com.educiot.recruit.common.constant.Constant;
import com.educiot.recruit.data.LoginUser;
import com.educiot.recruit.data.entity.*;
import com.educiot.recruit.data.entity.query.*;
import com.educiot.recruit.data.entity.vo.ChargerVO;
import com.educiot.recruit.data.entity.vo.PlanIDAndNameVO;
import com.educiot.recruit.data.entity.vo.PlanLoginVO;
import com.educiot.recruit.data.entity.vo.PlanVO;
import com.educiot.recruit.data.mapper.PlanMapper;
import com.educiot.recruit.data.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 招生计划表  服务实现类
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
@Service
public class PlanServiceImpl extends ServiceImpl<PlanMapper, Plan> implements IPlanService {

    @Resource
    private IPlanSchoolIntroductionService planSchoolIntroductionService;

    @Resource
    private IPlanRecruitInfoService planRecruitInfoService;

    @Resource
    private PlanMapper planMapper;

    @Resource
    private ITargetService targetService;

    @Resource
    private ICollegeService collegeService;

    @Resource
    private ITaskService taskService;

    @Resource
    private ITaskReceiverService taskReceiverService;

    @Resource
    private IMajorService majorService;

    @Override
    public IPage<Plan> listPlanPage(ListPlanPageQuery listPlanPageQuery) {
        IPage<Plan> page = new Page<>(listPlanPageQuery.getPageNum(), listPlanPageQuery.getPageSize());
        List<Plan> list = baseMapper.listPlanPage(page, listPlanPageQuery);
        page.setRecords(list);
        return page;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result addPlanInfo(AddPlanQuery addPlanQuery, LoginUser loginUser, APIRequest apiRequest) throws MyExcption {
        LocalDateTime now = LocalDateTime.now();
        if (null == addPlanQuery) {
            return Result.error(CodeMsg.PARAMETER_ISNULL);
        }
        addPlanQuery.setCreatorId(loginUser.getApiInfo().getUserId());
        addPlanQuery.setCreatorName(loginUser.getApiInfo().getUserName());
        Long schoolId = loginUser.getCurrentSchoolId();
        addPlanQuery.setRecruitSchoolId(schoolId);
        //判断目标是否存在
//        Target target = targetService.checkTarget(schoolId, addPlanQuery.getSemesterId());
//        if (null == target) {
//            return Result.error(CodeMsg.TARGET_NOT_EXIST);
//        }
        Plan plan = new Plan();
        try {
            //去重判断
            QueryWrapper<Plan> planWrapper = new QueryWrapper<>();
            planWrapper.eq("semester_id", addPlanQuery.getSemesterId());
            planWrapper.ne("status", Constant.SYS_ZERO);
            planWrapper.eq("recruit_school_id",loginUser.getCurrentSchoolId());
            Integer integer = baseMapper.selectCount(planWrapper);
            if (integer > 0) {
                return Result.error(CodeMsg.YEAR_OR_SEMESTER_EXIST);
            }
            BeanUtils.copyProperties(addPlanQuery, plan);
            plan.setCreateTime(now);
            plan.setStatus(Constant.SYS_ONE);
            plan.setIsDefaulted(Constant.SYS_ZERO);
            baseMapper.insert(plan);
            //新增计划时，将招生简介和招生学校信息初始化
            PlanSchoolIntroduction planSchoolIntroduction = new PlanSchoolIntroduction();
            planSchoolIntroduction.setPlanId(plan.getPlanId());
            planSchoolIntroduction.setIsDeleted(Constant.SYS_ONE);
            planSchoolIntroductionService.save(planSchoolIntroduction);
            PlanRecruitInfo planRecruitInfo = new PlanRecruitInfo();
            planRecruitInfo.setPlanId(plan.getPlanId());
            planRecruitInfo.setIsDeleted(Constant.SYS_ONE);
            planRecruitInfoService.save(planRecruitInfo);
            //调用新增原院系接口，添加院系信息
            AddCollegeQuery addCollegeQuery = new AddCollegeQuery();
            addCollegeQuery.setPlanId(plan.getPlanId());
            addCollegeQuery.setRecruitSchoolId(loginUser.getCurrentSchoolId());
            addCollegeQuery.setCreatorId(loginUser.getApiInfo().getUserId());
            addCollegeQuery.setCreatorName(loginUser.getApiInfo().getUserName());
            DepartInfoResult map = apiRequest.departInfo(loginUser.getApiInfo().getSchoolId().toString());
            // List<TermCollegeVO> termCollegeVOS = JSON.parseArray(map.get("data").toString(), TermCollegeVO.class);
            collegeService.addCollegeInfo(addCollegeQuery, map.getData());
        } catch (Exception e) {
            throw new MyExcption(CodeMsg.DATA_INSERT_ERROR);
        }
        return Result.success(plan.getPlanId().toString());
    }

    @Override
    public PlanVO queryPlanInfo(Long planId) {
        if (null == planId) {
            return null;
        }
        PlanVO planVO = planMapper.queryPlanInfo(planId);
        if (null == planVO) {
            return null;
        }
        return planVO;
    }

    @Override
    public Result<PlanVO> queryDefaultPlanInfo(Long schoolId) {

        PlanIDAndNameVO planIDAndNameVO = baseMapper.queryDefaultPlanInfo(schoolId);
        if (null == planIDAndNameVO) {
            return Result.success();
        }
        PlanVO planVO = new PlanVO();
        BeanUtils.copyProperties(planIDAndNameVO, planVO);
        return Result.success(planVO);
    }


    @Override
    public Result<PlanVO> queryDefaultPlanInfoWeChat(Long schoolId) {

        PlanIDAndNameVO planIDAndNameVO = baseMapper.queryDefaultPlanInfoWeChat(schoolId);
        if (null == planIDAndNameVO) {
            return Result.success();
        }
        PlanVO planVO = new PlanVO();
        BeanUtils.copyProperties(planIDAndNameVO, planVO);
        return Result.success(planVO);
    }


    @Override
    public List<Plan> queryAllPublishPlan(Long schoolId) {
        log.debug("queryDefaultPlanInfo" + schoolId);
        QueryWrapper<Plan> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", Constant.SYS_TWO);
        queryWrapper.eq("recruit_school_id", schoolId);
        queryWrapper.orderByDesc("semester_start");
        List<Plan> plans = baseMapper.selectList(queryWrapper);
        if (plans.size()==0) {
            return null;
        }
        return plans;
    }

    @Override
    public List<Plan> queryAllFinishedPlan(Long schoolId) {
        QueryWrapper<Plan> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("STATUS",Constant.SYS_TWO,Constant.SYS_THREE);
        queryWrapper.eq("recruit_school_id", schoolId);
        queryWrapper.orderByDesc("semester_start");
        List<Plan> plans = baseMapper.selectList(queryWrapper);
        if (plans.size()==0) {
            return null;
        }
        return plans;
    }

    @Override
    public PlanIDAndNameVO queryDefaultPlanInfoPlanIDAndNameVO(Long recruitSchoolId) {
        return baseMapper.queryDefaultPlanInfo(recruitSchoolId);
    }

    @Override
    public PlanVO queryDefaultPlanInfoBySchool(Long schoolId) {
        PlanIDAndNameVO planIDAndNameVO = baseMapper.queryDefaultPlanInfo(schoolId);
        if (null == planIDAndNameVO) {
            return null;
        }
        PlanVO planVO = new PlanVO();
        BeanUtils.copyProperties(planIDAndNameVO, planVO);
        return planVO;
    }

    @Override
    public Result<PlanLoginVO> queryDefaultPlanLogin(Long schoolId) {
        PlanIDAndNameVO planIDAndNameVO = baseMapper.queryDefaultPlanInfo(schoolId);
        if (null == planIDAndNameVO) {
            return Result.success();
        }
        PlanLoginVO planLoginVO = new PlanLoginVO();
        BeanUtils.copyProperties(planIDAndNameVO, planLoginVO);
        return Result.success(planLoginVO);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result updateStatus(UpdatePlanQuery updatePlanQuery,LoginUser loginUser) {
        LocalDateTime now = LocalDateTime.now();
        Plan queryPlan = baseMapper.selectById(updatePlanQuery.getPlanId());
        //点击发布时，如果招生简介和学校简介没有填写，就不能发布
        if(updatePlanQuery.getStatus() == 2){
            QueryWrapper<PlanRecruitInfo> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("plan_id",queryPlan.getPlanId());
            PlanRecruitInfo p = planRecruitInfoService.getOne(queryWrapper);
            QueryWrapper<PlanSchoolIntroduction> introductionQueryWrapper =new QueryWrapper<>();
            introductionQueryWrapper.eq("plan_id",queryPlan.getPlanId());
            PlanSchoolIntroduction one = planSchoolIntroductionService.getOne(introductionQueryWrapper);
            if(p.getRecruitmentImage()==null || p.getRecruitTarget()==null || p.getAttention() == null || p.getRegister() == null || p.getPlanDescription() == null
                    || one.getRecruitSchoolLogo() == null || one.getRecruitSchoolName() == null || one.getPrincipal() == null || one.getTelephoneNumber() == null || one.getAddress() == null || one.getDescription() == null){
                return Result.error(CodeMsg.PUBLISH_STATUS);
            }

            //判断计划是否有负责人，如果没有就不能发布
            String s = baseMapper.queryChargerIdByPlanId(updatePlanQuery.getPlanId());
            if(null == s||"".equals(s)){
                return Result.error(CodeMsg.PUBLISH_STATUS_CHARGER_ID);
            }
            //添加任务
            taskService.addPlanTask(queryPlan);
            taskService.planTaskCreate();
        }
        //如果是已经发布的状态，就不允许删除
        if (queryPlan.getStatus() == 2) {
            return Result.error(CodeMsg.PLAN_NOT_DELETE);
        }
        updatePlanQuery.setPublisherId(loginUser.getApiInfo().getUserId());
        updatePlanQuery.setPublisherName(loginUser.getApiInfo().getUserName());
        Plan plan = new Plan();
        BeanUtils.copyProperties(updatePlanQuery, plan);
        plan.setPublishTime(now);
        int i = baseMapper.updateById(plan);

        if (i > 0) {
            return Result.success();
        } else {
            return Result.error(CodeMsg.DATA_UPDATE_ERROR);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean updateStatusEnd(UpdatePlanQuery updatePlanQuery, LoginUser loginUser) {
        Plan plan = new Plan();
        BeanUtils.copyProperties(updatePlanQuery, plan);

        //点击完成按钮，发送入学通知书
        taskReceiverService.sendAdmission(loginUser.getCurrentSchoolId(),loginUser.getCurrentSemesterId(),loginUser.getApiInfo().getUserId(),loginUser.getApiInfo().getUserName());

        int i = baseMapper.updateById(plan);
        return i > 0;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result updateIsDefaulted(UpdatePlanIsDefaultecQuery updatePlanQuery, LoginUser loginUser) {
        //默认状态只能有一条数据，小于等于1，不能大于1
        QueryWrapper<Plan> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_defaulted", Constant.SYS_ONE);
        queryWrapper.eq("recruit_school_id",loginUser.getCurrentSchoolId());
        List<Plan> plans = baseMapper.selectList(queryWrapper);
        for (Plan planItem : plans) {
            planItem.setIsDefaulted(Constant.SYS_ZERO);
            baseMapper.updateById(planItem);
        }
        Plan plan = new Plan();
        BeanUtils.copyProperties(updatePlanQuery, plan);
        int i = baseMapper.updateById(plan);

        if (i > 0) {
            return Result.success();
        } else {
            return Result.error(CodeMsg.DATA_UPDATE_ERROR);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result updatePlanInfo(UpdatePlanInfoQuery updatePlanInfoQuery,LoginUser loginUser) {
        if (null == updatePlanInfoQuery) {
            return Result.error(CodeMsg.PARAMETER_ISNULL);
        }
        updatePlanInfoQuery.setModifierId(loginUser.getApiInfo().getUserId());
        Plan plan = baseMapper.selectById(updatePlanInfoQuery.getPlanId());
        if (null == plan || plan.getStatus() == Constant.SYS_ZERO) {
            return Result.error(CodeMsg.PLAN_NOT_EXIST);
        }

        //编辑时查看更新的学期目标是否存在
//        Long schoolId=loginUser.getApiInfo().getSchoolId();
//        Target target = targetService.checkTarget(schoolId, updatePlanInfoQuery.getSemesterId());
//        if (null == target) {
//            return Result.error(CodeMsg.TARGET_NOT_EXIST);
//        }

        //更新时学期不能重复
        Plan updatePlan = new Plan();
        if (plan.getSemesterId().equals(updatePlanInfoQuery.getSemesterId())) {
            BeanUtils.copyProperties(updatePlanInfoQuery, updatePlan);
            int i = baseMapper.updateById(updatePlan);
            if (i > 0) {
                return Result.success();
            } else {
                return Result.error(CodeMsg.DATA_UPDATE_ERROR);
            }
        } else {
            QueryWrapper<Plan> planWrapper = new QueryWrapper<>();
            planWrapper.eq("semester_id", updatePlanInfoQuery.getSemesterId());
            planWrapper.ne("status", Constant.SYS_ZERO);
            planWrapper.eq("recruit_school_id",loginUser.getCurrentSchoolId());
            Integer integer = baseMapper.selectCount(planWrapper);
            if (integer > 0) {
                return Result.error(CodeMsg.YEAR_OR_SEMESTER_EXIST);
            }else{
                BeanUtils.copyProperties(updatePlanInfoQuery, updatePlan);
                int i = baseMapper.updateById(updatePlan);
                if (i > 0) {
                    return Result.success();
                } else {
                    return Result.error(CodeMsg.DATA_UPDATE_ERROR);
                }
            }
        }

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result addCopyPlanInfo(AddCopyPlanQuery addCopyPlanQuery,LoginUser loginUser) {
        LocalDateTime now = LocalDateTime.now();
        if (null == addCopyPlanQuery) {
            return Result.error(CodeMsg.PARAMETER_ISNULL);
        }
        addCopyPlanQuery.setCreatorId(loginUser.getApiInfo().getUserId());
        addCopyPlanQuery.setCreatorName(loginUser.getApiInfo().getUserName());
        //根据copyId查询计划信息
        Plan queryPlan = getById(addCopyPlanQuery.getCopyId());
        //判断是否能查询到招生计划信息
        if (null == queryPlan) {
            return Result.error(CodeMsg.PLAN_NOT_EXIST);
        }

        try {
            //判断学期是否重复
            QueryWrapper<Plan> planWrapper = new QueryWrapper<>();
            planWrapper.eq("semester_id", addCopyPlanQuery.getSemesterId());
            planWrapper.ne("status", Constant.SYS_ZERO);
            planWrapper.eq("recruit_school_id",loginUser.getCurrentSchoolId());
            Integer integer = baseMapper.selectCount(planWrapper);
            if (integer > 0) {
                return Result.error(CodeMsg.YEAR_OR_SEMESTER_EXIST);
            }

            //复制招生计划基本信息
            queryPlan.setPlanId(null);
            queryPlan.setSemester(addCopyPlanQuery.getSemester());
            queryPlan.setCreateTime(now);
            queryPlan.setSemesterId(addCopyPlanQuery.getSemesterId());
            queryPlan.setCopyId(addCopyPlanQuery.getCopyId());
            queryPlan.setIsDefaulted(Constant.SYS_ZERO);
            queryPlan.setStatus(Constant.SYS_ONE);
            queryPlan.setChargerId(null);
            baseMapper.insert(queryPlan);

            //复制招生学校信息
            QueryWrapper<PlanSchoolIntroduction> recruitSchoolWrapper = new QueryWrapper<>();
            recruitSchoolWrapper.eq("plan_id", addCopyPlanQuery.getCopyId());
            PlanSchoolIntroduction introductionServiceOne = planSchoolIntroductionService.getOne(recruitSchoolWrapper);
            introductionServiceOne.setPlanId(queryPlan.getPlanId());
            introductionServiceOne.setPlanSchoolIntroductionId(null);
            planSchoolIntroductionService.save(introductionServiceOne);

            //复制招生简介信息
            QueryWrapper<PlanRecruitInfo> planRecruitInfoWrapper = new QueryWrapper<>();
            planRecruitInfoWrapper.eq("plan_id", addCopyPlanQuery.getCopyId());
            PlanRecruitInfo planRecruitInfo = planRecruitInfoService.getOne(planRecruitInfoWrapper);
            planRecruitInfo.setPlanId(queryPlan.getPlanId());
            planRecruitInfo.setPlanRecruitInfoId(null);
            planRecruitInfoService.save(planRecruitInfo);

            //复制招生院系
            QueryWrapper<College> collegeQueryWrapper = new QueryWrapper<>();
            collegeQueryWrapper.eq("plan_id", addCopyPlanQuery.getCopyId());
            List<College> collegeList = collegeService.list(collegeQueryWrapper);
            if (collegeList.size() == 0) {
                return Result.error(CodeMsg.COLLEGE_INFO_NOT_EXIST);
            }


            for (College college : collegeList) {
                College newCollege = new College();
                BeanUtils.copyProperties(college, newCollege);
                newCollege.setCollegeId(null);
                newCollege.setPlanId(queryPlan.getPlanId());
                collegeService.save(newCollege);

                QueryWrapper<Major> majorQueryWrapper = new QueryWrapper<>();
                majorQueryWrapper.eq("college_id", college.getCollegeId());
                List<Major> majorList = majorService.list(majorQueryWrapper);
                for (Major major : majorList) {
                    major.setMajorId(null);
                    major.setCollegeId(newCollege.getCollegeId());
                    majorService.save(major);
                }
            }

        } catch (Exception e) {
            return Result.error(CodeMsg.DATA_COPY_ERROR);
        }
        return Result.success(queryPlan.getPlanId().toString());
    }

    @Override
    public List<ChargerVO> queryChargerBySemesterId(Long semesterId,Long recruitSchoolId) {
        return baseMapper.queryChargerBySemesterId(semesterId,recruitSchoolId);
    }

    @Override
    public String queryChargerIdByPlanId(Long planId) {
        return baseMapper.queryChargerIdByPlanId(planId);
    }
}
