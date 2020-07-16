package com.educiot.recruit.data.service.student.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.educiot.recruit.data.entity.Request;
import com.educiot.recruit.data.entity.StudentPublicRelation;
import com.educiot.recruit.data.entity.query.AddRequestQuery;
import com.educiot.recruit.data.entity.query.RequestQuery;
import com.educiot.recruit.data.entity.vo.RequestVO;
import com.educiot.recruit.data.mapper.RequestMapper;
import com.educiot.recruit.data.service.ITaskService;
import com.educiot.recruit.data.service.student.IRequestService;
import com.educiot.common.base.CodeMsg;
import com.educiot.common.base.Result;
import com.educiot.common.constant.Constant;
import com.educiot.recruit.data.service.student.IStudentPublicRelationService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 诉求表 服务实现类
 * </p>
 *
 * @author Mr.AI
 * @since 2020-04-13
 */
@Service
public class RequestServiceImpl extends ServiceImpl<RequestMapper, Request> implements IRequestService {

    @Resource
    private IStudentPublicRelationService studentPublicRelationService;

    @Resource
    private ITaskService taskService;

    @Override
    public Result<List<RequestVO>> queryRequestInfo(RequestQuery requestQuery) {
//        if (null != requestQuery.getRecruitSchoolId()) {
//            // TODO 小程序端获取登录的学生ID
//            requestQuery.setStudentInfo(1L);
//        } else {
//            // TODO APP端获取登录的负责人ID
//            requestQuery.setCreatorId(1L);
//        }
        List<RequestVO> requestVOS = baseMapper.queryRequestInfo(requestQuery);
        return Result.success(requestVOS);
    }

    @Override
    public Result saveOrUpdateRequestInfo(AddRequestQuery addRequestQuery) {
        if (null == addRequestQuery) {
            return Result.error(CodeMsg.PARAMETER_ISNULL);
        }
        boolean flag = false;
        Request request = new Request();
        LocalDateTime now = LocalDateTime.now();
        BeanUtils.copyProperties(addRequestQuery, request);
        if (null != addRequestQuery.getRequestId()) {
            request.setReplyTime(now);
            request.setStatus(Constant.SYS_ZERO);
            //诉求回复时，添加诉求任务
            taskService.finishTask(addRequestQuery.getRequestId());
        } else {
            QueryWrapper<StudentPublicRelation> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("student_info", addRequestQuery.getStudentInfo());
            queryWrapper.eq("recruit_school_id", addRequestQuery.getRecruitSchoolId());
            StudentPublicRelation studentPublicRelation = studentPublicRelationService.getOne(queryWrapper);
            request.setCreateTime(now);
            request.setStatus(Constant.SYS_ONE);
            request.setStudentPublicRelationId(studentPublicRelation.getStudentPublicRelationId());
            flag = true;
        }

        boolean b = saveOrUpdate(request);
        //添加诉求时，增加任务
        if(flag){
            taskService.addTaskFromStudent(addRequestQuery.getStudentPublicRelationId(),
                    0, addRequestQuery.getSemesterId(), request.getRequestId());
        }
        return Result.success(b);

    }

    @Override
    public Boolean updateRequestStatus(Long requestId) {
        if (null == requestId) {
            return false;
        }
        Request request = new Request();
        request.setReplyId(requestId);
        request.setStatus(Constant.SYS_ONE);
        int i = baseMapper.updateById(request);
        if (i > 0) {
            return true;
        } else {
            return false;
        }
    }
}
