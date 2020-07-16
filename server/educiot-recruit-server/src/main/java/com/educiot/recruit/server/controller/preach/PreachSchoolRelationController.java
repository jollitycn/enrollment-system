package com.educiot.recruit.server.controller.preach;


import com.educiot.recruit.data.service.preach.IPreachSchoolRelationService;
import com.educiot.recruit.common.base.Result;
import com.educiot.recruit.server.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 宣讲生源学校关联表 前端控制器
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
@RestController
@RequestMapping("/preach-school-relation")
@Api(tags = {"宣讲计划面向学校"}, value = "PreachSchoolRelationController")
public class PreachSchoolRelationController extends BaseController {

    @Resource
    private IPreachSchoolRelationService preachSchoolRelationService;

    @GetMapping("/listByPreachPlanId/{id}")
    @ApiOperation(value = "获取宣讲计划面向学校列表")
    public Result<?> listByPreachPlanId(@PathVariable Long id) {
        List<String> result = preachSchoolRelationService.listByPreachPlanId(id);
        return Result.success(result);
    }
}
