package com.educiot.recruit.data.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.educiot.recruit.data.LoginUser;
import com.educiot.recruit.data.entity.News;
import com.educiot.recruit.data.entity.query.ListNewsPageQuery;
import com.educiot.recruit.data.entity.request.AddOrEditNewsReq;
import com.educiot.recruit.data.entity.vo.MessageListVO;
import com.educiot.recruit.data.entity.vo.NewsVO;
import com.educiot.recruit.data.mapper.NewsMapper;
import com.educiot.recruit.data.service.ILoginRelationService;
import com.educiot.recruit.data.service.INewsService;
import com.educiot.recruit.common.base.CodeMsg;
import com.educiot.recruit.common.base.MyExcption;
import com.educiot.recruit.common.constant.Constant;
import com.educiot.recruit.common.util.CommonUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 新闻表 服务实现类
 * </p>
 *
 * @author Xuchao
 * @since 2020-04-13
 */
@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements INewsService {


    @Resource
    private ILoginRelationService loginRelationService;

    /**
     * 分页获取新闻列表
     * @param param
     * @param recruitSchoolId
     * @return
     */
    @Override
    public IPage<NewsVO> getNewsListPage(ListNewsPageQuery param, Long recruitSchoolId, Long studentInfo) {

        //构建page对象
        Page<NewsVO> page = new Page<>(param.getPageNum(), param.getPageSize());

        //判断搜索参数是否有效，有效则增加%用作模糊搜索
        String searchKey=CommonUtil.checkSearchKey(param.getTitle());
        if(null!=searchKey){
            param.setTitle(searchKey);
        }
        if(null != studentInfo){
            //查询登录类型
            Integer integer = loginRelationService.queryLoginType(studentInfo);
            param.setRecevier(integer);
        }

        //DAO查询，按创建时间倒序，时间格式化注解实现 格式为 YYYY-MM-DD
        IPage<NewsVO> result=baseMapper.getNewsListPage(page,param, recruitSchoolId);

        return result;

    }

    /**
     * 获取新闻详情
     *
     * @param newsId
     * @return
     */
    @Override
    public NewsVO getNewsByNewsId(Long newsId,Long recruitSchoolId) {
        //DAO查询
       return baseMapper.getNewsByNewsId(newsId, recruitSchoolId);
    }

/**
     * 添加或编辑新闻
     *
     * @param addOrEditNewsReq
     * @return
     */
    @Override
    public Boolean addOrEditNews(AddOrEditNewsReq addOrEditNewsReq,LoginUser loginUser) {

        Long newsId=addOrEditNewsReq.getNewsId();

        //根据newsId判断是新增还是修改
        if(CommonUtil.checkId(newsId)){

            News news=baseMapper.selectById(newsId);
            BeanUtil.copyProperties(addOrEditNewsReq,news);
            return this.updateById(news);
            //新增
        }else {
            News news=new News();
            BeanUtil.copyProperties(addOrEditNewsReq,news);
            //写入创建人等相关信息
            news.setCreatorId(loginUser.getApiInfo().getUserId());
            news.setCreatorName(loginUser.getApiInfo().getUserName());
            news.setCreateTime(LocalDateTime.now());
            news.setRecruitSchoolId(loginUser.getCurrentSchoolId());
            return this.save(news);
        }

    }

    /**
     * 删除新闻
     *
     * @param newsId
     * @return
     */
    @Override
    public Boolean deleteNews(Long newsId) {

        return  this.removeById(newsId);
    }

    /**
     * 发布新闻
     *
     * @param newsId
     * @return
     */
    @Override
    public Boolean release(Long newsId) {

        News news=baseMapper.selectById(newsId);
        //更新状态为已发布
        news.setNewsStatus(Constant.SYS_ONE);

        return this.updateById(news);
    }

    @Override
    public void excption3(ListNewsPageQuery listNewsPageQuery) throws MyExcption {
        //自定义异常，可配合自定义code进行统一返回
        throw new MyExcption(CodeMsg.DATA_INSERT_ERROR);
    }



    @Override
    public void excption2(ListNewsPageQuery listNewsPageQuery){
        //系统异常，可自定义异常信息，返回code是固定的
        throw new NullPointerException("空指针异常");
    }

    /**
     * 获取学校新闻列表
     * @param recruitSchoolId
     * @param planId
     * @param loginType
     * @return List<MessageListVO>
     */
    @Override
    public List<MessageListVO> getMessageList(Long recruitSchoolId, Long planId, Integer loginType) {

        return baseMapper.getMessageList(recruitSchoolId, loginType);
    }

    @Override
    public IPage<NewsVO> getNewsListPageServer(ListNewsPageQuery param, Long recruitSchoolId) {
        //构建page对象
        Page<NewsVO> page = new Page<>(param.getPageNum(), param.getPageSize());

        //判断搜索参数是否有效，有效则增加%用作模糊搜索
        String searchKey=CommonUtil.checkSearchKey(param.getTitle());
        if(null!=searchKey){
            param.setTitle(searchKey);
        }

        //DAO查询，按创建时间倒序，时间格式化注解实现 格式为 YYYY-MM-DD
        IPage<NewsVO> result=baseMapper.getNewsListPageServer(page,param, recruitSchoolId);

        return result;
    }


}
