package com.educiot.recruit.server.controller.preach;


import com.educiot.recruit.data.service.preach.IPreachAssistantRelationService;
import com.educiot.recruit.common.base.Result;
import com.educiot.recruit.server.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 宣讲协助人关联表 前端控制器
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-13
 */
@RestController
@RequestMapping("/preach-assistant-relation")
@Api(tags = {"宣讲计划协助人"}, value = "PreachAssistantRelationController")
public class PreachAssistantRelationController extends BaseController {

    @Resource
    private IPreachAssistantRelationService preachAssistantRelationService ;

    @GetMapping("/listByPreachPlanId/{id}")
    @ApiOperation(value = "获取宣讲计划协助人列表")
    public Result<?> listByPreachPlanId(@PathVariable Long id) {
        List<String> result = preachAssistantRelationService.listByPreachPlanId(id);
        return Result.success(result);
    }
}
