package com.educiot.recruit.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.educiot.recruit.data.entity.Request;
import com.educiot.recruit.data.entity.query.RequestQuery;
import com.educiot.recruit.data.entity.vo.RequestVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RequestMapper extends BaseMapper<Request> {

    /**
     * 查询诉求信息
     * @param param
     * @return
     */
    List<RequestVO> queryRequestInfo(@Param("param")RequestQuery param);

}
