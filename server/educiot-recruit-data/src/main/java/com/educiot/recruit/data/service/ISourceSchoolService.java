package com.educiot.recruit.data.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.educiot.recruit.data.entity.SourceSchool;
import com.educiot.recruit.data.entity.query.AddSourceSchoolQuery;
import com.educiot.recruit.data.entity.query.ListSourceSchoolPageQuery;
import com.educiot.recruit.data.entity.query.UpdateSourceSchoolQuery;
import com.educiot.recruit.data.entity.vo.*;
import com.educiot.recruit.common.base.Result;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 生源学校 服务类
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
public interface ISourceSchoolService extends IService<SourceSchool> {

    /**
     * 分页获取生源学校别表
     *
     * @param pageQuery       查询条件
     * @param recruitSchoolId 招生学校id
     * @return com.baomidou.mybatisplus.core.metadata.IPage&lt;com.educiot.recruit.data.entity.vo.ListSourceSchoolVO&gt;
     * @author Pan Juncai
     * @date 2020/4/10 15:31
     */
    IPage<ListSourceSchoolVO> listSourceSchoolPage(ListSourceSchoolPageQuery pageQuery, Long recruitSchoolId);

    /**
     * 小程序获取学校列表
     * @param pageQuery
     * @param recruitSchoolId
     * @return
     */
    IPage<ListSourceSchoolVO> listSourceSchoolWeChat(ListSourceSchoolPageQuery pageQuery, Long recruitSchoolId);

    /**
     * 获取字典里配置的学校类型
     *
     * @param recruitSchoolId 招生学校id
     * @return java.util.List&lt;com.educiot.recruit.data.entity.vo.SysDictVO&gt;
     * @author Pan Juncai
     * @date 2020/4/16 13:54
     */
    List<SysDictVO> listSourceSchoolType(Long recruitSchoolId);

    /**
     * 新增生源学校信息
     *
     * @param sourceSchoolQuery 生源学校信息
     * @param recruitSchoolId   招生学校id
     * @param userName          用户名
     * @param userId            用户id
     * @return com.insi.educiot.recruit.common.base.Result
     * @author Pan Juncai
     * @date 2020/4/10 16:14
     */
    Result addSourceSchool(AddSourceSchoolQuery sourceSchoolQuery, Long recruitSchoolId, String userName, Long userId);

    /**
     * 编辑生源学校信息
     *
     * @param sourceSchoolQuery 要修改的信息
     * @return com.insi.educiot.recruit.common.base.Result
     * @author Pan Juncai
     * @date 2020/4/10 16:34
     */
    Result updateSourceSchool(UpdateSourceSchoolQuery sourceSchoolQuery);

    /**
     * 根据生源学校ID查询生源学校详情
     *
     * @param sourceSchoolId 要查询的生源学校ID
     * @return com.educiot.recruit.data.entity.vo.SourceSchoolDetailVO
     * @author Pan Juncai
     * @date 2020/4/16 11:02
     */
    SourceSchoolDetailVO getSourceSchoolDetail(Long sourceSchoolId);

    /**
     * 删除生源学校（修改状态）
     *
     * @param sourceSchoolId 要删除的生源学校id
     * @return com.insi.educiot.recruit.common.base.Result
     * @author Pan Juncai
     * @date 2020/4/10 16:43
     */
    Result deleteSourceSchool(Long sourceSchoolId);

    /**
     * 获取所有指定招生学校下的生源学校
     *
     * @param recruitSchoolId 招生学校id
     * @return java.util.List&lt;com.educiot.recruit.data.entity.vo.SourceSchoolVO&gt;
     * @author Pan Juncai
     * @date 2020/4/17 17:42
     */
    List<SourceSchoolVO> listAllSourceSchool(Long recruitSchoolId);

    /**
     * 查询学校列表信息
     * @param recruitSchoolId 招生学校ID
     * @param groupId 分组ID
     * @return
     */
    List<SourceSchoolVO> listSourceSchoolByRecruitSchoolIdAndGroupId(Long recruitSchoolId,Long groupId,Long chargerId,Long semesterId);

    /**
     * 查询某个分组下负责的生源学校完成度统计
     *
     * @param groupId 分组id
     * @return java.util.List&lt;com.educiot.recruit.data.entity.vo.CompleteRateVO&gt;
     * @author Pan Juncai
     * @date 2020/4/22 17:44
     */
    List<CompleteRateVO> listCompleteRate(Long groupId,Long currentSemesterId);
}
