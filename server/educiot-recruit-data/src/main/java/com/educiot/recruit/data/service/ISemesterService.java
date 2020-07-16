package com.educiot.recruit.data.service;

import com.educiot.recruit.data.entity.Semester;
import com.baomidou.mybatisplus.extension.service.IService;
import com.educiot.recruit.data.entity.request.AddSemesterReq;

/**
 * <p>
 * 学期表 服务类
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-15
 */
public interface ISemesterService extends IService<Semester> {

    /**
    * @Description:  新增学期
    * @Param:  addSemesterReq
    * @return:  Boolean
    * @Author: XuChao
    * @Date: 2020/4/16
    */
    Boolean add(AddSemesterReq addSemesterReq);
}
