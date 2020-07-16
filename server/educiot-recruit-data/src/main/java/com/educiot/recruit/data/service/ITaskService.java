package com.educiot.recruit.data.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.educiot.recruit.data.entity.Plan;
import com.educiot.recruit.data.entity.Task;
import com.educiot.recruit.data.entity.preach.PreachPlan;
import com.educiot.recruit.data.entity.query.ListTaskPageQuery;
import com.educiot.recruit.data.entity.vo.TaskListVO;

/**
 * <p>
 * 招生任务表 服务类
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-13
 */
public interface ITaskService extends IService<Task> {

    /**
     * 获取任务列表
     * @param userId
     * @param semesterId
     * @param schoolId
     * @param listTaskPageQuery
     * @return IPage<TaskListVO>
     */
    IPage<TaskListVO> getTaskList(Long userId, Long semesterId, Long schoolId, ListTaskPageQuery listTaskPageQuery);

    /**
     * 关闭任务
     * @param taskReceiverId
     * @return true 修改成功
     */
    boolean closeTask(Long taskReceiverId);

    /**
     * 添加任务
     * @param studentPublicRelationId 学生公关id
     * @param type 类型：0：诉求，1：咨询
     * @param semesterId 学期id
     * @param relatedId 相关ID
     * @return true 添加成功
     */
    boolean addTaskFromStudent(Long studentPublicRelationId, Integer type, Long semesterId, Long relatedId);

    /**
     * 完成任务
     * @param relatedId 相关id
     * @return true 修改成功
     */
    boolean finishTask(Long relatedId);

    /**
     * 添加计划任务
     *
     * @return true 修改成功
     */
    boolean addPlanTask(Plan plan);

    /**
     * 判断宣讲日期是否需要发送任务
     * @param preachPlan
     * @return true 流程成功
     */
    boolean checkPreachDate(PreachPlan preachPlan);

    void planTaskCreate();
}
