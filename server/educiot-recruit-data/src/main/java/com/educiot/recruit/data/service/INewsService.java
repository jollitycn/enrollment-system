package com.educiot.recruit.data.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.educiot.recruit.data.entity.News;
import com.educiot.recruit.data.entity.query.ListNewsPageQuery;
import com.educiot.recruit.data.entity.request.AddOrEditNewsReq;
import com.educiot.recruit.data.entity.vo.MessageListVO;
import com.educiot.recruit.data.entity.vo.NewsVO;
import com.educiot.common.base.MyExcption;

import java.util.List;

/**
 * <p>
 * 新闻表 服务类
 * </p>
 *
 * @author Xuchao
 * @since 2020-04-13
 */
public interface INewsService extends IService<News> {

    /**
     * 分页获取新闻列表
     * @param param
     * @param recruitSchoolId
     * @return
     */
    IPage<NewsVO> getNewsListPage(ListNewsPageQuery param, Long recruitSchoolId,Long studentInfo);

    /**
     * 获取新闻详情
     * @param newsId
     * @param recruitSchoolId
     * @return
     */
    NewsVO getNewsByNewsId(Long newsId, Long recruitSchoolId);

    /**
     * 添加或编辑新闻
     * @param addOrEditNewsReq
     * @param loginUser
     * @return
     */
    Boolean addOrEditNews(AddOrEditNewsReq addOrEditNewsReq,Long recruitSchoolId,Long userId,String userName);

    /**
     * 删除新闻
     * @param newsId
     * @return
     */
    Boolean deleteNews(Long newsId);

    /**
     * 发布新闻
     * @param newsId
     * @return
     */
    Boolean release(Long newsId);

    /**
     * 全局异常使用示例1
     * @param listNewsPageQuery
     * @throws MyExcption
     */
    void excption3(ListNewsPageQuery listNewsPageQuery) throws MyExcption;

    /**
     * 全局异常使用示例2
     * @param listNewsPageQuery
     */
    void excption2(ListNewsPageQuery listNewsPageQuery) ;

    /**
     * 获取学校新闻列表
     * @param recruitSchoolId
     * @param planId
     * @param loginType
     * @return List<MessageListVO>
     */
    List<MessageListVO> getMessageList(Long recruitSchoolId, Long planId, Integer loginType);

    IPage<NewsVO> getNewsListPageServer(ListNewsPageQuery param, Long currentSchoolId);
}
