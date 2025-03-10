package jd.mlz.module.module.gcRecord.mapper;

import jd.mlz.module.module.gcRecord.entity.GcExportTaskCrond;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import java.math.BigInteger;
import java.util.List;

/**
 * <p>
 * 异步导出任务状态表 Mapper 接口
 * </p>
 *
 * @author wangfeiyu
 * @since 2025-03-08
 */
@Mapper
public interface GcExportTaskCrondMapper{

    int insert(@Param("gcExportTaskCrond") GcExportTaskCrond gcExportTaskCrond);

    int update(@Param("gcExportTaskCrond") GcExportTaskCrond gcExportTaskCrond);

    @Select("select * from gc_export_task_crond where id=#{id} and is_deleted=0 limit 1")
    GcExportTaskCrond getById(@Param("id") BigInteger id);

    @Select("select * from gc_export_task_crond where id=#{id} limit 1")
    GcExportTaskCrond extractById(@Param("id") BigInteger id);

    @Update("update gc_export_task_crond set update_time=#{time},is_deleted=1 where id = #{id} limit 1")
    int delete(@Param("id") BigInteger id,@Param("time")Integer time);

    @Select("select task_id from gc_export_task_crond where is_deleted=0 and is_run=0 and is_end=0 limit 3")
    List<BigInteger> getByStatus();

    @Select("select * from gc_export_task_crond where is_deleted=0 and task_id in (${taskIdList})")
    List<GcExportTaskCrond> getByIds(@Param("taskIdList") String taskIdList);
}
