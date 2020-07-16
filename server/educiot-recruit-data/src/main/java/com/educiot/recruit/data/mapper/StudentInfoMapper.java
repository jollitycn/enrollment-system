package com.educiot.recruit.data.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.educiot.recruit.data.entity.Plan;
import com.educiot.recruit.data.entity.StudentInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.educiot.recruit.data.entity.query.ListPlanPageQuery;
import com.educiot.recruit.data.entity.query.ListStudentPageByClassIdQuery;
import com.educiot.recruit.data.entity.query.ListStudentPageQuery;
import com.educiot.recruit.data.entity.query.StudentQuery;
import com.educiot.recruit.data.entity.vo.ListSourceSchoolVO;
import com.educiot.recruit.data.entity.vo.StudentInfoVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 学生基本信息表 Mapper 接口
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-13
 */
public interface StudentInfoMapper extends BaseMapper<StudentInfo> {

    /**
     * 分页查询学生列表信息
     * @param page 分页信息
     * @param param 查询条件
     * @return
     */
    List<StudentInfoVO> listStudentPage(IPage<StudentInfoVO> page, @Param("param") ListStudentPageQuery param);

    /**
     * 查询学生数量
     * @param param 查询条件
     * @return
     */
    int listStudentCount(@Param("param") ListStudentPageQuery param);

    /**
     * 获取负责人负责的生源学校
     * @return 生源学校列表信息
     */
    List<ListSourceSchoolVO> listSourceSchool(@Param("chargerId") Long chargerId);


    /**
     * 查询学生信息详情，返回公关ID
     * @param studentInfoId
     * @param schoolId
     * @return
     */
    StudentInfoVO queryByStudentInfoId(@Param("studentInfoId") Long studentInfoId,@Param("schoolId") Long schoolId);

    /**
     * 判断学生是否重复
     * @param studentQuery
     * @return
     */
    String queryStudentCount(@Param("studentQuery")StudentQuery studentQuery);


    List<StudentInfoVO> listStudentInfoPageByClassId(IPage<StudentInfoVO> page, ListStudentPageByClassIdQuery param,Long semesterId);
}
