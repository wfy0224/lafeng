package jd.mlz.module.module.gcRecord.entity;


import java.math.BigInteger;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 垃圾分类记录附件表
 * </p>
 *
 * @author wangfeiyu
 * @since 2025-03-08
 */
@Data
@Accessors(chain = true)
public class GcRecordAttachment{

    //主键
    private BigInteger id;

    //记录id
    private BigInteger recordId;

    //附件类型；1-照片；2-视频
    private Integer attachmentType;

    //链接
    private String attachmentUrl;

    //附件额外，如视频首页图片
    private String attachmentExtra;

    private Integer createTime;

    private Integer updateTime;

    private Integer isDeleted;

}
