package jd.mlz.console.domain;

import lombok.Data;

import java.math.BigInteger;

/**
 * @author wangfeiyu
 * * @date 2025-03-09
 */

@Data
public class GcExportTaskVO {
    private BigInteger id;
    private String result;
    private String keyword;
    private String status;
    private String fileUrl;
    private String createTime;

}
