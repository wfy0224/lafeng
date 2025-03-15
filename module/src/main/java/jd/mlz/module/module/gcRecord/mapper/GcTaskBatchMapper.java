package jd.mlz.module.module.gcRecord.mapper;

import jd.mlz.module.module.gcRecord.entity.GcTaskBatch;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import java.math.BigInteger;
import java.util.List;

/**
 * <p>
 * 任务批次表 Mapper 接口
 * </p>
 *
 * @author wangfeiyu
 * @since 2025-03-15
 */
@Mapper
public interface GcTaskBatchMapper{

    int insert(@Param("gcTaskBatch") GcTaskBatch gcTaskBatch);

    int update(@Param("gcTaskBatch") GcTaskBatch gcTaskBatch);

    @Select("select * from gc_task_batch where id=#{id} and is_deleted=0 limit 1")
    GcTaskBatch getById(@Param("id") BigInteger id);

    @Select("select * from gc_task_batch where id=#{id} limit 1")
    GcTaskBatch extractById(@Param("id") BigInteger id);

    @Update("update gc_task_batch set update_time=#{time},is_deleted=1 where id = #{id} limit 1")
    int delete(@Param("id") BigInteger id,@Param("time")Integer time);

    @Select("select * from gc_task_batch where status=0 and is_deleted=0")
    List<GcTaskBatch> getByStatus();

    @Update("update gc_task_batch set start_time=#{time},update_time=#{time},status=1 where id = #{batchId} and status=0 limit 1")
    int getLock(@Param("time")Integer time, @Param("batchId") BigInteger batchId);

    @Select("select * from gc_task_batch where is_deleted=0 order by id desc limit 1")
    GcTaskBatch getLast();
}
