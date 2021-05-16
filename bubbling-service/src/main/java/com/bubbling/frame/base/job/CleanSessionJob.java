package com.bubbling.frame.base.job;


import com.bubbling.frame.base.utils.RSAencryUtils;
import com.bubbling.frame.base.utils.SessionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
import java.util.Map;

/**
 * @title: CleanSessionJob
 * @Author yangwen
 * @Date: 2021-05-16 11:05
 * @ignore
 */
@Configuration
@EnableScheduling
@Slf4j
public class CleanSessionJob {

    @Scheduled(cron = "0 0 0/1 * * ?")
    private void configureTasks() {
        log.info("清理session");
        Long time = new Date().getTime();
        Map<String, Long> sessionTimeOutMap = SessionUtils.sessionTimeOutMap;
        for (Map.Entry<String, Long> stringLongEntry : sessionTimeOutMap.entrySet()) {
            if (stringLongEntry.getValue()<time) {
                //过期清理
                SessionUtils.sessionTimeOutMap.remove(stringLongEntry.getKey());
                SessionUtils.sessionMap.remove(stringLongEntry.getKey());
            }
        }
        //清理秘钥
        Map<String, Long> rsaTimeOutMap = RSAencryUtils.rsaTimeOutMap;
        for (Map.Entry<String, Long> stringLongEntry : rsaTimeOutMap.entrySet()) {
            if (stringLongEntry.getValue()<time) {
                RSAencryUtils.rsaTimeOutMap.remove(stringLongEntry.getKey());
                RSAencryUtils.rsaMap.remove(stringLongEntry.getKey());
            }
        }
    }
}
