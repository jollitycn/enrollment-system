package com.educiot.recruit.data.mapper;

import com.educiot.recruit.data.entity.College;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.educiot.recruit.data.entity.vo.CollegeAndMajorVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 招生院系表 Mapper 接口
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
public interface CollegeMapper extends BaseMapper<College> {

    List<CollegeAndMajorVO> queryCollegeAndMajor(@Param("planId") Long planId);

    int queryMajorAmount(@Param("majorId")Long majorId,@Param("recruitSchoolId")Long recruitSchoolId,@Param("semesterId")Long semesterId);
}
