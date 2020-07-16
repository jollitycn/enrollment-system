package com.educiot.recruit.data.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @program: server
 * @description: 招生目标详情类
 * @author: XuChao
 * @create: 2020-04-14 17:00
 **/
@Data
public class TargetDetailVO extends TargetVO{

    @ApiModelProperty(value = "学校渠道树")
    private List<TagetTreeVO> channelTreeVO;

    @ApiModelProperty(value = "学校专业树")
    private List<TagetTreeVO> majorTreeVO;
}
