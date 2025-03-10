package jd.mlz.module.module.house.mapper;

import jd.mlz.module.module.house.entity.House;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import java.math.BigInteger;
/**
 * <p>
 * 住宅信息表 Mapper 接口
 * </p>
 *
 * @author wangfeiyu
 * @since 2025-03-08
 */
@Mapper
public interface HouseMapper{

    void insert(@Param("house") House house);

    void update(@Param("house") House house);

    @Select("select * from house where id=#{id} and is_deleted=0 limit 1")
    House getById(@Param("id") BigInteger id);

    @Select("select * from house where id=#{id} limit 1")
    House extractById(@Param("id") BigInteger id);

    @Update("update house set update_time=#{time},is_deleted=1 where id = #{id} limit 1")
    int delete(@Param("id") BigInteger id,@Param("time")Integer time);
}
