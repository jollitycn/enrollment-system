package com.educiot.recruit.data.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.educiot.recruit.common.base.CodeMsg;
import com.educiot.recruit.common.base.Result;
import com.educiot.recruit.data.entity.Target;
import com.educiot.recruit.data.entity.TargetDecomposition;
import com.educiot.recruit.data.mapper.TargetDecompositionMapper;
import com.educiot.recruit.data.service.ITargetDecompositionService;
import com.educiot.recruit.data.service.ITargetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 招生目标分解(树形表)  服务实现类
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
@Service
public class TargetDecompositionServiceImpl extends ServiceImpl<TargetDecompositionMapper, TargetDecomposition> implements ITargetDecompositionService {

    @Autowired
    private ITargetService targetService;

    @Override
    public Result queryTargetAmountByNodeId(String nodeId) {
        if (null == nodeId || "".equals(nodeId)) {
            return Result.error(CodeMsg.PARAMETER_ISNULL);
        }
        Integer targetAmount = baseMapper.selectByNodeId(nodeId);
        return Result.success(targetAmount);
    }

    /**
     * hongsifan 增加获取线下渠道区域目标列表
     *
     * @param schoolId
     * @param semesterId
     * @return
     */
    @Override
    public List<TargetDecomposition> getOfflineGroupInfoList(Long schoolId, Long semesterId) {

        List<TargetDecomposition> targetDecompositions = null;
        QueryWrapper<Target> wrapper = new QueryWrapper<>();
        wrapper.eq("semester_id", semesterId);
        wrapper.eq("recruit_school_id", schoolId);
        Target bean = targetService.getOne(wrapper);
        if (bean != null) {
            //获取线下的信息
            QueryWrapper<TargetDecomposition> targetDecompositionQueryWrapper = new QueryWrapper<>();
            targetDecompositionQueryWrapper.eq("target_id", bean.getTargetId());
            targetDecompositionQueryWrapper.eq("type", 0);
            targetDecompositionQueryWrapper.eq("parent_id", 0);
            TargetDecomposition targetDecomposition = baseMapper.selectOne(targetDecompositionQueryWrapper);
            targetDecompositionQueryWrapper = new QueryWrapper<>();
            targetDecompositionQueryWrapper.eq("parent_id", targetDecomposition.getTargetDecompositionId());
            targetDecompositions = baseMapper.selectList(targetDecompositionQueryWrapper);

        }
        return targetDecompositions;
    }
}
