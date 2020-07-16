package com.educiot.recruit.server.app.controller.student;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.educiot.recruit.common.base.MyExcption;
import com.educiot.recruit.common.base.Result;
import com.educiot.recruit.data.entity.query.AddInSchoolQuery;
import com.educiot.recruit.data.entity.query.ListSourceSchoolPageQuery;
import com.educiot.recruit.data.entity.vo.InSchoolVO;
import com.educiot.recruit.data.entity.vo.ListSourceSchoolVO;
import com.educiot.recruit.data.service.ISourceSchoolService;
import com.educiot.recruit.data.service.student.IInSchoolService;
import com.educiot.recruit.server.app.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * <p>
 * 学生在校信息表 前端控制器
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-13
 */
@RestController
@RequestMapping("/in-school")
@Api(tags = {"学生在校信息"}, value = "InSchoolController")
public class InSchoolController extends BaseController {

    @Resource
    private IInSchoolService iInSchoolService;

    @Resource
    private ISourceSchoolService sourceSchoolService;

    @GetMapping("/listSourceSchoolPage")
    @ApiOperation("分页获取生源学校信息列表")
    public Result<IPage<ListSourceSchoolVO>> listSourceSchoolPage(@Valid ListSourceSchoolPageQuery param) {
        IPage<ListSourceSchoolVO> page = sourceSchoolService.listSourceSchoolPage(param,
                getLoginUser().getCurrentSchoolId());
        return Result.success(page);
    }

    @PostMapping("/saveOrUpdateInSchoolInfo")
    @ApiOperation("添加或修改学生在校信息")
    public Result saveOrUpdateAimMajorInfo(@Valid @RequestBody AddInSchoolQuery addInSchoolQuery) throws MyExcption {
        addInSchoolQuery.setCreatorId(getLoginUser().getApiInfo().getUserId());
        addInSchoolQuery.setCreatorName(getLoginUser().getApiInfo().getUserName());
        addInSchoolQuery.setRecruitSchoolId(getLoginUser().getCurrentSchoolId());
        return iInSchoolService.saveOrUpdateInSchoolInfo(addInSchoolQuery);
    }

    @GetMapping("/queryInSchoolInfo")
    @ApiOperation("根据学生基本信息ID查询学生在校信息(包含生源学校、班级信息、成绩信息)")
    public Result queryInSchoolInfo(@Valid Long studentInfoId) {
        InSchoolVO inSchoolVO = iInSchoolService.queryInSchoolInfo(studentInfoId, getLoginUser().getCurrentSchoolId());
        return Result.success(inSchoolVO);
    }

}
