package com.educiot.recruit.data.mapper;

import com.educiot.recruit.data.entity.TargetDecomposition;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 招生目标分解(树形表)  Mapper 接口
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
public interface TargetDecompositionMapper extends BaseMapper<TargetDecomposition> {

    /**
     * 获取目标人数
     * @param nodeId
     * @return
     */
    Integer selectByNodeId(String nodeId);
}
