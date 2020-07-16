package com.educiot.recruit.data.mapper.preach;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.educiot.recruit.common.base.KeyValue;
import com.educiot.recruit.data.entity.preach.PreachAssistantRelation;

import java.util.List;

/**
 * <p>
 * 宣讲协助人关联表 Mapper 接口
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-13
 */
public interface PreachAssistantRelationMapper extends BaseMapper<PreachAssistantRelation> {

    List<String> listByPreachPlanId(Long id);

    List<KeyValue<String, String>> listKeyValueByPreachPlanId(Long id);
}
