package jd.mlz.console.domain;

import lombok.Data;

/**
 * @author wangfeiyu
 * * @date 2025-03-08
 */

@Data
public class GcRecordVO {
    private String id;
    private String homeownerName;
    private String region;
    private String garbageClassification;
    private String garbageWeight;
    private String result;
    private String lastBinPhoto;
    private String collectorId;
    private String createTime;
}
