package com.educiot.recruit.data.mapper;

import com.educiot.recruit.data.entity.Progress;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.educiot.recruit.data.entity.vo.ProgressListVO;
import com.educiot.recruit.data.entity.vo.ProgressVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 学生报名进度表 Mapper 接口
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-13
 */
public interface ProgressMapper extends BaseMapper<Progress> {

    /**
     *根据公关ID查询学生报名进度信息
     * @param studentPublicRelationId 学生报名进度Id
     * @return 返回学生报名进度信息
     */
    ProgressVO queryProgressInfo(@Param("studentPublicRelationId")Long studentPublicRelationId);

    /**
     * 查询学生报名进度信息
     * @param studentInfoId 学生信息Id
     * @return 学生报名进度信息
     */
    ProgressVO queryProgressInfoByStudentId(@Param("studentInfoId") Long studentInfoId);

    /**
     * 获取学生的报名进度
     * @param studentPublicRelationId
     * @return List<ProgressListVO>
     */
    List<ProgressListVO> getProgress(@Param("studentPublicRelationId") Long studentPublicRelationId);
}
