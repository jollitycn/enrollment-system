package com.educiot.recruit.data.service.impl;

import com.educiot.recruit.data.entity.Score;
import com.educiot.recruit.data.mapper.ScoreMapper;
import com.educiot.recruit.data.service.IScoreService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 成绩表 服务实现类
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-13
 */
@Service
public class ScoreServiceImpl extends ServiceImpl<ScoreMapper, Score> implements IScoreService {

}
