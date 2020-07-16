package com.educiot.recruit.server.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.educiot.common.base.CodeMsg;
import com.educiot.common.base.MyExcption;
import com.educiot.common.base.Result;
import com.educiot.recruit.data.entity.SysDictionary;
import com.educiot.recruit.data.entity.query.ListNewsPageQuery;
import com.educiot.recruit.data.entity.request.AddOrEditNewsReq;
import com.educiot.recruit.data.entity.vo.NewsVO;
import com.educiot.recruit.data.service.INewsService;
import com.educiot.recruit.data.service.ISysDictionaryService;
import com.educiot.recruit.server.common.BaseController;
import com.educiot.recruit.server.common.LoginUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 新闻模块
 * </p>
 *
 * @author Xuchao
 * @since 2020-04-20
 */
@Api(tags = {"新闻模块"}, value = "新闻模块")
@RestController
@RequestMapping("/news")
@Log4j2
public class NewsController extends BaseController {

    @Autowired
    private INewsService newsService;

    @Resource
    private ISysDictionaryService sysDictionaryService;

    @GetMapping("/list")
    @ApiOperation(value = "分页获取新闻列表",response = NewsVO.class)
    public Result list(ListNewsPageQuery param) {

        log.info("分页获取新闻列表");

        //获取登录用户
        LoginUser loginUser=getLoginUser();

        //分页获取新闻列表
        IPage<NewsVO> page =newsService.getNewsListPageServer(param, loginUser.getCurrentSchoolId());

        return Result.success(page);
    }

    @GetMapping("/detail/{newsId}")
    @ApiOperation(value = "获取新闻详情",response = NewsVO.class)
    public Result detail(@PathVariable Long newsId) {

        log.info("获取新闻详情");

        //获取登录用户
        LoginUser loginUser=getLoginUser();

        //分页获取新闻列表
        NewsVO newsVO =newsService.getNewsByNewsId(newsId, loginUser.getCurrentSchoolId());

        return Result.success(newsVO);
    }

    @PostMapping("/addOrEdit")
    @ApiOperation("添加或编辑新闻")
    public Result addOrEdit(@RequestBody AddOrEditNewsReq addOrEditNewsReq) {

        log.info("添加或编辑新闻");

        //获取登录用户信息
        LoginUser loginUser= getLoginUser();

        //添加或编辑新闻
        Boolean result =newsService.addOrEditNews(addOrEditNewsReq,loginUser.getCurrentSchoolId(),loginUser.getApiInfo().getUserId(),loginUser.getApiInfo().getUserName());

        if(result){
            return Result.success();
        }

        return Result.error(CodeMsg.SERVER_EXCEPTION);
    }

    @DeleteMapping("/delete/{newsId}")
    @ApiOperation("删除新闻")
    public Result delete(@PathVariable Long newsId) {

        log.info("删除新闻{}",newsId);

        //删除新闻
        Boolean result =newsService.deleteNews(newsId);

        if(result){
            return Result.success();
        }

        return Result.error(CodeMsg.SERVER_EXCEPTION);

    }

    @PostMapping("/release/{newsId}")
    @ApiOperation("发布新闻")
    public Result release(@PathVariable Long newsId) {

        log.info("发布新闻{}",newsId);

        //发布新闻
        Boolean result =newsService.release(newsId);

        if(result){
            return Result.success();
        }

        return Result.error(CodeMsg.SERVER_EXCEPTION);

    }


    @GetMapping("/getMessageTypeList")
    @ApiOperation(value = "获取信息类型列表", response = SysDictionary.class)
    public Result<?> getMessageTypeList() {

        //获取登录用户
        LoginUser loginUser = getLoginUser();

        List<SysDictionary> messageTypeListVOList = sysDictionaryService.getDictListByCode(loginUser.getCurrentSchoolId(), "news_message_type");

        return Result.success(messageTypeListVOList);

    }


    @PostMapping("/excption1")
    @ApiOperation("异常测试1")
    public Result excption1(@RequestBody ListNewsPageQuery listNewsPageQuery) throws MyExcption {

        log.info("异常测试1");

            newsService.excption3(listNewsPageQuery);


        return Result.error(CodeMsg.SERVER_EXCEPTION);

    }

    @PostMapping("/excption2")
    @ApiOperation("异常测试2")
    public Result excption2( ListNewsPageQuery listNewsPageQuery) throws Exception {

        log.info("异常测试2");

            newsService.excption2(listNewsPageQuery);


        return Result.error(CodeMsg.SERVER_EXCEPTION);

    }
}
