package com.educiot.recruit.data.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.educiot.common.constant.StatusEnum;
import com.educiot.recruit.data.entity.Charger;
import com.educiot.recruit.data.entity.ext.ChargerExt;
import com.educiot.recruit.data.mapper.ChargerMapper;
import com.educiot.recruit.data.service.IChargerService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 招生负责人 服务实现类
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
@Service
public class ChargerServiceImpl extends ServiceImpl<ChargerMapper, Charger> implements IChargerService {

    /**
     * @param apiId
     * @return
     */
    @Override
    public ChargerExt getByApiId(Long apiId, Long semesterId) {
        log.debug("getByApiId" + apiId + "," + semesterId);
        return baseMapper.getByApiId(apiId, semesterId, StatusEnum.NORMAL.getStatus());
    }

    @Override
    public List<Charger> getAllChargerList(Long semesterId) {
        return baseMapper.getAllChargerList(semesterId);
    }
}
