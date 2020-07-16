package com.educiot.recruit.server.wechat.controller.student;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.educiot.recruit.common.base.MyExcption;
import com.educiot.recruit.common.base.Result;
import com.educiot.recruit.data.entity.query.AddInSchoolQuery;
import com.educiot.recruit.data.entity.query.ListClassPageQuery;
import com.educiot.recruit.data.entity.query.ListSourceSchoolPageQuery;
import com.educiot.recruit.data.entity.vo.ClassVO;
import com.educiot.recruit.data.entity.vo.InSchoolClassInfoVO;
import com.educiot.recruit.data.entity.vo.InSchoolVO;
import com.educiot.recruit.data.entity.vo.ListSourceSchoolVO;
import com.educiot.recruit.data.service.IClassService;
import com.educiot.recruit.data.service.ISourceSchoolService;
import com.educiot.recruit.data.service.student.IInSchoolService;
import com.educiot.recruit.server.wechat.common.BaseController;
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
 * @author Mr.Ai
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

    @Resource
    private IClassService classService;


    @GetMapping("/listClassPage")
    @ApiOperation("分页获取生源学校下的班级信息列表")
    public Result<IPage<ClassVO>> listClassPage(@Valid ListClassPageQuery pageQuery) throws MyExcption {
        //pageQuery.setSemesterId();
        IPage<ClassVO> classVOIPage = classService.listSchoolClassPage(pageQuery, getLoginVO().getPlan().getSemesterId());
        return Result.success(classVOIPage);
    }


    @GetMapping("/listSourceSchoolPage")
    @ApiOperation("分页获取生源学校信息列表")
    public Result<IPage<ListSourceSchoolVO>> listSourceSchoolPage(@Valid ListSourceSchoolPageQuery param) {
        param.setSemesterId(getLoginVO().getPlan().getSemesterId());
        IPage<ListSourceSchoolVO> page = sourceSchoolService.listSourceSchoolWeChat(param,
                getLoginVO().getCurrentRecruitSchool().getRecruitSchoolId());
        return Result.success(page);
    }

    @PostMapping("/saveOrUpdateInSchoolInfo")
    @ApiOperation("添加或修改学生在校信息")
    public Result saveOrUpdateInSchoolInfo(@Valid @RequestBody AddInSchoolQuery addInSchoolQuery) throws MyExcption {
        //新增时，根据学生基本信息ID，查询出公关id
        //需要传入毕业学校ID 和 所属班级ID
        //更新公关信息表的信息
        addInSchoolQuery.setStudentInfo(getLoginVO().getCurrentStudent().getStudentInfo());
        addInSchoolQuery.setCreatorId(getLoginVO().getCurrentStudent().getStudentInfo());
        addInSchoolQuery.setCreatorName(getLoginVO().getCurrentStudent().getStudentName());
        Boolean aBoolean = iInSchoolService.saveOrUpdateInSchoolInfoForWeChat(addInSchoolQuery);
        return Result.success(aBoolean);
    }

    @GetMapping("/queryInSchoolInfo")
    @ApiOperation("根据学生基本信息ID查询学生在校信息(包含生源学校、班级信息、成绩信息)")
    public Result queryInSchoolInfo() {
        InSchoolVO inSchoolVO = iInSchoolService.queryInSchoolInfo(getLoginVO().getCurrentStudent().getStudentInfo(), getLoginVO().getCurrentRecruitSchool().getRecruitSchoolId());
        return Result.success(inSchoolVO);
    }

    @GetMapping("/queryInSchoolInfoStatu")
    @ApiOperation("查询学生在校信息填写完成状态")
    public Result queryInSchoolInfoStatu() {
        Boolean aBoolean = iInSchoolService.queryInSchoolInfoStatu(getLoginVO().getCurrentStudent().getStudentInfo(), getLoginVO().getCurrentRecruitSchool().getRecruitSchoolId());
        return Result.success(aBoolean);
    }

    @GetMapping("/getSourceSchoolInfo")
    @ApiOperation(value = "获取学校班级信息", response = InSchoolClassInfoVO.class)
    public Result<?> getSourceSchoolInfo() {

        InSchoolClassInfoVO inSchoolClassInfoVO = iInSchoolService.getSourceSchoolInfo(getLoginVO().getCurrentStudent().getStudentInfo(), getLoginVO().getCurrentRecruitSchool().getRecruitSchoolId());
        return Result.success(inSchoolClassInfoVO);
    }
}
