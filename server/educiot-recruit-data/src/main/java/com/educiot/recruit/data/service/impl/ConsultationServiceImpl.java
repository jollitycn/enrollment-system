package com.educiot.recruit.data.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.educiot.recruit.data.entity.Consultation;
import com.educiot.recruit.data.entity.query.AddConsultationQuery;
import com.educiot.recruit.data.mapper.ConsultationMapper;
import com.educiot.recruit.data.service.IConsultationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.educiot.recruit.common.base.CodeMsg;
import com.educiot.recruit.data.LoginUser;
import com.educiot.recruit.common.base.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 到校咨询表 服务实现类
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-13
 */
@Service
public class ConsultationServiceImpl extends ServiceImpl<ConsultationMapper, Consultation> implements IConsultationService {


    @Override
    public List<Consultation> queryAllConsultation(Long recruitSchoolId) {
        return baseMapper.queryAllConsultation(recruitSchoolId);
    }

    /**
     * 新增/编辑到校咨询
     * @param loginUser
     * @param addConsultationQuery
     * @return true 添加成功
     */
    @Override
    public Result<?> addOrEditConsultation(LoginUser loginUser, AddConsultationQuery addConsultationQuery) {

        //如果没有id新增咨询
        if (addConsultationQuery.getConsultationId() == null) {

            //判断该学期是否已有咨询
            QueryWrapper<Consultation> wrapper = new QueryWrapper<>();
            wrapper.eq("semester_id", loginUser.getCurrentSemesterId());
            int count = count(wrapper);
            if (count > 0) {
                return Result.error(CodeMsg.CONSULTATION_EXIST);
            }
            Consultation consultation = new Consultation();
            BeanUtils.copyProperties(addConsultationQuery, consultation);

            //填充学期字段
            consultation.setSemester(loginUser.getSemesterName());
            consultation.setSemesterId(loginUser.getCurrentSemesterId());
            //填充创建信息
            consultation.setCreateTime(LocalDateTime.now());
            consultation.setCreatorId(loginUser.getApiInfo().getUserId());
            consultation.setCreatorName(loginUser.getApiInfo().getUserName());

            boolean status = save(consultation);
            if (status) {
                return Result.success();
            } else {
                return Result.error(CodeMsg.DATA_INSERT_ERROR);
            }

        } else {
            Consultation consultation = getById(addConsultationQuery.getConsultationId());
            if (consultation == null) {
                return Result.error(CodeMsg.DATA_UPDATE_ERROR);
            } else {

                BeanUtils.copyProperties(addConsultationQuery, consultation);
                //填充学期字段
                consultation.setSemester(loginUser.getSemesterName());
                consultation.setSemesterId(loginUser.getCurrentSemesterId());
                //填充创建信息
                consultation.setCreateTime(LocalDateTime.now());
                consultation.setCreatorId(loginUser.getApiInfo().getUserId());
                consultation.setCreatorName(loginUser.getApiInfo().getUserName());

                boolean status = updateById(consultation);
                if (status) {
                    return Result.success();
                } else {
                    return Result.error(CodeMsg.DATA_INSERT_ERROR);
                }
            }
        }

    }
}
