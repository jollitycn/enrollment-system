package com.educiot.recruit.data.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.educiot.recruit.common.base.Result;
import com.educiot.recruit.data.entity.TargetDecomposition;

import java.util.List;

/**
 * <p>
 * 招生目标分解(树形表)  服务类
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
public interface ITargetDecompositionService extends IService<TargetDecomposition> {

    /**
     * 查询目标人数
     *
     * @param nodeId 节点编号
     * @return 返回目标人数
     */
    Result queryTargetAmountByNodeId(String nodeId);

    /**
     * @param schoolId
     * @param semesterId
     * @return
     */
    List<TargetDecomposition> getOfflineGroupInfoList(Long schoolId, Long semesterId);
}
