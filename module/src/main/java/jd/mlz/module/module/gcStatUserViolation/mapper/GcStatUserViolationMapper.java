package jd.mlz.module.module.gcStatUserViolation.mapper;

import jd.mlz.module.module.gcStatUserViolation.entity.GcStatUserViolation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import java.math.BigInteger;
/**
 * <p>
 * 居民违规统计表 Mapper 接口
 * </p>
 *
 * @author wangfeiyu
 * @since 2025-03-08
 */
@Mapper
public interface GcStatUserViolationMapper{

    void insert(@Param("gcStatUserViolation") GcStatUserViolation gcStatUserViolation);

    void update(@Param("gcStatUserViolation") GcStatUserViolation gcStatUserViolation);

    @Select("select * from gc_stat_user_violation where id=#{id} limit 1")
    GcStatUserViolation getById(@Param("id") BigInteger id);

    @Select("select * from gc_stat_user_violation where id=#{id} limit 1")
    GcStatUserViolation extractById(@Param("id") BigInteger id);

    @Update("update gc_stat_user_violation set update_time=#{time} where id = #{id} limit 1")
    int delete(@Param("id") BigInteger id,@Param("time")Integer time);
}
