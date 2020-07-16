package com.educiot.recruit.data.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.educiot.common.api.DepartInfo;
import com.educiot.common.base.CodeMsg;
import com.educiot.common.base.Result;
import com.educiot.recruit.data.entity.College;
import com.educiot.recruit.data.entity.Major;
import com.educiot.recruit.data.entity.query.AddCollegeQuery;
import com.educiot.recruit.data.entity.query.UpdateCollegeQuery;
import com.educiot.recruit.data.entity.vo.CollegeAndMajorVO;
import com.educiot.recruit.data.entity.vo.CollegeVO;
import com.educiot.recruit.data.entity.vo.MajorVO;
import com.educiot.recruit.data.mapper.CollegeMapper;
import com.educiot.recruit.data.mapper.MajorMapper;
import com.educiot.recruit.data.service.ICollegeService;
import com.educiot.recruit.data.service.IMajorService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 招生院系表 服务实现类
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
@Service
public class CollegeServiceImpl extends ServiceImpl<CollegeMapper, College> implements ICollegeService {

    @Resource
    private IMajorService majorService;

    @Resource
    private MajorMapper majorMapper;

    @Resource
    private CollegeMapper collegeMapper;

    @Override
    public Result queryCollegeInfo(String collegeId) {
        if (null == collegeId) {
            return Result.error(CodeMsg.PARAMETER_ISNULL);
        }
        QueryWrapper<College> collegeQueryWrapper = new QueryWrapper<>();
        collegeQueryWrapper.eq("plan_id", collegeId);
        List<College> colleges = baseMapper.selectList(collegeQueryWrapper);
        if (colleges.size() == 0) {
            return Result.error(CodeMsg.COLLEGE_INFO_NOT_EXIST);
        }
        List<CollegeVO> collegeVOS = new ArrayList<>();
        colleges.forEach(college -> {
            CollegeVO collegeVO = new CollegeVO();
            BeanUtils.copyProperties(college, collegeVO);
            collegeVO.setMajorCount(this.queryMajorCountByCollegeId(college.getCollegeId().toString()));
//            这是之前的需求，计算的是院系下所有的招生人数之和
//            collegeVO.setRecruitAmount(this.queryMajorAmountByCollegeId(college.getCollegeId().toString()));
            collegeVO.setRecruitAmount(college.getRecruitAmount());
            collegeVO.setCreatorId(college.getCreatorId());
            collegeVO.setCreatorName(college.getCreatorName());
            collegeVO.setCreateTime(college.getCreateTime());
            collegeVOS.add(collegeVO);
        });
        return Result.success(collegeVOS);
    }

    @Override
    public Result<College> queryCollegeInfoById(String collegeId) {
        if (null == collegeId) {
            return Result.error(CodeMsg.PARAMETER_ISNULL);
        }
        QueryWrapper<College> collegeQueryWrapper = new QueryWrapper<>();
        collegeQueryWrapper.eq("college_id", collegeId);
        College college = baseMapper.selectOne(collegeQueryWrapper);
        if (null == college) {
            return Result.error(CodeMsg.COLLEGE_INFO_NOT_EXIST);
        }
        return Result.success(college);
    }

    /**
     * 查询院系下面的专业个数
     *
     * @param collegeId
     * @return
     */
    public Integer queryMajorCountByCollegeId(String collegeId) {
        if (null == collegeId) {
            return 0;
        }
        QueryWrapper<Major> majorQueryWrapper = new QueryWrapper<>();
        majorQueryWrapper.eq("college_id", collegeId);
        Integer majorCount = majorService.getBaseMapper().selectCount(majorQueryWrapper);
        return majorCount;
    }

    /**
     * 查询院系下面所有专业的总招生人数总和
     *
     * @param collegeId
     * @return
     */
    public Integer queryMajorAmountByCollegeId(String collegeId) {
        if (null == collegeId) {
            return 0;
        }
        Integer amount = majorMapper.queryAmountCountByCollegeId(collegeId);
        return amount;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result updateCollegeInfo(UpdateCollegeQuery updateCollegeQuery) {
        if (updateCollegeQuery == null) {
            return Result.error(CodeMsg.PARAMETER_ISNULL);
        }
        College college = baseMapper.selectById(updateCollegeQuery.getCollegeId());
        if (null == college) {
            return Result.error(CodeMsg.COLLEGE_INFO_NOT_EXIST);
        }
        College updateCollege = new College();
        BeanUtils.copyProperties(updateCollegeQuery, updateCollege);
        baseMapper.updateById(updateCollege);
        return Result.success();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result addCollegeInfo(AddCollegeQuery addCollegeQuery, List<DepartInfo> termCollegeVOS) {
        if (null == addCollegeQuery || termCollegeVOS.size() == 0) {
            return Result.error(CodeMsg.PARAMETER_ISNULL);
        }
        LocalDateTime now = LocalDateTime.now();
        termCollegeVOS.forEach(termCollegeVO -> {
            College college = new College();
            college.setApiId(Long.parseLong(termCollegeVO.getId()));
            college.setApiCollegeName(termCollegeVO.getName());
            college.setCreateTime(now);
            college.setRecruitSchoolId(addCollegeQuery.getRecruitSchoolId());
            college.setPlanId(addCollegeQuery.getPlanId());
            college.setCreatorId(addCollegeQuery.getCreatorId());
            college.setCreatorName(addCollegeQuery.getCreatorName());
            college.setCollegeName(termCollegeVO.getName());
            save(college);
        });
        return Result.success();
    }

    @Override
    public Result<List<CollegeAndMajorVO>> queryCollegeAndMajor(Long planId,Long recruitSchoolId,Long semesterId) {
        if (null == planId) {
            return Result.error(CodeMsg.PARAMETER_ISNULL);
        }
        List<CollegeAndMajorVO> collegeAndMajorVOS = collegeMapper.queryCollegeAndMajor(planId);
        for (CollegeAndMajorVO collegeAndMajorVO:collegeAndMajorVOS) {
            for (MajorVO majorVO:collegeAndMajorVO.getMajors()) {
                int i = queryMajorAmount(majorVO.getMajorId(), recruitSchoolId, semesterId);
                majorVO.setAmount(i);
            }
        }
        return Result.success(collegeAndMajorVOS);
    }

    @Override
    public int queryMajorAmount(Long majorId, Long recruitSchoolId, Long semesterId) {
        return collegeMapper.queryMajorAmount(majorId,recruitSchoolId,semesterId);
    }
}
