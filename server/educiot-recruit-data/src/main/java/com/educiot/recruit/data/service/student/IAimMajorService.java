package com.educiot.recruit.data.service.student;

import com.baomidou.mybatisplus.extension.service.IService;
import com.educiot.recruit.data.entity.AimMajor;
import com.educiot.recruit.data.entity.vo.AimMajorInfoVO;

import java.util.List;

/**
 * <p>
 * 选择专业表 服务类
 * </p>
 *
 * @author Mr.AI
 * @since 2020-04-13
 */
public interface IAimMajorService extends IService<AimMajor> {


    /**
     * 添加或修改选择专业信息
     * @param aimMajorInfoVO 添加或编辑选择专业实体类列表
     * @return 返回添加或编辑是否成功状态
     */
    Boolean saveOrUpdateAimMajorInfo(AimMajorInfoVO aimMajorInfoVO);

    /**
     * 根据学生ID查询学生选择的专业信息
     * @param studentInfoId 学生ID
     * @return 返回专业信息
     */
    List<AimMajor> queryAimMajorInfo(Long studentInfoId ,Long schoolId);

    /**
     * 根据公关ID删除选择专业信息
     * @return
     */
    Boolean deleteAimMajorInfo(Long studentPublicRelationId);

}
