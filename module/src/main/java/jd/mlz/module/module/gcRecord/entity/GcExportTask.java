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
 * @since 2025-03-08
 */
@Data
@Accessors(chain = true)
public class GcExportTask{

    //任务ID
    private BigInteger id;

    //任务类型（1-违规投放记录）
    private Integer taskType;

    //筛选区域ID
    private BigInteger regionId;

    //筛选起始时间
    private Integer startTime;

    //筛选结束时间
    private Integer endTime;

    //分类结果过滤
    private Integer result;

    //生成文件名（UUID命名）
    private String fileName;

    //OSS存储桶名
    private String ossBucket;

    //OSS存储路径
    private String ossPath;

    //下载URL
    private String ossUrl;

    //错误信息
    private String errorMsg;

    //创建人ID
    private BigInteger userId;

    //处理时间
    private Integer processTime;

    //创建时间
    private Integer createTime;

    //更新时间
    private Integer updateTime;

    //是否删除
    private Integer isDeleted;

}
