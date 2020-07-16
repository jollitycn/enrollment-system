package com.educiot.recruit.server.wechat.controller.student;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.educiot.common.base.Result;
import com.educiot.recruit.data.entity.query.ListNewsPageQuery;
import com.educiot.recruit.data.entity.vo.MiniProgramLoginVO;
import com.educiot.recruit.data.entity.vo.NewsVO;
import com.educiot.recruit.data.entity.vo.RecruitSchoolVO;
import com.educiot.recruit.data.service.INewsService;
import com.educiot.recruit.data.service.IRecruitSchoolService;
import com.educiot.recruit.server.wechat.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * <p>
 * 新闻模块
 * </p>
 *
 * @author Xuchao
 * @since 2020-04-30
 */
@Api(tags = {"学校宣传"}, value = "学校宣传模块")
@RestController
@RequestMapping("/news")
@Log4j2
public class NewsController extends BaseController {

    @Resource
    IRecruitSchoolService recruitSchoolService;
    @Autowired
    private INewsService newsService;

    @GetMapping("/list")
    @ApiOperation(value = "分页获取新闻列表", response = NewsVO.class)
    public Result list(ListNewsPageQuery param) {

        log.info("分页获取新闻列表");

        //获取登录用户
        MiniProgramLoginVO miniProgramLoginVO = getLoginVO();

        //分页获取新闻列表
        IPage<NewsVO> page = newsService.getNewsListPage(param, miniProgramLoginVO.getCurrentRecruitSchool().getRecruitSchoolId(),miniProgramLoginVO.getCurrentStudent().getStudentInfo());

        return Result.success(page);
    }

    @GetMapping("/detail/{newsId}")
    @ApiOperation(value = "获取新闻详情", response = NewsVO.class)
    public Result detail(@PathVariable Long newsId) {

        log.info("获取新闻详情");

        MiniProgramLoginVO miniProgramLoginVO = getLoginVO();

        //分页获取新闻列表
        NewsVO newsVO = newsService.getNewsByNewsId(newsId, miniProgramLoginVO.getCurrentRecruitSchool().getRecruitSchoolId());

        return Result.success(newsVO);
    }


    @GetMapping("/queryRecruitSchoolInfo")
    @ApiOperation("获取招生学校信息")
    public Result<RecruitSchoolVO> queryRecruitSchoolInfo(@Valid @RequestParam("recruitSchoolId") String recruitSchoolId) {
        return recruitSchoolService.queryRecruitSchoolInfo(recruitSchoolId);
    }

}
