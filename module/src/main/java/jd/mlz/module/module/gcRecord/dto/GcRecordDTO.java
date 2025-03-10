package jd.mlz.module.module.gcRecord.dto;

import lombok.Data;

import java.math.BigInteger;

/**
 * @author wangfeiyu
 * * @date 2025-03-08
 */

@Data
public class GcRecordDTO {
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
