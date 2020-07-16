package com.educiot.recruit.data.service.preach.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.educiot.recruit.common.base.KeyValue;
import com.educiot.recruit.data.entity.preach.PreachAssistantRelation;
import com.educiot.recruit.data.mapper.preach.PreachAssistantRelationMapper;
import com.educiot.recruit.data.service.preach.IPreachAssistantRelationService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 宣讲协助人关联表 服务实现类
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-13
 */
@Service
public class PreachAssistantRelationServiceImpl extends ServiceImpl<PreachAssistantRelationMapper, PreachAssistantRelation> implements IPreachAssistantRelationService {

    @Override
    public List<String> listByPreachPlanId(Long id) {
        List<String> list = baseMapper.listByPreachPlanId(id);
        return list;
    }

    @Override
    public List<KeyValue<String, String>> listKeyValueByPreachPlanId(Long id) {
        List<KeyValue<String, String>> list = baseMapper.listKeyValueByPreachPlanId(id);
        return list;
    }
}
