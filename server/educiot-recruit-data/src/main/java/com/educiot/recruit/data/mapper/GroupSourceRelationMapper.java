package com.educiot.recruit.data.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.educiot.recruit.data.entity.GroupSourceRelation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.educiot.recruit.data.entity.query.ListSourceSchoolPageQuery;
import com.educiot.recruit.data.entity.vo.ListSourceSchoolVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 招生组生源学校关联表 Mapper 接口
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
public interface GroupSourceRelationMapper extends BaseMapper<GroupSourceRelation> {

    List<ListSourceSchoolVO> listGroupInfoPage(IPage<ListSourceSchoolVO> page, ListSourceSchoolPageQuery param, Long recruitSchoolId, Long semesterId);

    List<ListSourceSchoolVO> listSourceSchoolVOResult(@Param("groupId") Long groupId, @Param("currentSemesterId") Long currentSemesterId);
}
