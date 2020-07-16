package com.educiot.recruit.data.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.educiot.recruit.common.base.CodeMsg;
import com.educiot.recruit.common.base.Result;
import com.educiot.recruit.common.constant.Constant;
import com.educiot.recruit.data.entity.RecruitSchool;
import com.educiot.recruit.data.entity.query.AddRecruitSchoolQuery;
import com.educiot.recruit.data.entity.query.UpdateRecruitSchoolQuery;
import com.educiot.recruit.data.entity.vo.RecruitSchoolIdAndNameVO;
import com.educiot.recruit.data.entity.vo.RecruitSchoolVO;
import com.educiot.recruit.data.mapper.RecruitSchoolMapper;
import com.educiot.recruit.data.service.IRecruitSchoolService;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 招生学校 服务实现类
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
@Service
public class RecruitSchoolServiceImpl extends ServiceImpl<RecruitSchoolMapper, RecruitSchool> implements IRecruitSchoolService {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result<RecruitSchool> addRecruiutSchool(AddRecruitSchoolQuery addRecruitSchoolQuery) {
        LocalDateTime now = LocalDateTime.now();
        RecruitSchool recruitSchool = new RecruitSchool();
        if (null == addRecruitSchoolQuery) {
            return Result.error(CodeMsg.PARAMETER_ISNULL);
        }
        BeanUtils.copyProperties(addRecruitSchoolQuery, recruitSchool);
        recruitSchool.setCreateTime(now);
        recruitSchool.setIsDeleted(Constant.SYS_ONE);
        int i = baseMapper.insert(recruitSchool);
        if (i > 0) {
            return Result.success(recruitSchool);
        } else {
            return Result.error(CodeMsg.DATA_INSERT_ERROR);
        }
    }


    @Override
    public Result<RecruitSchoolVO> queryRecruitSchoolInfo(String recruitSchoolId) {
        if (null == recruitSchoolId) {
            return Result.error(CodeMsg.PARAMETER_ISNULL);
        }
        QueryWrapper<RecruitSchool> recruitSchoolWrapper = new QueryWrapper<>();
        recruitSchoolWrapper.eq("api_id", recruitSchoolId);
        recruitSchoolWrapper.eq("is_deleted", Constant.SYS_ONE);
        RecruitSchool recruitSchool = baseMapper.selectOne(recruitSchoolWrapper);
        if (null == recruitSchool) {
            return Result.error(CodeMsg.RECRUIT_SCHOOL_NOT_EXIST);
        }
        RecruitSchoolVO recruitSchoolVO = new RecruitSchoolVO();
        BeanUtils.copyProperties(recruitSchool, recruitSchoolVO);
        return Result.success(recruitSchoolVO);
    }

    @Override
    public RecruitSchoolVO getById(String recruitSchoolId) {
        QueryWrapper<RecruitSchool> recruitSchoolWrapper = new QueryWrapper<>();
        recruitSchoolWrapper.eq("recruit_school_id", recruitSchoolId);
        recruitSchoolWrapper.eq("is_deleted", Constant.SYS_ONE);
        RecruitSchool recruitSchool = baseMapper.selectOne(recruitSchoolWrapper);
        if (null == recruitSchool) {
            return null;
        }
        RecruitSchoolVO recruitSchoolVO = new RecruitSchoolVO();
        BeanUtils.copyProperties(recruitSchool, recruitSchoolVO);
        return recruitSchoolVO;
    }

    @Override
    public Result<RecruitSchool> queryByApiId(Long apiId) {
        if (null == apiId) {
            return Result.error(CodeMsg.PARAMETER_ISNULL);
        }
        QueryWrapper<RecruitSchool> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("api_id", apiId);
        RecruitSchool recruitSchool = baseMapper.selectOne(queryWrapper);
        if (null == recruitSchool) {
            return Result.error(CodeMsg.CRECRUIT_SCHOOL_EXIST);
        }
        return Result.success(recruitSchool);
    }

    @Override
    public List<RecruitSchoolIdAndNameVO> listRecruitSchool(Long recruitSchoolId) {
        return baseMapper.listRecruitSchool(recruitSchoolId);
    }

    @Override
    public List<RecruitSchoolIdAndNameVO> listRecruitSchoolWechat() {
        return baseMapper.listRecruitSchoolWechat();
    }

    //    @Override
//    public RecruitSchoolIdAndNameVO getRecruitSchoolIdAndNameVOById(Long recruitSchoolId) {
//        return baseMapper.getRecruitSchoolIdAndNameVOById(recruitSchoolId);
//    }
@Override
public List<RecruitSchoolVO> listRecruitSchoolByStudent(Long studentInfo) {
    if (null == studentInfo) {
        return Lists.newArrayList();
    }
    return baseMapper.listRecruitSchoolByStudent(studentInfo);
}

    @Override
    public Result<RecruitSchool> queryByApiIdForlogin(Long apiId) {
        if (null == apiId) {
            return Result.error(CodeMsg.PARAMETER_ISNULL);
        }
//        QueryWrapper<RecruitSchool> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("api_id", apiId);
        RecruitSchool recruitSchool = baseMapper.queryByApiIdForlogin(apiId);
        if (null == recruitSchool) {
            return Result.error(CodeMsg.CRECRUIT_SCHOOL_EXIST);
        }
        return Result.success(recruitSchool);
    }

    @Override
    public RecruitSchoolIdAndNameVO getRecruitSchoolIdAndNameVOById(Long recruitSchoolId) {
        return baseMapper.getRecruitSchoolIdAndNameVOById(recruitSchoolId);
    }
//
//    @Override
//    public RecruitSchoolIdAndNameVO getRecruitSchoolIdAndNameVOById(Long recruitSchoolId) {
//        return null;
//    }
//
//    @Override
//    public RecruitSchoolIdAndNameVO getRecruitSchoolIdAndNameVOById(Long recruitSchoolId) {
//        return null;
//    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result updateRecruitSchoolInfo(UpdateRecruitSchoolQuery updateRecruitSchoolQuery) {
        RecruitSchool recruitSchool = baseMapper.selectById(updateRecruitSchoolQuery.getRecruitSchoolId());
        if (null == recruitSchool) {
            return Result.error(CodeMsg.RECRUIT_SCHOOL_NOT_EXIST);
        }
        if (recruitSchool.getIsDeleted() == Constant.SYS_ZERO) {
            return Result.error(CodeMsg.RECRUIT_SCHOOL_NOT_EXIST);
        }
        RecruitSchool updateRecruitSchool = new RecruitSchool();
        BeanUtils.copyProperties(updateRecruitSchoolQuery, updateRecruitSchool);
        int i = baseMapper.updateById(updateRecruitSchool);
        if (i > 0) {
            return Result.success();
        } else {
            return Result.error(CodeMsg.DATA_UPDATE_ERROR);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result deleteRecruitSchoolInfo(Long recruitSchoolId) {
        RecruitSchool recruitSchool = baseMapper.selectById(recruitSchoolId);
        if (null == recruitSchool) {
            return Result.error(CodeMsg.RECRUIT_SCHOOL_NOT_EXIST);
        }
        if (recruitSchool.getIsDeleted() == Constant.SYS_ZERO) {
            return Result.error(CodeMsg.RECRUIT_SCHOOL_NOT_EXIST);
        }
        recruitSchool.setIsDeleted(Constant.SYS_ZERO);
        int i = baseMapper.updateById(recruitSchool);
        if (i > 0) {
            return Result.success();
        } else {
            return Result.error(CodeMsg.DATA_DELETE_ERROR);
        }

    }
}
