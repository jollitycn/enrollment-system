package com.educiot.recruit.data.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.educiot.common.constant.Constant;
import com.educiot.recruit.data.common.MyServiceImpl;
import com.educiot.recruit.data.entity.SysRegion;
import com.educiot.recruit.data.entity.vo.SysRegionVO;
import com.educiot.recruit.data.mapper.SysRegionMapper;
import com.educiot.recruit.data.service.ISysRegionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 系统地区表 服务实现类
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-13
 */
@Service
public class SysRegionServiceImpl extends MyServiceImpl<SysRegionMapper, SysRegion> implements ISysRegionService {

    @Override
    public List<SysRegionVO> getAllList() {
        List<SysRegionVO> result = null;
        if (exists(Constant.RedisKey.SYS_REGIONS)) {
            String test = getCache(Constant.RedisKey.SYS_REGIONS);
            result = JSONObject.parseArray(test, SysRegionVO.class);
            return result;
        }
//        QueryWrapper<SysRegion> wrapper = new QueryWrapper<>();
//        wrapper.eq("parent_id", 100000);
        result = baseMapper.listByParentID(100000L);


//        List<SysRegionVO> result = new ArrayList<>();
//        for (SysRegion sysRegion : sysRegions) {
//            SysRegionVO sysRegionVO = new SysRegionVO();
//            BeanUtils.copyProperties(sysRegion, sysRegionVO);
//            result.add(sysRegionVO);
//        }
//
        List<SysRegionVO> finalResult = result;
        result.forEach(levelOneMenu -> levelOneMenu.setSubs(listChildRegion(finalResult, levelOneMenu)));
        return result;
    }

    //
    private List<SysRegionVO> listChildRegion(List<SysRegionVO> result, SysRegionVO levelOneMenu) {

//        QueryWrapper<SysRegion> wrapper = new QueryWrapper<>();
//        wrapper.eq("parent_id", parentId);
        // List<SysRegionVO> child = baseMapper.listByParentID(parentId);
        List<SysRegionVO> childs = new ArrayList<>();
        for (SysRegionVO vo :
                result) {
            if (vo.getParentId().equals(levelOneMenu.getRegionId())) {
                childs.add(vo);
            }
        }
        // levelOneMenu.setSubs(childs);
//        List<SysRegionVO> child = new ArrayList<>();
//        for (SysRegion sysRegion : sysRegions) {
//            SysRegionVO sysRegionVO = new SysRegionVO();
//            BeanUtils.copyProperties(sysRegion, sysRegionVO);
//            child.add(sysRegionVO);
//        }

        childs.forEach(region -> region.setSubs(listChildRegion(result, region)));
        return childs;
    }
}
