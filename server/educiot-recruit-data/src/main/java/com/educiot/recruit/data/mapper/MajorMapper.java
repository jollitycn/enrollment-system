package com.educiot.recruit.data.mapper;

import com.educiot.recruit.data.entity.Major;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.educiot.recruit.data.entity.vo.MajorVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 招生专业表 Mapper 接口
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
public interface MajorMapper extends BaseMapper<Major> {

    /**
     * 查看招生专业信息
     * @param majorId
     * @return
     */
    MajorVO queryMajorInfoByMajorId(@Param("majorId") String majorId);


    /**
     * 查询院系下面所有专业的招生人数总和
     * @param collegeId
     * @return
     */
    Integer queryAmountCountByCollegeId(@Param("collegeId") String collegeId);

    /**
     * 查询目标人数
     * @return 人数
     */
    Integer queryTargetAmount(@Param("recruitSchoolId")Long recruitSchoolId,@Param("semesterId")Long semesterId,@Param("nodeId")String nodeId);

}
