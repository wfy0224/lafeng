package jd.mlz.module.module.region.mapper;

import jd.mlz.module.module.region.entity.Region;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import java.math.BigInteger;
import java.util.List;

/**
 * <p>
 * 区域层级表 Mapper 接口
 * </p>
 *
 * @author wangfeiyu
 * @since 2025-03-08
 */
@Mapper
public interface RegionMapper{

    int insert(@Param("region") Region region);

    int update(@Param("region") Region region);

    @Select("select * from region where id=#{id} and is_deleted=0 limit 1")
    Region getById(@Param("id") BigInteger id);

    @Select("select * from region where id=#{id} limit 1")
    Region extractById(@Param("id") BigInteger id);

    @Update("update region set update_time=#{time},is_deleted=1 where id = #{id} limit 1")
    int delete(@Param("id") BigInteger id,@Param("time")Integer time);

    @Select("select * from region where is_deleted=0 limit 999")
    List<Region> getAll();
}
