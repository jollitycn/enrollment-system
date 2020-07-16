package com.educiot.recruit.server.app.controller.preach;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.educiot.recruit.common.base.Result;
import com.educiot.recruit.data.entity.ext.PreachSummaryExt;
import com.educiot.recruit.data.entity.query.preach.PreachSummaryAddQuery;
import com.educiot.recruit.data.entity.query.preach.PreachSummaryPageQuery;
import com.educiot.recruit.data.service.preach.IPreachSummaryService;
import com.educiot.recruit.server.app.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 宣讲总结表  前端控制器
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
@Api(tags = {"宣讲计划反馈总结"}, value = "PreachSummaryController")
@RestController
@RequestMapping("/preach-summary")
public class PreachSummaryController extends BaseController {

    @Resource
    private IPreachSummaryService preachSummaryService;

    @PostMapping("/page")
    @ApiOperation(value = "获取宣讲计划总结列表", response = PreachSummaryExt.class)
    public Result<?> page(@RequestBody PreachSummaryPageQuery query) {
        IPage<PreachSummaryExt> result = preachSummaryService.listPage(query);
        return Result.success(result);
    }

//    @PostMapping("/feedBackCount")
//    @ApiOperation(value = "获取演讲计划反馈评分列表",response = PreachSummaryFeedBackCountVO.class)
//    public Result<?> feedBackCount(@RequestBody PreachSummaryQuery query) {
//        List<PreachSummaryFeedBackCountVO> result = preachSummaryService.feedBackCount(query);
//        return Result.success(result);
//    }

    @PutMapping("/add")
    @ApiOperation("新增宣讲总结")
    public Result<?> add(@RequestBody PreachSummaryAddQuery query) {
        return preachSummaryService.add(getLoginUser().getApiInfo().getUserId(), getLoginUser().getApiInfo().getUserName(), query);
    }
}
