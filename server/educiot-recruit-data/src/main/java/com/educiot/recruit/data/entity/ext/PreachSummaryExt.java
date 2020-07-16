package com.educiot.recruit.data.entity.ext;

import com.educiot.recruit.data.entity.preach.PreachSummary;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@ApiModel(value = "宣讲计划总结信息返回封装类")
@EqualsAndHashCode()
public class PreachSummaryExt extends PreachSummary {
}
