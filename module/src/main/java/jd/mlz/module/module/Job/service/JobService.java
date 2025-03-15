package jd.mlz.module.module.Job.service;

import jd.mlz.module.module.Job.dto.GroupDTO;
import jd.mlz.module.module.Job.dto.JobDTO;
import jd.mlz.module.utils.BaseUtils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.quartz.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wangfeiyu
 * * @date 2025-03-14
 */

@Slf4j
@Service
public class JobService {

    @Autowired
    private Scheduler scheduler;

    public List<JobDTO> getAllJobs() throws SchedulerException {
        List<JobDTO> jobList = new ArrayList<>();

        for (String groupName : scheduler.getJobGroupNames()) {
            for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
                JobDetail jobDetail = scheduler.getJobDetail(jobKey);
                List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);

                if (triggers != null && !triggers.isEmpty()) {
                    Trigger trigger = triggers.get(0);
                    JobDTO job = new JobDTO();
                    job.setJobName(jobKey.getName());
                    job.setJobGroup(jobKey.getGroup());
                    job.setDescription(jobDetail.getDescription());
                    job.setJobClass(jobDetail.getJobClass().getName());
                    job.setNextFireTime(trigger.getNextFireTime() != null ? BaseUtils.timeStamp2Date((int) (trigger.getNextFireTime().getTime() / 1000)) : null);

                    if (trigger instanceof CronTrigger) {
                        job.setCronExpression(((CronTrigger) trigger).getCronExpression());
                    }

                    Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                    if (triggerState == Trigger.TriggerState.PAUSED) {
                        job.setStatus("PAUSED");
                    } else if (triggerState == Trigger.TriggerState.NORMAL) {
                        job.setStatus("RUNNING");
                    } else {
                        job.setStatus("ERROR");
                    }

                    jobList.add(job);
                }
            }
        }
        return jobList;
    }

    public Boolean createJob(String jobClass, String jobName, String jobGroup, String description, String cronExpression) {

        // 构造JobDetail
        JobDetail jobDetail = null;
        try {
            jobDetail = JobBuilder.newJob((Class<? extends Job>) Class.forName(jobClass))
                    .withIdentity(jobName, jobGroup)
                    .withDescription(description)
                    .build();
        } catch (ClassNotFoundException e) {
            return null;
        }

        // 构造Trigger
        CronTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(jobName + "Trigger", jobGroup)
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                .build();
        try {
            // 调度任务
            if (!BaseUtils.isEmpty(scheduler.scheduleJob(jobDetail, trigger))){
                return true;
            }
            return false;
        } catch (Exception e) {
            log.info("创建任务失败");
            return false;
        }
    }

    public Boolean updateJob(String group, String name, String description, String cronExpression) {

        // 更新Trigger
        TriggerKey triggerKey = new TriggerKey(name + "Trigger", group);
        CronTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(triggerKey)
                .withDescription(description)
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                .build();
        try {
            // 重新调度任务
            if (!BaseUtils.isEmpty(scheduler.rescheduleJob(triggerKey, trigger))){
                return true;
            }
            return false;
        } catch (Exception e) {
            log.info("更新任务失败");
            return false;
        }
    }

    public Boolean deleteJob(String group, String name) {
        try {
            JobKey jobKey = new JobKey(name, group);
            return scheduler.deleteJob(jobKey);
        } catch (SchedulerException e) {
            throw new RuntimeException("删除任务失败", e);
        }
    }

    public Boolean pauseJob(String group, String name) {
        try {
            JobKey jobKey = new JobKey(name, group);
            scheduler.pauseJob(jobKey);
            return true;
        } catch (SchedulerException e) {
            log.info("暂停任务失败", e);
            return false;
        }
    }

    public Boolean resumeJob(String group, String name) {
        try {
            JobKey jobKey = new JobKey(name, group);
            scheduler.resumeJob(jobKey);
            return true;
        } catch (SchedulerException e) {
            log.info("恢复任务失败");
            return false;
        }
    }

    public Boolean triggerJob(String group, String name) {
        try {
            JobKey jobKey = new JobKey(name, group);
            scheduler.triggerJob(jobKey);
            return true;
        } catch (SchedulerException e) {
            log.info("触发任务失败");
            return false;
        }
    }

    public List<GroupDTO> getAllGroups() throws SchedulerException {
        List<GroupDTO> groupList = new ArrayList<>();

        for (String groupName : scheduler.getJobGroupNames()) {
            GroupDTO group = new GroupDTO();
            group.setGroupName(groupName);
            group.setJobCount(String.valueOf(scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName)).size()));
            groupList.add(group);
        }
        return groupList;
    }

}