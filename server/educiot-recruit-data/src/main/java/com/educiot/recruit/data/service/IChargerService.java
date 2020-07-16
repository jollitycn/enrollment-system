package com.educiot.recruit.data.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.educiot.recruit.data.entity.Charger;
import com.educiot.recruit.data.entity.ext.ChargerExt;

import java.util.List;

/**
 * <p>
 * 招生负责人 服务类
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
public interface IChargerService extends IService<Charger> {
    ChargerExt getByApiId(Long apiId, Long semesterId);

    List<Charger> getAllChargerList(Long semesterId);
}
