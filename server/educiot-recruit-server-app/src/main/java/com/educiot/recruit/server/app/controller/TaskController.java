package com.educiot.recruit.server.app.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.educiot.recruit.common.base.CodeMsg;
import com.educiot.recruit.common.base.Result;
import com.educiot.recruit.common.constant.Constant;
import com.educiot.recruit.data.entity.TaskReceiver;
import com.educiot.recruit.data.entity.query.ListTaskPageQuery;
import com.educiot.recruit.data.entity.vo.TaskListVO;
import com.educiot.recruit.data.service.ITaskReceiverService;
import com.educiot.recruit.data.service.ITaskService;
import com.educiot.recruit.server.app.common.BaseController;
import com.educiot.recruit.server.app.common.LoginUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 招生任务表 前端控制器
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-13
 */
@RestController
@RequestMapping("/task")
@Api(tags = {"任务模块"}, value = "任务模块")
public class TaskController extends BaseController {

    @Resource
    private ITaskService taskService;

    @Resource
    private ITaskReceiverService taskReceiverService;

    @GetMapping("/getTaskList")
    @ApiOperation(value = "获取当前登录用户的任务列表", response = TaskListVO.class)
    public Result<?> getTaskList(ListTaskPageQuery listTaskPageQuery) {

        LoginUser loginUser = getLoginUser();

        Long userId = loginUser.getApiInfo().getUserId();

        Long semesterId = loginUser.getCurrentSemesterId();

        Long schoolId = loginUser.getCurrentSchoolId();

        IPage<TaskListVO> taskList = taskService.getTaskList(userId, semesterId, schoolId, listTaskPageQuery);

        return Result.success(taskList);
    }

    @PostMapping("/closeTask/{taskReceiverId}")
    @ApiOperation(value = "关闭任务")
    public Result<?> closeTask(@PathVariable Long taskReceiverId) {

        boolean status = taskService.closeTask(taskReceiverId);

        if (status) {
            return Result.success();
        } else {
            return Result.error(CodeMsg.DATA_UPDATE_ERROR);
        }
    }

    @PostMapping("/jumpPage/{taskReceiverId}")
    @ApiOperation(value = "跳转已读")
    public Result<?> jumpPage(@PathVariable Long taskReceiverId) {

        TaskReceiver taskReceiver = taskReceiverService.getById(taskReceiverId);
        taskReceiver.setReadStatus(Constant.SYS_ONE);

        boolean status = taskReceiverService.updateById(taskReceiver);

        if (status) {
            return Result.success();
        } else {
            return Result.error(CodeMsg.DATA_UPDATE_ERROR);
        }
    }


}
