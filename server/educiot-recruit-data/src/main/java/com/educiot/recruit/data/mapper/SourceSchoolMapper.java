package com.educiot.recruit.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.educiot.recruit.data.entity.SourceSchool;
import com.educiot.recruit.data.entity.query.ListSourceSchoolPageQuery;
import com.educiot.recruit.data.entity.vo.CompleteRateVO;
import com.educiot.recruit.data.entity.vo.ListSourceSchoolVO;
import com.educiot.recruit.data.entity.vo.SourceSchoolVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 生源学校 Mapper 接口
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
public interface SourceSchoolMapper extends BaseMapper<SourceSchool> {
    /**
     * 分页获取招生分组信息列表
     *
     * @param page            分页信息
     * @param param           页面的查询条件
     * @param recruitSchoolId 招生学校id
     * @return java.util.List&lt;com.educiot.recruit.data.entity.vo.ListSourceSchoolVO&gt;
     * @author Pan Juncai
     * @date 2020/4/10 10:34
     */
    List<ListSourceSchoolVO> listGroupInfoPage(IPage<ListSourceSchoolVO> page,
                                               @Param("param") ListSourceSchoolPageQuery param,
                                               @Param("recruitSchoolId") Long recruitSchoolId);

    List<ListSourceSchoolVO> listGroupInfoWeChat(IPage<ListSourceSchoolVO> page,
                                               @Param("param") ListSourceSchoolPageQuery param,
                                               @Param("recruitSchoolId") Long recruitSchoolId);
    /**
     * 根据生源学校id集合查询生源学校信息
     *
     * @param sourceSchoolIds 生源学校信息集合
     * @return java.util.List&lt;com.educiot.recruit.data.entity.vo.SourceSchoolVO&gt;
     * @author Pan Juncai
     * @date 2020/4/15 17:29
     */
    List<SourceSchoolVO> listSourceSchoolByIds(@Param("sourceSchoolIds") List<Long> sourceSchoolIds);

    /**
     * 获取所有指定招生学校下的生源学校
     *
     * @param recruitSchoolId 招生学校ID
     * @return java.util.List&lt;com.educiot.recruit.data.entity.vo.SourceSchoolVO&gt;
     * @author Pan Juncai
     * @date 2020/4/17 17:42
     */
    List<SourceSchoolVO> listSourceSchoolByRecruitSchoolId(@Param("recruitSchoolId") Long recruitSchoolId,@Param("semesterId")Long semesterId);

    /**
     * 查询学校列表信息
     * @param recruitSchoolId 招生学校ID
     * @param groupId 分组ID
     * @return
     */
    List<SourceSchoolVO> listSourceSchoolByRecruitSchoolIdAndGroupId(@Param("recruitSchoolId") Long recruitSchoolId,@Param("groupId") Long groupId,@Param("chargerId")Long chargerId,@Param("semesterId")Long semesterId);

    /**
     * 查询某个分组下负责的生源学校完成度统计
     *
     * @param groupId 分组id
     * @return java.util.List&lt;com.educiot.recruit.data.entity.SourceSchool&gt;
     * @author Pan Juncai
     * @date 2020/4/22 17:53
     */
    List<SourceSchool> listCompleteRate(@Param("groupId")Long groupId,@Param("currentSemesterId")Long currentSemesterId);
}
