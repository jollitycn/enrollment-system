package com.educiot.recruit.data.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.educiot.recruit.data.entity.LoginRelation;
import com.educiot.recruit.data.entity.vo.StudentIdAndNameVO;
import com.educiot.recruit.data.mapper.LoginRelationMapper;
import com.educiot.recruit.data.service.ILoginRelationService;
import com.educiot.recruit.data.service.student.IStudentInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 登录信息关联表 服务实现类
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-28
 */
@Service
public class LoginRelationServiceImpl extends ServiceImpl<LoginRelationMapper, LoginRelation> implements ILoginRelationService {

    @Resource
    private IStudentInfoService studentInfoService;

    @Override
    public List<StudentIdAndNameVO> listFocusStudent(Long login) {
        List<StudentIdAndNameVO> list = baseMapper.listFocusStudent(login);
        return list;
    }

    @Override
    public Integer queryLoginType(Long studentInfo) {
        return baseMapper.queryLoginType(studentInfo);
    }
}
