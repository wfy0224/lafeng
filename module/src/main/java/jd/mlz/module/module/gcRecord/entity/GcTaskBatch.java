package jd.mlz.module.module.gcRecord.entity;


import com.baomidou.mybatisplus.annotation.IdType;

import com.baomidou.mybatisplus.annotation.TableId;

import java.math.BigInteger;

import java.io.Serializable;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 任务批次表
 * </p>
 *
 * @author wangfeiyu
 * @since 2025-03-15
 */
@Data
@Accessors(chain = true)
public class GcTaskBatch{

    private BigInteger id;

    //批次状态
    private Integer status;

    //起始任务id
    private BigInteger startTaskId;

    //截止任务id
    private BigInteger endTaskId;

    //批次开始时间
    private Integer startTime;

    //批次结束时间
    private Integer endTime;

    private Integer createTime;

    private Integer updateTime;

    private Integer isDeleted;

}
