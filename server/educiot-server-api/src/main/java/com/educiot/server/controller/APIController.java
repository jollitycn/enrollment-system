package com.educiot.server.controller;


import com.educiot.common.api.APIResult;
import com.educiot.common.api.MajorListResult;
import com.educiot.common.api.TermInfo;
import com.educiot.common.base.MyExcption;
import com.educiot.server.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public APIResult schoolUserInfo() throws MyExcption {
        return getAPIRequest().schoolUserInfo();
    }


    @PostMapping("/sys/school/org/struct")
    @ApiOperation("根据学校id获取当前组织结构")
    public APIResult orgStruct(String schoolId) throws MyExcption {
        return getAPIRequest().orgStruct(schoolId);
    }

    @PostMapping("/sys/depart/info")
    @ApiOperation("根据学校id获取院系列表信息")
    public APIResult departInfo(String schoolId) throws MyExcption {
        return  getAPIRequest().departInfo(schoolId) ;
    }

    @PostMapping("/sys/term/info")
    @ApiOperation("根据学校id获取学期列表信息")
    public APIResult termInfo(String schoolId) throws MyExcption {
        return getAPIRequest().termInfo(schoolId) ;
    }

    @PostMapping("/sys/add/major")
    @ApiOperation("新增专业同步到平台")
    public APIResult addMajor(String major, String departId) throws MyExcption {
        return  getAPIRequest().addMajor(major, departId) ;
    }

    @PostMapping("/sys/major/list")
    @ApiOperation("根据院系id获取专业列表")
    public APIResult majorList(String departId) throws MyExcption {
//        if (null == departId) {
//            return Result.error(CodeMsg.PARAMETER_ISNULL);
//        }
        MajorListResult result = getAPIRequest().majorList(departId);
//        if (result.isSuccess()) {
            return  result ;
//        } else {
//            return Result.error(CodeMsg.API_FAILED, result.getMsg());
//        }
    }

    @PostMapping("/sys/term/add")
    @ApiOperation("新增学期返回学期id和名称")
    public APIResult addTerm(@RequestBody TermInfo termInfo) throws MyExcption {
        return getAPIRequest().addTerm(termInfo) ;
    }


    @PostMapping("/sys/get/superior/info")
    @ApiOperation("根据员工id或者模糊名称，获取负责人信息")
    public APIResult getSuperior(String userId, String name) throws MyExcption {
        return  getAPIRequest().getSuperior(userId, name) ;
    }

//    @PostMapping("/sys/get/msg/code")
//    @ApiOperation("招生系统获取短信息验证码")
//    public Result getMsgCode(String phone) throws MyExcption {
//        MsgCodeResult result = getAPIRequest().getMsgCode(phone);
//        if (result.isSuccess()) {
//            return Result.success();
//        } else {
//            return Result.error(CodeMsg.API_FAILED, result);
//        }
//    }

}
