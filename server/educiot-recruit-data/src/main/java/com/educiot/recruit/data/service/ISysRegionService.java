package com.educiot.recruit.data.service;

import com.educiot.recruit.data.entity.SysRegion;
import com.baomidou.mybatisplus.extension.service.IService;
import com.educiot.recruit.data.entity.vo.SysRegionVO;

import java.util.List;

/**
 * <p>
 * 系统地区表 服务类
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
public interface ISysRegionService extends IService<SysRegion> {

    List<SysRegionVO> getAllList();
}
