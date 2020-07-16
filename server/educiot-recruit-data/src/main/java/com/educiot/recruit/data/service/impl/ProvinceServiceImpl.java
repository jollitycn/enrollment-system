package com.educiot.recruit.data.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.educiot.recruit.common.constant.Constant;
import com.educiot.recruit.data.common.MyServiceImpl;
import com.educiot.recruit.data.entity.Province;
import com.educiot.recruit.data.entity.vo.ProvinceVO;
import com.educiot.recruit.data.entity.vo.ProvinceVO;
import com.educiot.recruit.data.mapper.ProvinceMapper;
import com.educiot.recruit.data.service.IProvinceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 省市区县 服务实现类
 * </p>
 *
 * @author LiuHao
 * @since 2020-06-12
 */
@Service
public class ProvinceServiceImpl extends MyServiceImpl<ProvinceMapper, Province> implements IProvinceService {

    @Override
    public List<ProvinceVO> getAllList() {
        List<ProvinceVO> result = null;
        if (exists(Constant.RedisKey.PROVINCE)) {
            result = getCacheArray(Constant.RedisKey.PROVINCE,ProvinceVO.class);
            return result;
        }
        result = baseMapper.listByParentID(1L);
        List<ProvinceVO> finalResult = result;
        List<ProvinceVO> firstResult = new ArrayList<>();
        result.forEach(levelOneMenu -> {if(levelOneMenu.getLevel()==1){ firstResult.add(levelOneMenu);}});
        firstResult.forEach(levelOneMenu -> levelOneMenu.setSubs(listChildRegion(finalResult, levelOneMenu)));
        return firstResult;
    }


    //
    private List<ProvinceVO> listChildRegion(List<ProvinceVO> result, ProvinceVO levelOneMenu) {

//        QueryWrapper<SysRegion> wrapper = new QueryWrapper<>();
//        wrapper.eq("parent_id", parentId);
         //List<ProvinceVO> childs = baseMapper.listByParentID(levelOneMenu.getId());
        List<ProvinceVO> childs = new ArrayList<>();
        for (ProvinceVO vo :
                result) {
            if (vo.getParentid().equals(levelOneMenu.getId())) {
                childs.add(vo);
            }
        }
         levelOneMenu.setSubs(childs);
//        List<ProvinceVO> child = new ArrayList<>();
//        for (SysRegion sysRegion : sysRegions) {
//            ProvinceVO ProvinceVO = new ProvinceVO();
//            BeanUtils.copyProperties(sysRegion, ProvinceVO);
//            child.add(ProvinceVO);
//        }

        childs.forEach(region -> region.setSubs(listChildRegion(result, region)));
        return childs;
    }
}
