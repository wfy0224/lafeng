package jd.mlz.module.module.Job.dto;

import lombok.Data;

/**
 * @author wangfeiyu
 * * @date 2025-03-14
 */

@Data
public class JobDTO {
    private String jobName;
    private String jobGroup;
    private String description;
    private String cronExpression;
    private String jobClass;
    private String status;
    private String nextFireTime;
}
