package jd.mlz.module.module.gcStatUserViolation.entity;


import com.baomidou.mybatisplus.annotation.IdType;

import com.baomidou.mybatisplus.annotation.TableId;

import java.math.BigInteger;

import java.io.Serializable;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 居民违规统计表
 * </p>
 *
 * @author wangfeiyu
 * @since 2025-03-08
 */
@Data
@Accessors(chain = true)
public class GcStatUserViolation{

    private BigInteger id;

    //地区id
    private BigInteger regionId;

    //房屋id
    private BigInteger houseId;

    //用户id
    private BigInteger userId;

    //户主id
    private BigInteger houseHolderId;

    //违规次数
    private Integer violationAmount;

    //最近一条违规记录
    private BigInteger lastViolationRecordId;

    //是否正在曝光
    private Integer isExposure;

    //创建时间
    private Integer createTime;

    //更新时间
    private Integer updateTime;

}
