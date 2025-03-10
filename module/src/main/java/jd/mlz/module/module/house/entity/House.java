package jd.mlz.module.module.house.entity;


import com.baomidou.mybatisplus.annotation.IdType;

import com.baomidou.mybatisplus.annotation.TableId;

import java.math.BigInteger;

import java.io.Serializable;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 住宅信息表
 * </p>
 *
 * @author wangfeiyu
 * @since 2025-03-08
 */
@Data
@Accessors(chain = true)
public class House{

    //主键
    private BigInteger id;

    //区域id
    private BigInteger regionId;

    //具体地址
    private String address;

    private Integer createTime;

    private Integer updateTime;

    private Integer isDeleted;

}
