package com.educiot.recruit.server.wechat.quartz;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

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
