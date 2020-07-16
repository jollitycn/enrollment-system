package com.educiot.recruit.data.entity.vo;

import com.educiot.recruit.data.entity.TargetDecomposition;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @program: server
 * @description: 学校渠道树
 * @author: XuChao
 * @create: 2020-04-15 10:32
 **/
@Data
public class ChannelTreeVO extends TargetDecomposition {

    @ApiModelProperty(value = "树")
    private List<TargetDecomposition> nodeList;

}
