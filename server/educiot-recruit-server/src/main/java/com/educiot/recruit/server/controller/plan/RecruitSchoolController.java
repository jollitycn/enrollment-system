package com.educiot.recruit.server.controller.plan;


import com.educiot.recruit.data.entity.query.AddRecruitSchoolQuery;
import com.educiot.recruit.data.entity.query.UpdateRecruitSchoolQuery;
import com.educiot.recruit.data.entity.vo.RecruitSchoolVO;
import com.educiot.recruit.data.service.IRecruitSchoolService;
import com.educiot.common.base.Result;
import com.educiot.recruit.server.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 招生学校 前端控制器
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
@RestController
@RequestMapping("/recruit-school")
@Api(tags = {"招生学校信息管理"}, value = "RecruitSchoolController")
public class RecruitSchoolController extends BaseController {

    @Resource
    IRecruitSchoolService recruitSchoolService;

    @PostMapping("/addRecruitSchool")
    @ApiOperation("添加招生学校信息")
    public Result addRecruitSchool(@Valid @RequestBody AddRecruitSchoolQuery addRecruitSchoolQuery) {
        return recruitSchoolService.addRecruiutSchool(addRecruitSchoolQuery);
    }

    @GetMapping("/queryRecruitSchoolInfo")
    @ApiOperation("获取招生学校信息")
    public Result<RecruitSchoolVO> queryRecruitSchoolInfo(@Valid String recruitSchoolId){
        return recruitSchoolService.queryRecruitSchoolInfo(recruitSchoolId);
    }

    @PutMapping("/updateRecruitSchoolInfo")
    @ApiOperation("编辑招生学校信息")
    public Result updateRecruitSchoolInfo(@Valid @RequestBody UpdateRecruitSchoolQuery updateRecruitSchoolQuery){
        return recruitSchoolService.updateRecruitSchoolInfo(updateRecruitSchoolQuery);
    }

    @DeleteMapping("/deleteRecruitSchoolInfo/{recruitSchoolId}")
    @ApiImplicitParam(name = "recruitSchoolId", value = "招生学校ID", required = true)
    @ApiOperation("删除招生学校信息")
    public Result deleteRecruitSchoolInfo(@Valid @PathVariable("recruitSchoolId")
                                          @NotNull(message = "招生学校ID不能为空")
                                          @Min(value = 1L, message = "招生学校ID最小为1")
                                          @Max(value = Long.MAX_VALUE, message = "招生学校ID超出最大限制")
                                                  Long recruitSchoolId){
        return recruitSchoolService.deleteRecruitSchoolInfo(recruitSchoolId);
    }

}
