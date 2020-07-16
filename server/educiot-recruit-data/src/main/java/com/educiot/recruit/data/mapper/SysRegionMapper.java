package com.educiot.recruit.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.educiot.recruit.data.entity.SysRegion;
import com.educiot.recruit.data.entity.vo.SysRegionVO;

import java.util.List;

/**
 * <p>
 * 系统地区表 Mapper 接口
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-13
 */
public interface SysRegionMapper extends BaseMapper<SysRegion> {

    List<SysRegionVO> listByParentID(Long parentId);
}
