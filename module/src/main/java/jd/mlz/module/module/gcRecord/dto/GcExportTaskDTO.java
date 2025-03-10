package jd.mlz.module.module.gcRecord.dto;

import lombok.Data;

import java.math.BigInteger;

/**
 * @author wangfeiyu
 * * @date 2025-03-09
 */

@Data
public class GcExportTaskDTO {

    private BigInteger id;
    private Integer taskType;
    private BigInteger regionId;
    private Integer startTime;
    private Integer endTime;
    private Integer result;
    private String url;
    private String status;
    private Integer createTime;
}
