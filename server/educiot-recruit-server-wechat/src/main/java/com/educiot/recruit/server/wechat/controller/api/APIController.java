package com.educiot.recruit.server.wechat.controller.api;


import com.educiot.recruit.common.api.MajorListResult;
import com.educiot.recruit.common.api.MsgCodeResult;
import com.educiot.recruit.common.api.TermInfo;
import com.educiot.recruit.common.base.CodeMsg;
import com.educiot.recruit.common.base.MyExcption;
import com.educiot.recruit.common.base.Result;
import com.educiot.recruit.server.wechat.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 * 选择专业表 前端控制器
 * </p>
 *
 * @author Hongsifan
 * @since 2020-04-13
 */


@Api(tags = {"平台API接口"}, value = "APIController")
@RestController
@RequestMapping("/api")
@Configuration
public class APIController extends BaseController {

    @PostMapping("/sys/school/user/info")
    @ApiOperation("获取当前登录学校信息和账号信息")
    public Result schoolUserInfo() throws MyExcption {
        return Result.success(getAPIRequest().schoolUserInfo());
    }

    @PostMapping("/sys/school/org/struct")
    @ApiOperation("根据学校id获取当前组织结构")
    public Result orgStruct(String schoolId) throws MyExcption {
        return Result.success(getAPIRequest().orgStruct(schoolId));
    }

    @PostMapping("/sys/depart/info")
    @ApiOperation("根据学校id获取院系列表信息")
    public Result departInfo(String schoolId) throws MyExcption {
        return Result.success(getAPIRequest().departInfo(schoolId));
    }

    @PostMapping("/sys/term/info")
    @ApiOperation("根据学校id获取学期列表信息")
    public Result termInfo(String schoolId) throws MyExcption {
        return Result.success(getAPIRequest().termInfo(schoolId));
    }

    @PostMapping("/sys/add/major")
    @ApiOperation("新增专业同步到平台")
    public Result addMajor(String major, String departId) throws MyExcption {
        return Result.success(getAPIRequest().addMajor(major, departId));
    }

    @PostMapping("/sys/major/list")
    @ApiOperation("根据院系id获取专业列表")
    public Result majorList(String departId) throws MyExcption {
        if (null == departId) {
            return Result.error(CodeMsg.PARAMETER_ISNULL);
        }
        MajorListResult result = getAPIRequest().majorList(departId);
        if (result.isSuccess()) {
            return Result.success(result.getData());
        } else {
            return Result.error(CodeMsg.API_FAILED.getRetCode(), result.getErrorMessage());
        }
    }

    @PostMapping("/sys/term/add")
    @ApiOperation("新增学期返回学期id和名称")
    public Result addTerm(TermInfo name) throws MyExcption {
        return Result.success(getAPIRequest().addTerm(name));
    }


    @PostMapping("/sys/get/superior/info")
    @ApiOperation("根据员工id或者模糊名称，获取负责人信息")
    public Result getSuperior(String userId, String name) throws MyExcption {
        return Result.success(getAPIRequest().getSuperior(userId, name));
    }

    @PostMapping("/sys/get/msg/code")
    @ApiOperation("招生系统获取短信息验证码")
    public Result<MsgCodeResult> getMsgCode(String phone) throws MyExcption {
        MsgCodeResult result = getAPIRequest(false).getMsgCode(phone);
        if (result.isSuccess()) {
            return Result.success(result);
        } else {
            return Result.error(CodeMsg.VERIFY_CODE_SEND_ERROR,result);
        }
    }
}
