package com.educiot.recruit.server.quartz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @author liuhao
 * @date Created in 2020/4/2
 */
@Component
@EnableScheduling
public class APISyncSchedule {
//    @Scheduled(cron = "0 30 1 * * ?")
    public void checkRequest() {
    }
}
