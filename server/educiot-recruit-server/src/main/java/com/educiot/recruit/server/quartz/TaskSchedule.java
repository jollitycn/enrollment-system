package com.educiot.recruit.server.quartz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.educiot.common.constant.Constant;
import com.educiot.recruit.data.entity.ConsultationRegister;
import com.educiot.recruit.data.entity.Task;
import com.educiot.recruit.data.entity.TaskReceiver;
import com.educiot.recruit.data.entity.preach.PreachPlan;
import com.educiot.recruit.data.service.IChargerService;
import com.educiot.recruit.data.service.IConsultationRegisterService;
import com.educiot.recruit.data.service.ITaskReceiverService;
import com.educiot.recruit.data.service.ITaskService;
import com.educiot.recruit.data.service.preach.IPreachPlanService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author liuhao
 * @program educiot-recruit
 * @description：负责人任务定时模块
 * @date Create in 2020/5/7
 */
@Component
@EnableScheduling
public class TaskSchedule {

    @Resource
    private IPreachPlanService preachPlanService;

    @Resource
    private ITaskService taskService;

    @Resource
    private ITaskReceiverService taskReceiverService;

    @Resource
    private IConsultationRegisterService consultationRegisterService;

    @Resource
    private IChargerService chargerService;

    /**
     * 为明天有宣讲计划的主讲人创建任务
     */
    @Scheduled(cron = "0 0 1 * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void preachTask() {

        LocalDate tomorrow = LocalDate.now().plusDays(1L);
        String start = tomorrow.toString() + " 00:00:00";
        String end = tomorrow.toString() + " 23:59:59";
        QueryWrapper<PreachPlan> wrapper = new QueryWrapper<>();
        wrapper.eq("status", Constant.SYS_TWO);
        wrapper.between("preach_time", start, end);
        List<PreachPlan> preachPlanList = preachPlanService.list(wrapper);

        for (PreachPlan preachPlan : preachPlanList) {
            Task task = new Task();
            task.setRelatedId(preachPlan.getPreachPlanId());
            task.setJumpPage("/preach-plan/pageAllPreachPlanByUserId");
            task.setContent("preach_schdule");
            task.setTaskType(Constant.SYS_TWO);
            task.setSemesterId(preachPlan.getSemesterId());
            task.setCreateTime(LocalDateTime.now());
            task.setReportType(Constant.SYS_ZERO);
            task.setCreatorId(preachPlan.getCreatorId());
            task.setCreatorName(preachPlan.getCreatorName());

            taskService.save(task);

            TaskReceiver taskReceiver = new TaskReceiver();
            taskReceiver.setReceiverId(preachPlan.getSpeakerId());
            taskReceiver.setSemesterId(preachPlan.getSemesterId());
            taskReceiver.setTaskId(task.getTaskId());
            taskReceiver.setCreateTime(LocalDateTime.now());
            taskReceiverService.save(taskReceiver);
        }
    }

    /**
     * 关闭完成的咨询任务
     */
    @Scheduled(cron = "0 0 1 * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void closeConsultationTask() {

        LocalDate yesterday = LocalDate.now().minusDays(1L);

        //获取未关闭的咨询任务
        QueryWrapper<Task> wrapper = new QueryWrapper<>();
        wrapper.eq("task_type", Constant.SYS_FOUR);
        wrapper.eq("status", Constant.SYS_ZERO);
        wrapper.eq("type", Constant.SYS_ZERO);
        List<Task> taskList = taskService.list(wrapper);

        for (Task task : taskList) {
            ConsultationRegister consultationRegister = consultationRegisterService.getById(task.getRelatedId());
            //判断咨询日期是否是昨天
            if (consultationRegister.getConsultationDate().isEqual(yesterday)) {
                //如果是昨天就完成任务
                task.setStatus(Constant.SYS_ONE);
                taskService.updateById(task);
                QueryWrapper<TaskReceiver> taskReceiverQueryWrapper = new QueryWrapper<>();
                taskReceiverQueryWrapper.eq("task_id", task.getTaskId());
                TaskReceiver taskReceiver = taskReceiverService.getOne(taskReceiverQueryWrapper);
                taskReceiver.setStatus(Constant.SYS_ONE);
                taskReceiverService.updateById(taskReceiver);
            }
        }
    }

    /**
     * 计划任务触发
     */
    @Scheduled(cron = "0 0 1 * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void planTaskCreate() {
        taskService.planTaskCreate();
    }

}
