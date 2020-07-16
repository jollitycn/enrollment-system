package com.educiot.recruit.data.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.educiot.recruit.common.base.MyExcption;
import com.educiot.recruit.common.base.Result;
import com.educiot.recruit.data.entity.Class;
import com.educiot.recruit.data.entity.query.*;
import com.educiot.recruit.data.entity.vo.ClassIdAndNameVO;
import com.educiot.recruit.data.entity.vo.ClassVO;

import java.util.List;

/**
 * <p>
 * 班级表 服务类
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
public interface IClassService extends IService<Class> {

    /**
     * 分页获取生源学校下的班级列表
     *
     * @param pageQuery 条件
     * @return com.baomidou.mybatisplus.core.metadata.IPage&lt;com.educiot.recruit.data.entity.vo.ClassVO&gt;
     * @author Pan Juncai
     * @date 2020/4/23 10:44
     */
    IPage<ClassVO> listSchoolClassPage(ListClassPageQuery pageQuery, Long semesterId) throws MyExcption;

    /**
     * 新增班级
     *
     * @param classQuery 班级信息
     * @param userId     创建人id
     * @param userName   创建人名称
     * @return com.insi.educiot.recruit.common.base.Result
     * @author Pan Juncai
     * @date 2020/4/23 10:54
     */
    Result addClass(AddClassQuery classQuery, Long userId, String userName, Long semesterId) throws MyExcption;

    /**
     * 获取班级信息
     *
     * @param classId 班级id
     * @return com.educiot.recruit.data.entity.vo.ClassVO
     * @author Pan Juncai
     * @date 2020/4/23 11:04
     */
    ClassVO getClassInfo(Long classId, Long semesterId) throws MyExcption;

    /**
     * 获取指定生源学校下的所有班级
     *
     * @param sourceSchoolId 生源学校id
     * @return java.util.List&lt;com.educiot.recruit.data.entity.vo.ClassIdAndNameVO&gt;
     * @author Pan Juncai
     * @date 2020/4/29 17:36
     */
    List<ClassIdAndNameVO> listClassBySource(ListClassBySourceQuery sourceSchoolId);

    /**
     * 编辑班级信息
     *
     * @param updateClassQuery 修改的信息
     * @return com.educiot.recruit.common.base.Result
     * @author Pan Juncai
     * @date 2020/5/8 10:03
     */
    Result updateClass(UpdateClassQuery updateClassQuery);

    /**
     * 删除班级信息
     * @param classId
     * @return
     */
    Result deleteClass(Long classId);
}
