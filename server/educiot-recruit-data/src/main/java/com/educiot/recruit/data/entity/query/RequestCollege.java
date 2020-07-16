package com.educiot.recruit.data.entity.query;

import com.educiot.recruit.data.entity.vo.TermCollegeVO;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * @program: server
 * @description: 平台根据学校id返回学期列表封装类
 * @author: Mr.AI
 * @create: 2020-04-18
 **/

@Data
@ApiModel(value = "平台根据学校id返回学期列表封装类")
public class RequestCollege {

    private String msg;

    private Integer total;

    private List<TermCollegeVO> termCollegeVOS;
}
