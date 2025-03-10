package jd.mlz.console.domain;

import lombok.Data;

/**
 * @author wangfeiyu
 * * @date 2025-03-09
 */

@Data
public class GcExportExcelVO {
    private String id;
    private String homeownerName;
    private String region;
    private String garbageClassification;
    private String garbageWeight;
    private String result;
    private String collectorId;
    private String createTime;
}
