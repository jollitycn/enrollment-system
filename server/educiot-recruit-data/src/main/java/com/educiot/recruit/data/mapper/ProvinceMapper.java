package com.educiot.recruit.data.mapper;

import com.educiot.recruit.data.entity.Province;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.educiot.recruit.data.entity.vo.ProvinceVO;
import com.educiot.recruit.data.entity.vo.SysRegionVO;

import java.util.List;

/**
 * <p>
 * 省市区县 Mapper 接口
 * </p>
 *
 * @author LiuHao
 * @since 2020-06-12
 */
public interface ProvinceMapper extends BaseMapper<Province> {

    List<ProvinceVO> listByParentID(long parentID);
}
