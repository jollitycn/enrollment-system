package com.educiot.recruit.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.educiot.recruit.data.entity.InSchool;
import com.educiot.recruit.data.entity.vo.InSchoolClassInfoVO;
import com.educiot.recruit.data.entity.vo.InSchoolVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 学生在校信息表 Mapper 接口
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-13
 */
public interface InSchoolMapper extends BaseMapper<InSchool> {

    /**
     * 根据学生基本信息ID查询学生在校信息
     *
     * @param studentInfoId
     * @return
     */
    InSchoolVO queryInSchoolInfo(@Param("studentInfoId") Long studentInfoId, @Param("recruitSchoolId") Long recruitSchoolId);

    InSchoolClassInfoVO getSourceSchoolInfo(@Param("studentInfoId") Long studentInfoId, @Param("recruitSchoolId") Long recruitSchoolId);
}
