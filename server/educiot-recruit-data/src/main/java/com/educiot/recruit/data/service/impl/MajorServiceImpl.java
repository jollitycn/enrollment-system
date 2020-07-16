package com.educiot.recruit.data.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.educiot.recruit.common.api.MajorListResult;
import com.educiot.recruit.data.LoginUser;
import com.educiot.recruit.data.entity.Major;
import com.educiot.recruit.data.entity.query.AddMajorInfoQuery;
import com.educiot.recruit.data.entity.query.UpdateMajorInfoQuery;
import com.educiot.recruit.data.entity.vo.MajorAmountVO;
import com.educiot.recruit.data.entity.vo.MajorVO;
import com.educiot.recruit.data.mapper.MajorMapper;
import com.educiot.recruit.data.service.ICollegeService;
import com.educiot.recruit.data.service.IMajorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.educiot.recruit.common.base.CodeMsg;
import com.educiot.recruit.common.base.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 招生专业表 服务实现类
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
@Service
public class MajorServiceImpl extends ServiceImpl<MajorMapper, Major> implements IMajorService {

    @Resource
    private ICollegeService collegeService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result addMajorInfo(AddMajorInfoQuery addMajorInfoQuery,LoginUser loginUser) {
        LocalDateTime now = LocalDateTime.now();
        addMajorInfoQuery.setCreatorId(loginUser.getApiInfo().getUserId());
        addMajorInfoQuery.setCreatorName(loginUser.getApiInfo().getUserName());
        Major major=new Major();
        BeanUtils.copyProperties(addMajorInfoQuery,major);
        major.setCreateTime(now);
        int i = baseMapper.insert(major);
        if (i > 0) {
            return Result.success();
        }else{
            return Result.error(CodeMsg.DATA_INSERT_ERROR);
        }
    }

    @Override
    public Result<List<Major>> queryMajorInfoByCollegeId(String collegeId) {
        if(null==collegeId){
            return Result.error(CodeMsg.PARAMETER_ISNULL);
        }
        QueryWrapper<Major> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("college_id",collegeId);
        List<Major> majors = baseMapper.selectList(queryWrapper);
//        if(majors.size()==0){
//            return Result.error(CodeMsg.MAJOR_INFO_NOT_EXIST);
//        }
        return Result.success(majors);
    }

    @Override
    public Result<MajorVO> queryMajorInfoByMajorId(String majorId,Long recruitSchoolId,Long semesterId) {
        if(null==majorId){
            return Result.error(CodeMsg.PARAMETER_ISNULL);
        }
        MajorVO majorVO = baseMapper.queryMajorInfoByMajorId(majorId);
        int i = collegeService.queryMajorAmount(majorVO.getMajorId(), recruitSchoolId, semesterId);
        majorVO.setAmount(i);
        if(null==majorVO){
            return Result.error(CodeMsg.MAJOR_INFO_NOT_EXIST);
        }
        return Result.success(majorVO);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result updateMajorInfo(UpdateMajorInfoQuery updateMajorInfoQuery) {
        if(null==updateMajorInfoQuery){
            return Result.error(CodeMsg.PARAMETER_ISNULL);
        }
        Major major = baseMapper.selectById(updateMajorInfoQuery.getMajorId());
        if(null==major){
            return Result.error(CodeMsg.MAJOR_INFO_NOT_EXIST);
        }
        Major updateMajor=new Major();
        BeanUtils.copyProperties(updateMajorInfoQuery,updateMajor);
        int i = baseMapper.updateById(updateMajor);
        if (i > 0) {
            return Result.success();
        }else{
            return Result.error(CodeMsg.DATA_UPDATE_ERROR);
        }
    }

    @Override
    public Result deleteMajorInfo(Long majorId) {
        if(null==majorId){
            return Result.error(CodeMsg.PARAMETER_ISNULL);
        }
        boolean b = removeById(majorId);
        if(b){
            return Result.success();
        }else{
            return Result.error(CodeMsg.DATA_DELETE_ERROR);
        }
    }

    @Override
    public List<MajorAmountVO> queryMajorInfoByAPI(MajorListResult majorListResult, LoginUser loginUser) {
        List<MajorAmountVO> majorAmountVOS=new ArrayList<>();
        if(0 == majorListResult.getData().size()){
            return majorAmountVOS;
        }
        majorListResult.getData().forEach(Major->{
            MajorAmountVO major=new MajorAmountVO();
            major.setId(Major.getId());
            major.setName(Major.getName());
            Integer integer = baseMapper.queryTargetAmount(loginUser.getCurrentSchoolId(), loginUser.getCurrentSemesterId(), Major.getId());
            major.setAmount(integer);
            majorAmountVOS.add(major);
        });
        return majorAmountVOS;
    }
}
