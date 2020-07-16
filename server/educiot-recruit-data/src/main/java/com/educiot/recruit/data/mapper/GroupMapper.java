package com.educiot.recruit.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.educiot.recruit.data.entity.Group;
import com.educiot.recruit.data.entity.query.ListGroupPageQuery;
import com.educiot.recruit.data.entity.vo.GroupVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 招生组表 Mapper 接口
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
public interface GroupMapper extends BaseMapper<Group> {

    /**
     * 分页获取招生分组信息列表
     *
     * @param page  分页信息
     * @param param 页面的查询条件
     * @return java.util.List&lt;com.educiot.recruit.data.entity.vo.GroupVO&gt;
     * @author Pan Juncai
     * @date 2020/4/10 10:34
     */
    List<GroupVO> listGroupPage(IPage<GroupVO> page, @Param("param") ListGroupPageQuery param);

    /**
     * @param recruitSchoolId
     * @param semesterId
     * @Description: 根据学校id和学期id查询招生区域信息
     * @return:Group
     * @Author: XuChao
     * @Date: 2020/4/15
     */
    List<Group> getTargetUnderLineData(@Param("recruitSchoolId")Long recruitSchoolId,
                                       @Param("semesterId")Long semesterId);


    /**
     *
     * @param recruitSchoolId
     * @param semesterId
     * @return
     */
    Integer queryGroupCount(@Param("recruitSchoolId")Long recruitSchoolId,
                            @Param("semesterId")Long semesterId,@Param("name")String name);
}
