package com.educiot.recruit.server.wechat.controller.preach;

import com.educiot.recruit.common.base.Result;
import com.educiot.recruit.data.entity.preach.PreachFeedback;
import com.educiot.recruit.data.entity.query.preach.PreachFeedbackAddQuery;
import com.educiot.recruit.data.entity.vo.preach.PreachFeedbackVO;
import com.educiot.recruit.data.service.preach.IPreachFeedbackService;
import com.educiot.recruit.server.wechat.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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

    @PostMapping(METHOD_ADD)
    @ApiOperation(value = METHOD_ADD_NAME, response = PreachFeedbackVO.class)
    public Result<?> add(@RequestBody PreachFeedbackAddQuery query) {
        PreachFeedback result = preachFeedbackService.add(getLoginVO().getStudentPublicRelationId(), getLoginVO().getCurrentStudent().getStudentInfo(), getLoginVO().getCurrentStudent().getStudentName(), query);
        return Result.success(result);
    }

//    @PutMapping("detailByPreachPlanId/{id}")
//    @ApiOperation(value = "当前学生宣讲计划对应的反馈详情", response = PreachFeedbackVO.class)
//    public Result<?> detailByPreachPlanId(@PathVariable Long id) {
//        LoginUser loginUser = getLoginUser();
//        PreachFeedbackVO result = preachFeedbackService.detailByPreachPlanId(loginUser.getApiInfo().getUserId(), id);
//        return Result.success(result);
//    }
}
