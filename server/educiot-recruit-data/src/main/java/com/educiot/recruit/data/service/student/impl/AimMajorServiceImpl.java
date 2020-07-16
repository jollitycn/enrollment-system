package com.educiot.recruit.data.service.student.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.educiot.recruit.data.entity.AimMajor;
import com.educiot.recruit.data.entity.vo.AimMajorInfoVO;
import com.educiot.recruit.data.mapper.AimMajorMapper;
import com.educiot.recruit.data.service.student.IAimMajorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 选择专业表 服务实现类
 * </p>
 *
 * @author Mr.AI
 * @since 2020-04-13
 */
@Service
public class AimMajorServiceImpl extends ServiceImpl<AimMajorMapper, AimMajor> implements IAimMajorService {

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public Boolean saveOrUpdateAimMajorInfo(AimMajorInfoVO aimMajorInfoVO) {
        Boolean aBoolean = this.deleteAimMajorInfo(aimMajorInfoVO.getStudentPublicRelationId());
        if (aimMajorInfoVO.getAimMajors().size() == 0) {
            return false;
        }
        aimMajorInfoVO.getAimMajors().forEach(AimMajor -> {
            AimMajor.setStudentPublicRelationId(aimMajorInfoVO.getStudentPublicRelationId());
        });
        if (aBoolean) {
            saveBatch(aimMajorInfoVO.getAimMajors());
            return true;
        } else {
            return false;
        }

    }

    @Override
    public Boolean deleteAimMajorInfo(Long studentPublicRelationId) {
        if (studentPublicRelationId == null) {
            return false;
        }
        QueryWrapper<AimMajor> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_public_relation_id", studentPublicRelationId);
        remove(queryWrapper);
        return true;
    }

    @Override
    public List<AimMajor> queryAimMajorInfo(Long studentInfoId, Long schoolId) {
        if (null == studentInfoId) {
            return null;
        }
        List<AimMajor> aimMajor = baseMapper.queryAimMajorInfo(studentInfoId, schoolId);
        return aimMajor;
    }
}
