package com.educiot.recruit.data.service.preach.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.educiot.common.base.KeyValue;
import com.educiot.recruit.data.entity.preach.PreachSchoolRelation;
import com.educiot.recruit.data.mapper.preach.PreachSchoolRelationMapper;
import com.educiot.recruit.data.service.preach.IPreachSchoolRelationService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 宣讲生源学校关联表 服务实现类
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-13
 */
@Service
public class PreachSchoolRelationServiceImpl extends ServiceImpl<PreachSchoolRelationMapper, PreachSchoolRelation> implements IPreachSchoolRelationService {


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
