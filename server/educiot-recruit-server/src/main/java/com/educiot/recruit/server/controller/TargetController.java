package com.educiot.recruit.server.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.educiot.common.api.TermInfo;
import com.educiot.common.api.TermInfoResult;
import com.educiot.common.base.CodeMsg;
import com.educiot.common.base.MyExcption;
import com.educiot.common.base.Result;
import com.educiot.common.constant.Constant;
import com.educiot.common.util.CommonUtil;
import com.educiot.recruit.data.entity.Group;
import com.educiot.recruit.data.entity.Target;
import com.educiot.recruit.data.entity.query.ListTargetPageQuery;
import com.educiot.recruit.data.entity.request.AddChannelOrMajorReq;
import com.educiot.recruit.data.entity.request.AddOrEditTargetReq;
import com.educiot.recruit.data.entity.vo.TargetDetailVO;
import com.educiot.recruit.data.entity.vo.TargetVO;
import com.educiot.recruit.data.service.IGroupService;
import com.educiot.recruit.data.service.ITargetService;
import com.educiot.recruit.server.common.BaseController;
import com.educiot.recruit.server.common.LoginUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 目标模块
 * </p>
 *
 * @author Xuchao
 * @since 2020-04-13
 */
@Api(tags = {"目标模块"}, value = "目标模块")
@RestController
@RequestMapping("/target")
@Log4j2
public class TargetController extends BaseController {

    @Autowired
    private ITargetService targetService;

    @Autowired
    private IGroupService groupService;


    @GetMapping("/list")
    @ApiOperation(value = "分页获取招生目标列表", response = TargetVO.class)
    public Result list(ListTargetPageQuery param) {
        log.info("分页获取招生目标列表");
        //获取登录用户信息
        LoginUser loginUser = getLoginUser();
        //分页获取招生目标列表
        IPage<TargetVO> page = targetService.getTargetListPage(param, loginUser.getCurrentSchoolId());
        return Result.success(page);
    }

    @PostMapping("/addOrEdit")
    @ApiOperation("添加或编辑招生目标")
    public Result addOrEdit(@Valid AddOrEditTargetReq addOrEditTargerReq) throws Exception {
        log.info("添加或编辑招生目标");
        //获取登录用户信息
        LoginUser loginUser = getLoginUser();
        //添加或编辑招生目标
        Long result = targetService.addOrEditTarget(addOrEditTargerReq, loginUser.getCurrentSchoolId(),loginUser.getApiInfo().getSchoolId(),loginUser.getApiInfo().getUserId(),loginUser.getApiInfo().getUserName(), getAPIRequest());

        //返回targetId
        if (CommonUtil.checkId(result)) {
            return Result.success(result.toString());
        } else if (result.equals(Long.valueOf(Constant.SYS_NEGATIVE_ONE))) {
            //返回学期已存在
            return Result.error(CodeMsg.YEAR_OR_SEMESTER_EXIST_TARGET);
        }
        return Result.error(CodeMsg.SERVER_EXCEPTION);
    }

    @GetMapping("/detail/{targetId}")
    @ApiOperation(value = "获取招生目标详情", response = TargetDetailVO.class)
    public Result detail(@PathVariable Long targetId) throws MyExcption {
        log.info("获取招生目标详情");
        //获取招生目标详情
        targetService.beforeGetDetail(targetId, getLoginUser().getCurrentSchoolId());
        TargetDetailVO detail = targetService.getDetail(targetId);
        return Result.success(detail);
    }

    @PutMapping("/editChannelOrMajorNode")
    @ApiOperation("修改渠道/专业节点信息")
    public Result editChannelOrMajorNode(@RequestBody AddChannelOrMajorReq addChannelOrMajorReq) {
        log.info("修改渠道/专业节点信息");
        //修改渠道/专业节点信息
        Boolean result =targetService.editChannelOrMajorNode(addChannelOrMajorReq);
        if(result){
            return Result.success();
        }

        return Result.error(CodeMsg.SERVER_EXCEPTION);
    }


    @PostMapping("/addSemester")
    @ApiOperation("新增学期")
    public Result addSemester(@RequestBody TermInfo addSemesterReq) throws MyExcption {
        log.info("新增学期");
        //通过api新增学期

        Object str = getAPIRequest().addTerm(addSemesterReq);
        return Result.success(str);
    }

    @GetMapping("/semesterList/{schoolId}")
    @ApiOperation("获取学校学期列表")
    public Result getSemesterList(@PathVariable Long schoolId) throws MyExcption {
        log.info("获取学校学期列表");
        //根据学校id通过api获取学校学期列表
        TermInfoResult str = getAPIRequest().termInfo(schoolId.toString());
        return Result.success(str);
    }

    @PostMapping("/checkTarget")
    @ApiOperation("检查指定学期是否存在招生目标")
    public Result checkTarget(Long schoolId, Long semesterId) {
        log.info("检查指定学期是否存在招生目标");
        //根据学校id和学期id获取招生目标，存在即返回目标对象，不存在返回空
        Target target = targetService.checkTarget(schoolId, semesterId);
        return Result.success(target);
    }

    @PostMapping("/checkTargetGroup")
    @ApiOperation("检查指定学期是否存在招生目标对应的区域")
    public Result checkTargetGroup(Long schoolId, Long semesterId) {
        log.info("检查指定学期是否存在招生目标对应的区域");
        //根据学校id和学期id获取招生目标，存在即返回目标对象，不存在返回空

        //查询线下渠道区域数据
        List<Group> groupList = groupService.getTargetUnderLineData(schoolId, semesterId);
        if (groupList == null || groupList.size() == 0) {
            return Result.error(CodeMsg.API_FAILED);
        } else {
            return Result.success();
        }
    }

}
