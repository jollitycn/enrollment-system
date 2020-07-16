package com.educiot.recruit.data.service.preach;

import com.baomidou.mybatisplus.extension.service.IService;
import com.educiot.recruit.common.base.KeyValue;
import com.educiot.recruit.data.entity.preach.PreachAssistantRelation;

import java.util.List;

/**
 * <p>
 * 宣讲协助人关联表 服务类
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-13
 */
public interface IPreachAssistantRelationService extends IService<PreachAssistantRelation> {

    List<String> listByPreachPlanId(Long id);

    List<KeyValue<String, String>> listKeyValueByPreachPlanId(Long id);
}
