package com.educiot.recruit.data.entity.ext;

import com.educiot.common.constant.Constant;
import com.educiot.recruit.data.entity.PlanSchoolIntroduction;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 招生计划学校简介
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="PlanSchoolIntroductionExt", description="PlanSchoolIntroductionExt")
public class PlanSchoolIntroductionExt extends PlanSchoolIntroduction {


}
