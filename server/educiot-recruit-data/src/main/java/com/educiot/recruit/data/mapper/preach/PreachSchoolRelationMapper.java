package com.educiot.recruit.data.mapper.preach;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.educiot.common.base.KeyValue;
import com.educiot.recruit.data.entity.preach.PreachSchoolRelation;

import java.util.List;

/**
 * <p>
 * 宣讲生源学校关联表 Mapper 接口
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-13
 */
public interface PreachSchoolRelationMapper extends BaseMapper<PreachSchoolRelation> {

    List<String> listByPreachPlanId(Long id);

    List<KeyValue<String, String>> listKeyValueByPreachPlanId(Long id);
}
