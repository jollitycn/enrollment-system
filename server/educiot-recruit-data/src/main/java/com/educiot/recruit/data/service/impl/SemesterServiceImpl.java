package com.educiot.recruit.data.service.impl;

import com.educiot.recruit.data.entity.Semester;
import com.educiot.recruit.data.entity.request.AddSemesterReq;
import com.educiot.recruit.data.mapper.SemesterMapper;
import com.educiot.recruit.data.service.ISemesterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 学期表 服务实现类
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-15
 */
@Service
public class SemesterServiceImpl extends ServiceImpl<SemesterMapper, Semester> implements ISemesterService {

    /**
     * @param addSemesterReq
     * @Description: 新增学期
     * @Param: addSemesterReq
     * @return: Boolean
     * @Author: XuChao
     * @Date: 2020/4/16
     */
    @Override
    public Boolean add(AddSemesterReq addSemesterReq) {

        //TODO 调用api接口进行新增，目前api接口还未通

        //添加学期信息到数据库
        Semester semester=new Semester();
        semester.setSemesterApiId(1L);
        semester.setRecruitSchoolId(addSemesterReq.getSchoolId());
        semester.setSemesterName(addSemesterReq.getSemesterName());
        semester.setSyncTime(new Date());
        return this.save(semester);
    }
}
