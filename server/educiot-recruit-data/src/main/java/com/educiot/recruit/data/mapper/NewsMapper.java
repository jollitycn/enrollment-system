package com.educiot.recruit.data.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.educiot.recruit.data.entity.News;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.educiot.recruit.data.entity.query.ListNewsPageQuery;
import com.educiot.recruit.data.entity.vo.MessageListVO;
import com.educiot.recruit.data.entity.vo.NewsVO;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * <p>
 * 新闻表 Mapper 接口
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-13
 */
public interface NewsMapper extends BaseMapper<News> {

    /**
     * 获取新闻列表
     * @param page
     * @param newsParam 封装参数
     * @return
     */
    IPage<NewsVO> getNewsListPage(Page<NewsVO> page,
                                  @Param(value = "newsParam") ListNewsPageQuery newsParam,
                                  @Param(value = "recruitSchoolId") Long recruitSchoolId);

    /**
     * 获取新闻详情
     * @param newsId
     * @return
     */
    NewsVO getNewsByNewsId(@Param(value = "newsId") Long newsId,
                           @Param(value = "recruitSchoolId") Long recruitSchoolId);

    /**
     * 获取学校新闻列表
     * @param recruitSchoolId
     * @param planId
     * @param loginType
     * @return List<MessageListVO>
     */
    List<MessageListVO> getMessageList(@Param("recruitSchoolId") Long recruitSchoolId,
//                                       @Param("planId") Long planId,
                                       @Param("loginType") Integer loginType);

    IPage<NewsVO> getNewsListPageServer(Page<NewsVO> page,
                                        @Param(value = "newsParam") ListNewsPageQuery newsParam,
                                        @Param(value = "recruitSchoolId") Long recruitSchoolId);
}
