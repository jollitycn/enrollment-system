package com.educiot.recruit.data.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.educiot.recruit.data.entity.AimStudent;
import com.educiot.recruit.data.entity.query.AddAimStudentQuery;
import com.educiot.recruit.data.entity.query.ListAimStudentPageQuery;
import com.educiot.recruit.data.entity.vo.AimStudentVO;
import com.educiot.recruit.data.mapper.AimStudentMapper;
import com.educiot.recruit.data.service.IAimStudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 * 目标生表 服务实现类
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-13
 */
@Service
public class AimStudentServiceImpl extends ServiceImpl<AimStudentMapper, AimStudent> implements IAimStudentService {

    @Override
    public IPage<AimStudent> listAimStudentPage(ListAimStudentPageQuery param) {

        //构建page对象
        IPage<AimStudent> page = new Page<>(param.getPageNum(), param.getPageSize());
        return baseMapper.listAimStudentPage(page,param);
//        //判断ClassId是否可用
//        boolean b = CommonUtil.checkId(param.getClassId());
//
//        if(b){
//
//        }else{
//            return null;
//        }
    }

    @Override
    public int listAimStudentCount(ListAimStudentPageQuery param) {
        return baseMapper.listAimStudentCount(param);
    }

    @Override
    public Boolean saveOrUpdateAimStudentInfo(AddAimStudentQuery addAimStudentQuery) {
        if(null == addAimStudentQuery){
            return false;
        }
        AimStudent aimStudent=new AimStudent();
        if(null ==  addAimStudentQuery.getAimStudentId()){
            LocalDateTime now = LocalDateTime.now();
            addAimStudentQuery.setCreateTime(now);
        }
        BeanUtils.copyProperties(addAimStudentQuery,aimStudent);
        boolean b = saveOrUpdate(aimStudent);
        return b;
    }

    @Override
    public AimStudentVO queryAimStudentInfo(Long aimStudentId) {
        if(null == aimStudentId){
            return null;
        }
        AimStudentVO aimStudentVO = baseMapper.queryAimStudentInfo(aimStudentId);
        return aimStudentVO;
    }
}
