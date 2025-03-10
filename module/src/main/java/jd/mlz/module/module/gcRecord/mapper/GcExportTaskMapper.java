package jd.mlz.module.module.gcRecord.mapper;

import jd.mlz.module.module.gcRecord.entity.GcExportTask;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import java.math.BigInteger;
import java.util.List;

/**
 * <p>
 * 异步导出任务表 Mapper 接口
 * </p>
 *
 * @author wangfeiyu
 * @since 2025-03-08
 */
@Mapper
public interface GcExportTaskMapper{

    int insert(@Param("gcExportTask") GcExportTask gcExportTask);

    int update(@Param("gcExportTask") GcExportTask gcExportTask);

    @Select("select * from gc_export_task where id=#{id} and is_deleted=0 limit 1")
    GcExportTask getById(@Param("id") BigInteger id);

    @Select("select * from gc_export_task where id=#{id} limit 1")
    GcExportTask extractById(@Param("id") BigInteger id);

    @Update("update gc_export_task set update_time=#{time},is_deleted=1 where id = #{id} limit 1")
    int delete(@Param("id") BigInteger id,@Param("time")Integer time);

    @Select("select * from gc_export_task where is_deleted=0 and user_id=#{userId} limit #{offset},#{pageSize} ")
    List<GcExportTask> getByUserId(@Param("userId") BigInteger userId,@Param("offset") Integer offset,@Param("pageSize") Integer pageSize);

    @Select("select count(1) from gc_export_task where is_deleted=0 and user_id=#{userId}")
    int getByUserIdCount(@Param("userId") BigInteger userId);
}
