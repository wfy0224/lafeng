package jd.mlz.module.module.region.entity;


import com.baomidou.mybatisplus.annotation.IdType;

import com.baomidou.mybatisplus.annotation.TableId;

import java.math.BigInteger;

import java.io.Serializable;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 区域层级表
 * </p>
 *
 * @author wangfeiyu
 * @since 2025-03-08
 */
@Data
@Accessors(chain = true)
public class Region{

    private BigInteger id;

    //父级区域
    private BigInteger parentId;

    //区域名称
    private String regionName;

    //区域层级（街道/社区/村）
    private Integer regionLevel;

    private Integer createTime;

    private Integer updateTime;

    private Integer isDeleted;

}
