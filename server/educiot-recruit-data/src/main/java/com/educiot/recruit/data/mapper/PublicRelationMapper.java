package com.educiot.recruit.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.educiot.recruit.data.entity.PublicRelation;
import com.educiot.recruit.data.entity.query.common.UpdateStatusQuery;
import com.educiot.recruit.data.entity.query.publicRelation.PublicRelationPageQuery;
import com.educiot.recruit.data.entity.vo.publicRelation.PublicRelationGroupCountVO;
import com.educiot.recruit.data.entity.vo.publicRelation.PublicRelationVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 学校公关表 Mapper 接口
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-13
 */
public interface PublicRelationMapper extends BaseMapper<PublicRelation> {

    void updateStatus(UpdateStatusQuery query);

    IPage<PublicRelationVO> listPage(IPage<PublicRelationVO> page, long userId, PublicRelationPageQuery query);

    IPage<PublicRelationGroupCountVO> listPageGroupCountByUserId(IPage<PublicRelationGroupCountVO> page, @Param("groupId") Long groupId, @Param("semesterId") Long semesterId);
}
