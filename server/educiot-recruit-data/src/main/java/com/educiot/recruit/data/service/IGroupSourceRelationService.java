package com.educiot.recruit.data.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.educiot.recruit.data.entity.GroupSourceRelation;
import com.educiot.recruit.data.entity.ext.GroupSourceRelationExt;
import com.educiot.recruit.data.entity.query.ListSourceSchoolPageQuery;
import com.educiot.recruit.data.entity.vo.CompleteRateVO;
import com.educiot.recruit.data.entity.vo.ListSourceSchoolVO;

import java.util.List;

/**
 * <p>
 * 招生组生源学校关联表 服务类
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
public interface IGroupSourceRelationService extends IService<GroupSourceRelation> {

    GroupSourceRelationExt getByGroupIdAndSourceSchoolId(Long groupId, Long sourceSchoolId);

    IPage<ListSourceSchoolVO> listSourceSchoolPage(ListSourceSchoolPageQuery param, Long schoolId, Long semesterId);

    List<ListSourceSchoolVO> listSourceSchoolVOResult(Long groupId, Long currentSemesterId);
}