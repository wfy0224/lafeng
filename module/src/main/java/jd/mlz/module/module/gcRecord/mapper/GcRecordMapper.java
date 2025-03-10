package jd.mlz.module.module.gcRecord.mapper;

import jd.mlz.module.module.gcRecord.entity.GcRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import java.math.BigInteger;
import java.util.List;

/**
 * <p>
 * 垃圾投放记录表 Mapper 接口
 * </p>
 *
 * @author wangfeiyu
 * @since 2025-03-08
 */
@Mapper
public interface GcRecordMapper{

    int insert(@Param("gcRecord") GcRecord gcRecord);

    int update(@Param("gcRecord") GcRecord gcRecord);

    @Select("select * from gc_record where id=#{id} limit 1")
    GcRecord getById(@Param("id") BigInteger id);

    @Select("select * from gc_record where id=#{id} limit 1")
    GcRecord extractById(@Param("id") BigInteger id);

    @Update("update gc_record set update_time=#{time} where id = #{id} limit 1")
    int delete(@Param("id") BigInteger id,@Param("time")Integer time);

    List<GcRecord> getGcRecordList(@Param("regionId") BigInteger regionId, @Param("recordStartTime") Integer recordStartTime,
                                   @Param("recordEndTime") Integer recordEndTime, @Param("offset") Integer offset,
                                   @Param("pageSize") Integer pageSize, @Param("result")Integer result);


    int getTotal(@Param("regionId") BigInteger regionId, @Param("recordStartTime") Integer recordStartTime,
                @Param("recordEndTime") Integer recordEndTime, @Param("result")Integer result);
}
