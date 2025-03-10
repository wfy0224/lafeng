package jd.mlz.module.module.gcRecord.entity;

import java.math.BigInteger;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 异步导出任务状态表
 * </p>
 *
 * @author wangfeiyu
 * @since 2025-03-08
 */
@Data
@Accessors(chain = true)
public class GcExportTaskCrond{

    //主键
    private BigInteger id;

    //任务id
    private BigInteger taskId;

    //是否正在执行
    private Integer isRun;

    //是否结束
    private Integer isEnd;

    private Integer createTime;

    private Integer updateTime;

    //是否删除
    private Integer isDeleted;

}
