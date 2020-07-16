package com.educiot.recruit.data.mapper;

import com.educiot.recruit.data.entity.AimMajor;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 选择专业表 Mapper 接口
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-13
 */
public interface AimMajorMapper extends BaseMapper<AimMajor> {

    /**
     * 根据学生ID查询学生选择的专业信息
     * @param studentInfoId 学生Id
     * @return 选择专业信息
     */
    List<AimMajor> queryAimMajorInfo(@Param("studentInfoId") Long studentInfoId,@Param("schoolId") Long schoolId);

}
