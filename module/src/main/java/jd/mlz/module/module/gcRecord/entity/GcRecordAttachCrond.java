package jd.mlz.module.module.gcRecord.entity;


import java.math.BigInteger;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 定时拉取附件表
 * </p>
 *
 * @author wangfeiyu
 * @since 2025-03-08
 */
@Data
@Accessors(chain = true)
public class GcRecordAttachCrond{

    //主键
    private BigInteger id;

    //记录id
    private BigInteger recordId;

    //是否正在执行
    private Integer isRun;

    //是否结束
    private Integer isEnd;

    //设备编号
    private String deviceIdentifier;

    //记录时间
    private Integer recordTime;

    private Integer createTime;

    private Integer updateTime;

}
