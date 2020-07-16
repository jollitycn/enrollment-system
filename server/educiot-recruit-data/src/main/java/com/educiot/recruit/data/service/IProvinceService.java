package com.educiot.recruit.data.service;

import com.educiot.recruit.data.entity.Province;
import com.baomidou.mybatisplus.extension.service.IService;
import com.educiot.recruit.data.entity.vo.ProvinceVO;
import com.educiot.recruit.data.entity.vo.SysRegionVO;

import java.util.List;

/**
 * <p>
 * 省市区县 服务类
 * </p>
 *
 * @author LiuHao
 * @since 2020-06-12
 */
public interface IProvinceService extends IService<Province> {

    List<ProvinceVO> getAllList();
}
