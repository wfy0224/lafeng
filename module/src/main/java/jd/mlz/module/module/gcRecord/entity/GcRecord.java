package jd.mlz.module.module.gcRecord.entity;


import java.math.BigDecimal;

import java.math.BigInteger;

import java.io.Serializable;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 垃圾投放记录表
 * </p>
 *
 * @author wangfeiyu
 * @since 2025-03-08
 */
@Data
@Accessors(chain = true)
public class GcRecord{

    //记录ID
    private BigInteger id;

    //记录种类，1-农村；2-社区
    private Integer gcType;

    //房屋ID
    private BigInteger houseId;

    //户主ID
    private BigInteger houseHolderId;

    //用户ID
    private BigInteger userId;

    //垃圾种类
    private Integer garbageClassification;

    //垃圾重量（单位：克）
    private Integer garbageWeight;

    //分类结果
    private Integer result;

    //设备ID
    private BigInteger deviceId;

    //垃圾桶ID
    private BigInteger deviceBinId;

    //合同工ID
    private BigInteger contractorId;

    //合同工用户ID
    private BigInteger contractorUserId;

    //服务区域ID快照
    private BigInteger regionId;

    //服务地址快照
    private String address;

    //经度
    private BigDecimal longitude;

    //纬度
    private BigDecimal latitude;

    //人脸照片
    private String facePhoto;

    //垃圾桶最后照片
    private String lastBinPhoto;

    //统计状态
    private Integer statStatus;

    //操作时间
    private Integer operateTime;

    //创建时间
    private Integer createTime;

    //更新时间
    private Integer updateTime;

    private Integer isDeleted;
}
