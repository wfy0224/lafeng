package jd.mlz.module.module.gcRecord.mapper;

import jd.mlz.module.module.gcRecord.entity.GcRecordAttachCrond;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import java.math.BigInteger;
/**
 * <p>
 * 定时拉取附件表 Mapper 接口
 * </p>
 *
 * @author wangfeiyu
 * @since 2025-03-08
 */
@Mapper
public interface GcRecordAttachCrondMapper{

    int insert(@Param("gcRecordAttachCrond") GcRecordAttachCrond gcRecordAttachCrond);

    int update(@Param("gcRecordAttachCrond") GcRecordAttachCrond gcRecordAttachCrond);

    @Select("select * from gc_record_attach_crond where id=#{id} limit 1")
    GcRecordAttachCrond getById(@Param("id") BigInteger id);

    @Select("select * from gc_record_attach_crond where id=#{id} limit 1")
    GcRecordAttachCrond extractById(@Param("id") BigInteger id);

    @Update("update gc_record_attach_crond set update_time=#{time} where id = #{id} limit 1")
    int delete(@Param("id") BigInteger id,@Param("time")Integer time);
}
