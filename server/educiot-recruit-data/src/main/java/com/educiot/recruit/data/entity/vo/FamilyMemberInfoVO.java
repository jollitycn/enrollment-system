package com.educiot.recruit.data.entity.vo;

import com.educiot.recruit.data.entity.query.AddFamilyMemberQuery;
import com.educiot.common.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @program: server
 * @description: 家庭成员信息入参封装实体
 * @author: Mr.AI
 * @create: 2020-04-28
 **/

@Data
@ApiModel(value = "家庭成员信息入参封装实体VO")
@EqualsAndHashCode(callSuper = true)
public class FamilyMemberInfoVO extends BaseVO {

    @ApiModelProperty(value = "家庭成员信息列表",name = "addFamilyMemberQueries")
    private List<AddFamilyMemberQuery> addFamilyMemberQueries;

}
