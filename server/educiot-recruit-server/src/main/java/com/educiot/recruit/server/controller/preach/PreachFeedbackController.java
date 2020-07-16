package com.educiot.recruit.server.controller.preach;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.educiot.common.base.Result;
import com.educiot.recruit.data.entity.query.preach.PreachFeedbackPageQuery;
import com.educiot.recruit.data.entity.query.preach.PreachFeedbackQuery;
import com.educiot.recruit.data.entity.vo.preach.PreachFeedbackCountVO;
import com.educiot.recruit.data.entity.vo.preach.PreachFeedbackVO;
import com.educiot.recruit.data.service.preach.IPreachFeedbackService;
import com.educiot.recruit.server.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 宣讲反馈表 前端控制器
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
@RequestMapping("/preach-feedback")
@Api(tags = {"宣讲计划反馈"}, value = "PreachFeedbackController")
@RestController
public class PreachFeedbackController extends BaseController {
    @Resource
    private IPreachFeedbackService preachFeedbackService;

    @PostMapping("/page")
    @ApiOperation(value = "获取演讲计划反馈列表",response = PreachFeedbackVO.class)
    public Result<?> page(@RequestBody PreachFeedbackPageQuery query) {
        IPage<PreachFeedbackVO> result = preachFeedbackService.listPage(query);
        return Result.success(result);
    }

    @PostMapping("/feedBackCount")
    @ApiOperation(value = "获取演讲计划反馈评分列表",response = PreachFeedbackCountVO.class)
    public Result<?> feedBackCount(@RequestBody PreachFeedbackQuery query) {
        List<PreachFeedbackCountVO> result = preachFeedbackService.feedBackCount(query);
        return Result.success(result);
    }
}
