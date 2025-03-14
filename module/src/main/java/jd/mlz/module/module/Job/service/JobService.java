package jd.mlz.module.module.Job.service;

import jd.mlz.module.module.Job.dto.GroupDTO;
import jd.mlz.module.module.Job.dto.JobDTO;
import jd.mlz.module.utils.BaseUtils;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.quartz.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangfeiyu
 * * @date 2025-03-14
 */

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
                    job.setNextFireTime(trigger.getNextFireTime() != null ? BaseUtils.timeStamp2Date((int) (trigger.getNextFireTime().getTime()/1000)) : null);

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

    public void createJob(String jobClass, String jobName, String jobGroup, String description, String cronExpression) {

            // 构造JobDetail
        JobDetail jobDetail = null;
        try {
            jobDetail = JobBuilder.newJob((Class<? extends Job>) Class.forName(jobClass))
                    .withIdentity(jobName, jobGroup)
                    .withDescription(description)
                    .build();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        // 构造Trigger
            CronTrigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(jobName + "Trigger", jobGroup)
                    .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                    .build();
        try {
            // 调度任务
            scheduler.scheduleJob(jobDetail, trigger);

        } catch (Exception e) {
            throw new RuntimeException("创建任务失败", e);
        }
    }

    public void updateJob(String group, String name, String description,String cronExpression) {


            // 更新Trigger
            TriggerKey triggerKey = new TriggerKey(name + "Trigger", group);
            CronTrigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(triggerKey)
                    .withDescription(description)
                    .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                    .build();
        try {
            // 重新调度任务
            scheduler.rescheduleJob(triggerKey, trigger);

        } catch (Exception e) {
            throw new RuntimeException("更新任务失败", e);
        }
    }

    public void deleteJob(String group, String name) {
        try {
            JobKey jobKey = new JobKey(name, group);
            scheduler.deleteJob(jobKey);
        } catch (SchedulerException e) {
            throw new RuntimeException("删除任务失败", e);
        }
    }

    public void pauseJob(String group, String name) {
        try {
            JobKey jobKey = new JobKey(name, group);
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            throw new RuntimeException("暂停任务失败", e);
        }
    }

    public void resumeJob(String group, String name) {
        try {
            JobKey jobKey = new JobKey(name, group);
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            throw new RuntimeException("恢复任务失败", e);
        }
    }

    public void triggerJob(String group, String name) {
        try {
            JobKey jobKey = new JobKey(name, group);
            scheduler.triggerJob(jobKey);
        } catch (SchedulerException e) {
            throw new RuntimeException("触发任务失败", e);
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