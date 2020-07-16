package com.educiot.recruit.server.app.controller.student;


import com.educiot.recruit.server.app.common.BaseController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 成绩表 前端控制器
 * </p>
 *
 * @author Mr.AI
 * @since 2020-04-13
 */
@RestController
@RequestMapping("/score")
@Api(tags = {"学生成绩信息"}, value = "RequestController")
public class ScoreController extends BaseController {

}
