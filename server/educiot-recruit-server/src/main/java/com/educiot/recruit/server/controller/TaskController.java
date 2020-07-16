package com.educiot.recruit.server.controller;


import com.educiot.common.base.Result;
import com.educiot.recruit.server.common.BaseController;
import com.educiot.recruit.server.quartz.TaskSchedule;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
public class TaskController extends BaseController {

    @Resource
    private TaskSchedule taskSchedule;

    @GetMapping("/test")
    @ApiModelProperty(value = "test")
    public Result<?> test() {
        taskSchedule.planTaskCreate();
        return Result.success();
    }

}
