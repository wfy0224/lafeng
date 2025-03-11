package jd.mlz.console.config;

import jd.mlz.console.crond.TaskJob;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.quartz.*;
/**
 * @author wangfeiyu
 * * @date 2025-03-09
 */

//@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail jobDetail() {
        return JobBuilder.newJob(TaskJob.class)
                .withIdentity("myJob", "group1")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger trigger() {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail())
                .withIdentity("myTrigger", "group1")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(1) // 每 1 秒执行一次
                        .repeatForever())
                .build();
    }
}