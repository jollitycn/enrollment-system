package com.educiot.recruit.data.entity.vo.preach;

import com.educiot.recruit.common.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@ApiModel(value = "宣讲计划反馈统计信息返回封装类")
@EqualsAndHashCode()
public class PreachFeedbackCountVO extends BaseVO {


    @ApiModelProperty(value = "评估等级")
    private Integer performance;


    @ApiModelProperty(value = "反馈人数")
    private Integer amount;

//    @ApiModelProperty(value = "评估等级中文")
//    public String getFeedbackContentName() {
//        String statusStr = feedbackContent.toString();
//        String value = "";
//        switch (statusStr) {
//            case "0":
//                value = "好";
//                break;
//            case "1":
//                value = "一般";
//                break;
//            case "2":
//                value = "差";
//                break;
//            default:
//                break;
//        }
//        return value;
//    }
}
