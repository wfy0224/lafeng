package jd.mlz.module.module.gcRecord.mapper;

import jd.mlz.module.module.gcRecord.entity.GcRecordAttachment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import java.math.BigInteger;
/**
 * <p>
 * 垃圾分类记录附件表 Mapper 接口
 * </p>
 *
 * @author wangfeiyu
 * @since 2025-03-08
 */
@Mapper
public interface GcRecordAttachmentMapper{

    int insert(@Param("gcRecordAttachment") GcRecordAttachment gcRecordAttachment);

    int update(@Param("gcRecordAttachment") GcRecordAttachment gcRecordAttachment);

    @Select("select * from gc_record_attachment where id=#{id} and is_deleted=0 limit 1")
    GcRecordAttachment getById(@Param("id") BigInteger id);

    @Select("select * from gc_record_attachment where id=#{id} limit 1")
    GcRecordAttachment extractById(@Param("id") BigInteger id);

    @Update("update gc_record_attachment set update_time=#{time},is_deleted=1 where id = #{id} limit 1")
    int delete(@Param("id") BigInteger id,@Param("time")Integer time);
}
