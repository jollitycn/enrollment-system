package com.educiot.recruit.server.controller.plan;

import com.educiot.recruit.data.service.ITargetDecompositionService;
import com.educiot.common.base.Result;
import com.educiot.recruit.server.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @program: server
 * @description: 招生目标分解扩展 前端控制器
 * @author: Mr.AI
 * @create: 2020-04-15
 **/

@RestController
@RequestMapping("/target-decomposition-plan")
@Api(tags = {"招生目标分解"}, value = "TargetDecompositionPlanController")
public class TargetDecompositionPlanController extends BaseController {

    @Resource
    private ITargetDecompositionService targetDecompositionService;

    @GetMapping("/queryTargetAmountByNodeId")
    @ApiOperation("查询目标人数")
    public Result queryTargetAmountByNodeId(@Valid String nodeId){
        return targetDecompositionService.queryTargetAmountByNodeId(nodeId);
    }
}
