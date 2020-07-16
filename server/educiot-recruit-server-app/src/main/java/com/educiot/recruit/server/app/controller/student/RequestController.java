package com.educiot.recruit.server.app.controller.student;


import com.educiot.recruit.common.base.Result;
import com.educiot.recruit.data.entity.query.AddRequestQuery;
import com.educiot.recruit.data.entity.query.RequestQuery;
import com.educiot.recruit.data.service.student.IRequestService;
import com.educiot.recruit.server.app.common.BaseController;
import com.educiot.recruit.server.app.common.LoginUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * <p>
 * 诉求表 前端控制器
 * </p>
 *
 * @author Mr.AI
 * @since 2020-04-13
 */
@RestController
@RequestMapping("/request")
@Api(tags = {"诉求信息"}, value = "RequestController")
public class RequestController extends BaseController {

    @Resource
    private IRequestService requestService;

    @PostMapping("/saveOrUpdateRequestInfo")
    @ApiOperation("添加或修改学生诉求信息")
    public Result saveOrUpdateRequestInfo(@Valid @RequestBody AddRequestQuery addRequestQuery) {
        LoginUser loginUser = getLoginUser();
        addRequestQuery.setReplyId(loginUser.getApiInfo().getUserId());
        addRequestQuery.setReplyName(loginUser.getApiInfo().getUserName());
        return requestService.saveOrUpdateRequestInfo(addRequestQuery);
    }

//    @GetMapping("/queryRequestInfoByRecruitSchoolId")
//    @ApiOperation("根据招生学校ID查询所有诉求信息")
//    public Result queryRequestInfoByRecruitSchoolId(@Valid RequestQuery requestQuery) {
//        LoginUser loginUser = getLoginUser();
//        requestQuery.setRecruitSchoolId(loginUser.getCurrentSchoolId());
//        return requestService.queryRequestInfo(requestQuery);
//    }

    @GetMapping("/queryRequestInfo")
    @ApiOperation("根据招生学校ID查询学生的已处理和未处理的诉求信息")
    public Result queryRequestInfo(@Valid RequestQuery requestQuery) {
        LoginUser loginUser = getLoginUser();
        requestQuery.setCreatorId(loginUser.getApiInfo().getUserId());
        requestQuery.setRecruitSchoolId(loginUser.getCurrentSchoolId());
        return requestService.queryRequestInfo(requestQuery);
    }

    @GetMapping("/updateRequestStatus/{requestId}")
    @ApiOperation("修改诉求信息状态")
    public Result updateRequestStatus(@Valid @PathVariable("requestId") Long requestId) {
        return Result.success(requestService.updateRequestStatus(requestId));
    }

}
