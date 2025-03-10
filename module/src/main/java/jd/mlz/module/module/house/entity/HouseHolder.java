package jd.mlz.module.module.house.entity;


import java.math.BigInteger;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 住宅居民信息表
 * </p>
 *
 * @author wangfeiyu
 * @since 2025-03-08
 */
@Data
@Accessors(chain = true)
public class HouseHolder{

    private BigInteger id;

    private BigInteger houseId;

    private BigInteger userId;

    //是否为户主，为一户多用户冗余
    private Integer isHolder;

    //是否常住
    private Integer isPermanent;

    //创建时间
    private Integer createTime;

    //更新时间
    private Integer updateTime;

    //是否删除
    private Integer isDeleted;

}
