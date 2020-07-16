package com.educiot.recruit.server.wechat.controller.student;


import com.educiot.common.base.Result;
import com.educiot.recruit.data.entity.query.AddRequestQuery;
import com.educiot.recruit.data.entity.query.RequestQuery;
import com.educiot.recruit.data.service.student.IRequestService;
import com.educiot.recruit.server.wechat.common.BaseController;
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
 * @author Mr.Ai
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
        addRequestQuery.setStudentInfo(getLoginVO().getCurrentStudent().getStudentInfo());
        addRequestQuery.setStudentPublicRelationId(getLoginVO().getStudentPublicRelationId());
        addRequestQuery.setSemesterId(getLoginVO().getPlan().getSemesterId());
        addRequestQuery.setRecruitSchoolId(getLoginVO().getCurrentRecruitSchool().getRecruitSchoolId());
        return requestService.saveOrUpdateRequestInfo(addRequestQuery);
    }

    @GetMapping("/queryRequestInfo")
    @ApiOperation("查询学生的已处理和未处理的诉求信息")
    public Result queryRequestInfo(@Valid RequestQuery requestQuery) {
        requestQuery.setStudentInfo(getLoginVO().getCurrentStudent().getStudentInfo());
        return requestService.queryRequestInfo(requestQuery);
    }


}
