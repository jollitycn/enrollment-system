package com.educiot.recruit.data.service.student;

import com.baomidou.mybatisplus.extension.service.IService;
import com.educiot.recruit.common.base.MyExcption;
import com.educiot.recruit.common.base.Result;
import com.educiot.recruit.data.entity.InSchool;
import com.educiot.recruit.data.entity.query.AddInSchoolQuery;
import com.educiot.recruit.data.entity.vo.InSchoolClassInfoVO;
import com.educiot.recruit.data.entity.vo.InSchoolVO;

/**
 * <p>
 * 学生在校信息表 服务类
 * </p>
 *
 * @author Mr.AI
 * @since 2020-04-13
 */
public interface IInSchoolService extends IService<InSchool> {


    /**
     * 添加或修改学生在校信息
     * @param addInSchoolQuery 添加或编辑入参封装类
     * @return 返回添加或编辑是否成功状态
     */
    Result saveOrUpdateInSchoolInfo(AddInSchoolQuery addInSchoolQuery) throws MyExcption ;

    /**
     * 根据学生基本信息ID查询学生在校信息
     *
     * @param studentInfoId 学生在校信息Id
     * @return 返回学生在校信息
     */
    InSchoolVO queryInSchoolInfo(Long studentInfoId, Long recruitSchoolId);

    /**
     *  添加或编辑学生在校信息
     * @param addInSchoolQuery 添加编辑入参封装实体
     * @return 添加或编辑是否成功的状态
     */
    Boolean saveOrUpdateInSchoolInfoForWeChat(AddInSchoolQuery addInSchoolQuery) throws MyExcption;

    /**
     * 查询学生在校信息填写完成状态
     *
     * @param studentInfoId 学生基本信息ID
     * @return 返回状态
     */
    Boolean queryInSchoolInfoStatu(Long studentInfoId, Long recruitSchoolId);

    InSchoolClassInfoVO getSourceSchoolInfo(Long studentInfoId, Long recruitSchoolId);
}
