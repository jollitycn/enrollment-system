package com.educiot.recruit.data.service.preach;

import com.baomidou.mybatisplus.extension.service.IService;
import com.educiot.common.base.KeyValue;
import com.educiot.recruit.data.entity.preach.PreachSchoolRelation;

import java.util.List;

/**
 * <p>
 * 宣讲生源学校关联表 服务类
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
public interface IPreachSchoolRelationService extends IService<PreachSchoolRelation> {

    List<String> listByPreachPlanId(Long id);

    List<KeyValue<String, String>> listKeyValueByPreachPlanId(Long id);
}
