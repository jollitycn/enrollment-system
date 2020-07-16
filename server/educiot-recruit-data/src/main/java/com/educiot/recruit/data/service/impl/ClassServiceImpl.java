package com.educiot.recruit.data.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.educiot.common.base.CodeMsg;
import com.educiot.common.base.MyExcption;
import com.educiot.common.base.Result;
import com.educiot.recruit.data.common.ValidateUtil;
import com.educiot.recruit.data.entity.Class;
import com.educiot.recruit.data.entity.query.*;
import com.educiot.recruit.data.entity.vo.ClassIdAndNameVO;
import com.educiot.recruit.data.entity.vo.ClassVO;
import com.educiot.recruit.data.mapper.ClassMapper;
import com.educiot.recruit.data.service.IAimStudentService;
import com.educiot.recruit.data.service.IClassService;
import com.educiot.recruit.data.service.student.IStudentInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 班级表 服务实现类
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-13
 */
@Service
public class ClassServiceImpl extends ServiceImpl<ClassMapper, Class> implements IClassService {

    @Resource
    private IAimStudentService aimStudentService;

    @Resource
    private IStudentInfoService studentInfoService;

    @Override
    public IPage<ClassVO> listSchoolClassPage(ListClassPageQuery pageQuery, Long semesterId) throws MyExcption {
        ValidateUtil.validateSemester(semesterId);
        IPage<ClassVO> page = new Page<>(pageQuery.getPageNum(), pageQuery.getPageSize());
        List<ClassVO> list = baseMapper.listSchoolClassPage(page, pageQuery, semesterId);
        page.setRecords(list);
        return page;
    }

    @Override
    public Result addClass(AddClassQuery classQuery, Long userId, String userName, Long semesterId) throws MyExcption {
        ValidateUtil.validateSemester(semesterId);
        Class c = new Class();
        BeanUtil.copyProperties(classQuery, c);
        // 获取登录信息
        c.setCreatorId(userId);
        c.setCreatorName(userName);
        c.setCreateTime(LocalDateTime.now());
        c.setSemesterId(semesterId);
        baseMapper.insert(c);
        return Result.success();
    }

    @Override
    public ClassVO getClassInfo(Long classId, Long semesterId) throws MyExcption {
        ValidateUtil.validateSemester(semesterId);
        ClassVO classVO = new ClassVO();
        Class c = baseMapper.selectById(classId);
        if (null == c) {
            return classVO;
        }
        BeanUtil.copyProperties(c, classVO);
        if (classVO.getClassId() != null) {
            ListAimStudentPageQuery listAimStudentPageQuery = new ListAimStudentPageQuery();
            listAimStudentPageQuery.setClassId(classVO.getClassId());
            listAimStudentPageQuery.setSemesterId(semesterId);
            classVO.setAimStudentCount(aimStudentService.listAimStudentCount(listAimStudentPageQuery));
            ListStudentPageQuery listStudentPageQuery = new ListStudentPageQuery();
            listStudentPageQuery.setClassId(classVO.getClassId());
            listStudentPageQuery.setSemesterId(semesterId);
            classVO.setStudentCount(studentInfoService.listStudentCount(listStudentPageQuery));
        }
        return classVO;
    }

    @Override
    public List<ClassIdAndNameVO> listClassBySource( ListClassBySourceQuery query) {
        return baseMapper.listClassBySource(query);
    }

    @Override
    public Result updateClass(UpdateClassQuery updateClassQuery) {
        // 查询该班级是否存在
        Class aClass = baseMapper.selectById(updateClassQuery.getClassId());
        if (null == aClass) {
            return Result.error(CodeMsg.CLASS_UN_EXIST);
        }

        // 修改信息
        Class updateClass = BeanUtil.copyProperties(updateClassQuery, Class.class);
        baseMapper.updateById(updateClass);

        return Result.success();
    }

    @Override
    public Result deleteClass(Long classId) {
        if (null == classId) {
            return Result.error(CodeMsg.PARAMETER_ISNULL);
        }
        baseMapper.deleteById(classId);
        return Result.success();
    }
}
