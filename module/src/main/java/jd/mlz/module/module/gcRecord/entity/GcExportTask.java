package jd.mlz.module.module.gcRecord.entity;


import com.baomidou.mybatisplus.annotation.IdType;

import com.baomidou.mybatisplus.annotation.TableId;

import java.math.BigInteger;

import java.io.Serializable;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 异步导出任务表
 * </p>
 *
 * @author wangfeiyu
 * @since 2025-03-10
 */
@Data
@Accessors(chain = true)
public class GcExportTask{

    //任务ID
    private BigInteger id;

    //任务类型（1-违规投放记录）
    private Integer taskType;

    //查询区域ID（快照）
    private BigInteger regionId;

    //查询起始时间（快照）
    private Integer startTime;

    //查询结束时间（快照）
    private Integer endTime;

    //分类结果过滤
    private Integer result;

    //下载URL
    private String fileUrl;

    //创建人ID
    private BigInteger userId;

    //处理时间
    private Integer processTime;

    //任务状态：0未开始；1已执行
    private Integer status;

    //创建时间
    private Integer createTime;

    //更新时间
    private Integer updateTime;

    //是否删除
    private Integer isDeleted;

}
