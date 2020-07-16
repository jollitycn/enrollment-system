package com.educiot.recruit.server.controller.plan;


import com.educiot.recruit.common.api.DepartInfoResult;
import com.educiot.recruit.common.base.CodeMsg;
import com.educiot.recruit.common.base.MyExcption;
import com.educiot.recruit.common.base.Result;
import com.educiot.recruit.data.entity.query.AddCollegeQuery;
import com.educiot.recruit.data.entity.query.UpdateCollegeQuery;
import com.educiot.recruit.data.entity.vo.CollegeAndMajorVO;
import com.educiot.recruit.data.entity.vo.CollegeVO;
import com.educiot.recruit.data.entity.vo.ProgressListVO;
import com.educiot.recruit.data.service.ICollegeService;
import com.educiot.recruit.server.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 招生院系表 前端控制器
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
@RestController
@RequestMapping("/college")
@Api(tags = {"招生院系管理"}, value = "CollegeController")
public class CollegeController extends BaseController {

    @Resource
    private ICollegeService collegeService;

    @GetMapping("/queryCollegeInfo")
    @ApiOperation(value="获取招生院系列表信息",response = ProgressListVO.class)
    public Result queryCollegeInfo(@Valid String planId){
        return collegeService.queryCollegeInfo(planId);
    }

    @GetMapping("/queryCollegeInfoById")
    @ApiOperation("获取招生院系信息")
    public Result queryCollegeInfoById(@Valid String collegeId){
        return collegeService.queryCollegeInfoById(collegeId);
    }

    @PutMapping("/updateCollegeInfo")
    @ApiOperation("编辑招生院系信息")
    public Result updateCollegeInfo(@Valid @RequestBody UpdateCollegeQuery updateCollegeQuery){
        return collegeService.updateCollegeInfo(updateCollegeQuery);
    }

    @GetMapping("/addCollegeInfo")
    @ApiOperation("添加招生院系信息")
    public Result addCollegeInfo(@Valid Long planId) throws MyExcption {
        if (null == planId) {
            return Result.error(CodeMsg.PARAMETER_ISNULL);
        }
        DepartInfoResult map = getAPIRequest().departInfo(getLoginUser().getApiInfo().getSchoolId().toString());
        AddCollegeQuery addCollegeQuery = new AddCollegeQuery();
        addCollegeQuery.setPlanId(planId);
        addCollegeQuery.setRecruitSchoolId(getLoginUser().getApiInfo().getSchoolId());
        addCollegeQuery.setCreatorId(getLoginUser().getApiInfo().getUserId());
        addCollegeQuery.setCreatorName(getLoginUser().getApiInfo().getUserName());
        return collegeService.addCollegeInfo(addCollegeQuery, map.getData());

    }

    @GetMapping("/queryCollegeAndMajor")
    @ApiOperation("获取招生院系信息以及下面所有的专业信息")
    public Result<List<CollegeAndMajorVO>> queryCollegeAndMajor(@Valid Long planId){
        return collegeService.queryCollegeAndMajor(planId, getLoginUser().getApiInfo().getSchoolId(), getLoginUser().getCurrentSemesterId());
    }

}
