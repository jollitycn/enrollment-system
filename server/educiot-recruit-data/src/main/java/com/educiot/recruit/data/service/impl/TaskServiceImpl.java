package com.educiot.recruit.data.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.educiot.recruit.common.constant.Constant;
import com.educiot.recruit.data.entity.*;
import com.educiot.recruit.data.entity.preach.PreachPlan;
import com.educiot.recruit.data.entity.query.ListTaskPageQuery;
import com.educiot.recruit.data.entity.vo.TaskListVO;
import com.educiot.recruit.data.mapper.TaskMapper;
import com.educiot.recruit.data.service.IChargerService;
import com.educiot.recruit.data.service.IPlanService;
import com.educiot.recruit.data.service.ITaskReceiverService;
import com.educiot.recruit.data.service.ITaskService;
import com.educiot.recruit.data.service.student.IStudentInfoService;
import com.educiot.recruit.data.service.student.IStudentPublicRelationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 招生任务表 服务实现类
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-13
 */
@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements ITaskService {

    @Resource
    private IStudentPublicRelationService studentPublicRelationService;

    @Resource
    private IChargerService chargerService;

    @Resource
    private ITaskReceiverService taskReceiverService;

    @Resource
    private IStudentInfoService studentInfoService;

    @Resource
    private IPlanService planService;

    /**
     * 获取任务列表
     * @param userId
     * @param semesterId
     * @param schoolId
     * @param listTaskPageQuery
     * @return IPage<TaskListVO>
     */
    @Override
    public IPage<TaskListVO> getTaskList(Long userId, Long semesterId, Long schoolId, ListTaskPageQuery listTaskPageQuery) {

        Page<TaskListVO> page = new Page<>(listTaskPageQuery.getPageNum(), listTaskPageQuery.getPageSize());

        return baseMapper.getTaskList(page, userId, semesterId, schoolId, listTaskPageQuery.getStatus());
    }

    /**
     * 关闭任务
     * @param taskReceiverId
     * @return true 修改成功
     */
    @Override
    public boolean closeTask(Long taskReceiverId) {

        TaskReceiver taskReceiver = taskReceiverService.getById(taskReceiverId);
        Task task = getById(taskReceiver.getTaskId());

        //如果任务类型是计划任务，只改变接收信息状态
        taskReceiver.setStatus(Constant.SYS_ONE);
        boolean status = taskReceiverService.updateById(taskReceiver);
        //判断任务类型，如果是其他类型，同时改变任务状态
        if (task.getTaskType() == Constant.SYS_TWO || task.getTaskType() == Constant.SYS_THREE || task.getTaskType() == Constant.SYS_FOUR) {
            task.setStatus(Constant.SYS_ONE);
            status = status && updateById(task);
        }
        return status;
    }

    /**
     * 添加任务
     * @param studentPublicRelationId 学生公关id
     * @param type 类型：0：诉求，1：咨询
     * @param semesterId 学期id
     * @param relatedId 相关ID
     * @return true 添加成功
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean addTaskFromStudent(Long studentPublicRelationId, Integer type, Long semesterId, Long relatedId) {

        //查询学生负责人api id
        StudentPublicRelation studentPublicRelation = studentPublicRelationService.getById(studentPublicRelationId);
        Long chargerId = studentPublicRelation.getCreatorId();
//        Charger charger = chargerService.getById(chargerId);

        //添加任务
        Task task = new Task();
        task.setCreateTime(LocalDateTime.now());
        task.setSemesterId(semesterId);
        task.setRelatedId(relatedId);
        task.setReportType(Constant.SYS_ONE);
        task.setCreatorId(studentPublicRelationId);
        //获取学生姓名
        StudentInfo studentInfo = studentInfoService.getById(studentPublicRelation.getStudentInfo());
        task.setCreatorName(studentInfo.getStudentName());

        //根据类型填充不同的任务
        if (type == Constant.SYS_ZERO) {
            //诉求
            task.setTaskType(Constant.SYS_THREE);
            task.setContent("request_deal");
            task.setJumpPage("/request/queryRequestInfoByRecruitSchoolId");
        } else {
            //咨询
            task.setTaskType(Constant.SYS_FOUR);
            task.setContent("consultation_deal");
            task.setJumpPage("/consultation/getConsultationRegisterList");
        }
        boolean status = save(task);

        //存储任务接收人
        TaskReceiver taskReceiver = new TaskReceiver();
        taskReceiver.setCreateTime(task.getCreateTime());
        taskReceiver.setTaskId(task.getTaskId());
        taskReceiver.setSemesterId(semesterId);
        taskReceiver.setReceiverId(chargerId);
        status = status && taskReceiverService.save(taskReceiver);
        return status;
    }

    /**
     * 完成任务
     * @param relatedId 相关id
     * @return true 修改成功
     */
    @Override
    public boolean finishTask(Long relatedId) {

        QueryWrapper<Task> taskQueryWrapper = new QueryWrapper<>();
        taskQueryWrapper.eq("related_id", relatedId);
        List<Task> taskList = list(taskQueryWrapper);
        for (Task task : taskList) {
            task.setStatus(Constant.SYS_ONE);

            QueryWrapper<TaskReceiver> taskReceiverQueryWrapper = new QueryWrapper<>();
            taskReceiverQueryWrapper.eq("task_id", task.getTaskId());
            TaskReceiver taskReceiver = taskReceiverService.getOne(taskReceiverQueryWrapper);
            taskReceiver.setStatus(Constant.SYS_ONE);
            taskReceiverService.updateById(taskReceiver);

        }
        if (taskList.size() == 0) {
            return false;
        } else {
            return updateBatchById(taskList);
        }
    }

    /**
     * 添加计划任务
     * //     * @param semesterId
     *
     * @return true 修改成功
     */
    @Override
    public boolean addPlanTask(Plan plan) {

        plan = planService.getById(plan.getPlanId());
        //判断该学期是否已有计划任务
        QueryWrapper<Task> wrapper = new QueryWrapper<>();
        wrapper.eq("semester_id", plan.getSemesterId());
        wrapper.in("task_type", Constant.SYS_ZERO, Constant.SYS_ONE);
        int count = count(wrapper);
        if (count > 0) {
            List<Task> taskList = list(wrapper);
            for (Task task : taskList) {
                task.setCreatorName(plan.getPublisherName());
                task.setCreatorId(plan.getPublisherId());
            }
            return updateBatchById(taskList);
        } else {
            //如果没有创建两个任务
            //维护学校
            Task schoolTask = new Task();
            schoolTask.setCreateTime(LocalDateTime.now());
            schoolTask.setSemesterId(plan.getSemesterId());
            schoolTask.setTaskType(Constant.SYS_ZERO);
            schoolTask.setContent("maintain_school");
            schoolTask.setJumpPage("/sourceSchool/listCompleteRate");
            schoolTask.setReportType(Constant.SYS_ZERO);
            schoolTask.setCreatorId(plan.getPublisherId());
            schoolTask.setCreatorName(plan.getPublisherName());
            boolean status = save(schoolTask);
            //维护宣讲
            Task preachTask = new Task();
            preachTask.setCreateTime(LocalDateTime.now());
            preachTask.setSemesterId(plan.getSemesterId());
            preachTask.setTaskType(Constant.SYS_ONE);
            preachTask.setContent("maintain_preach");
            preachTask.setJumpPage("/preach-plan/pageAllPreachPlanByUserId");
            preachTask.setReportType(Constant.SYS_ZERO);
            preachTask.setCreatorId(plan.getPublisherId());
            preachTask.setCreatorName(plan.getPublisherName());
            status = status && save(preachTask);
            return status;
        }
    }

    /**
     * 判断宣讲日期是否需要发送任务
     * @param preachPlan
     * @return true 流程成功
     */
    @Override
    public boolean checkPreachDate(PreachPlan preachPlan) {

        //获取宣讲日期
        LocalDate preachDate = preachPlan.getPreachTime().toLocalDate();

        //如果宣讲时间是明天，创建任务
        if (preachDate.isEqual(LocalDate.now().plusDays(1L))) {
            Task task = new Task();
            task.setRelatedId(preachPlan.getPreachPlanId());
            task.setCreatorId(preachPlan.getCreatorId());
            task.setCreatorName(preachPlan.getCreatorName());
            task.setContent("preach_schdule");
            task.setTaskType(Constant.SYS_TWO);
            task.setSemesterId(preachPlan.getSemesterId());
            task.setCreateTime(LocalDateTime.now());
            task.setReportType(Constant.SYS_ZERO);

            boolean status = save(task);

            TaskReceiver taskReceiver = new TaskReceiver();
            taskReceiver.setReceiverId(preachPlan.getSpeakerId());
            taskReceiver.setSemesterId(preachPlan.getSemesterId());
            taskReceiver.setTaskId(task.getTaskId());
            taskReceiver.setCreateTime(LocalDateTime.now());
            return status && taskReceiverService.save(taskReceiver);
        }

        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void planTaskCreate() {
        //获取计划任务列表
        QueryWrapper<Task> planQueryWrapper = new QueryWrapper<>();
        planQueryWrapper.in("task_type", Constant.SYS_ZERO, Constant.SYS_ONE);
        planQueryWrapper.eq("type", Constant.SYS_ZERO);
        List<Task> taskList = list(planQueryWrapper);

        for (Task task : taskList) {
            //获取全部负责人
            List<Charger> chargerList = chargerService.getAllChargerList(task.getSemesterId());
            for (Charger charger : chargerList) {
                //获取是否已有接收信息存在
                QueryWrapper<TaskReceiver> wrapper = new QueryWrapper<>();
                wrapper.eq("task_id", task.getTaskId());
                wrapper.eq("receiver_id", charger.getApiId());
                TaskReceiver taskReceiver = taskReceiverService.getOne(wrapper);
                if (taskReceiver == null) {
                    //如果接收信息为空，创建接收信息
                    TaskReceiver insert = new TaskReceiver();
                    insert.setTaskId(task.getTaskId());
                    insert.setCreateTime(LocalDateTime.now());
                    insert.setSemesterId(task.getSemesterId());
                    insert.setReceiverId(charger.getApiId());
                    taskReceiverService.save(insert);
                }
            }
        }
    }

}
