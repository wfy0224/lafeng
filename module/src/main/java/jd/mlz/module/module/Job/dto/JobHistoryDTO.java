package jd.mlz.module.module.Job.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author wangfeiyu
 * * @date 2025-03-14
 */

@Data
public class JobHistoryDTO {
    private Long id;
    private String jobName;
    private String jobGroup;
    private Date startTime;
    private Date endTime;
    private String status;
    private String message;
}
