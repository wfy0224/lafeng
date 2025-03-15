package jd.mlz.module.module.gcRecord.service;

import jd.mlz.module.module.gcRecord.entity.GcTaskBatch;
import jd.mlz.module.module.gcRecord.mapper.GcTaskBatchMapper;
import jd.mlz.module.utils.BaseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;

/**
 * @author wangfeiyu
 * * @date 2025-03-15
 */

@Service
@Slf4j
public class GcTaskBatchService {

    @Resource
    private GcTaskBatchMapper gcTaskBatchMapper;

    public BigInteger edit(BigInteger batchId,Integer status,BigInteger startTaskId,BigInteger endTaskId,Integer startTime,Integer endTime){
        if (BaseUtils.isEmpty(batchId)){
            //新增数据
            //校验
            if (BaseUtils.isEmpty(startTaskId) || BaseUtils.isEmpty(endTaskId)){
                return null;
            }
            GcTaskBatch gcTaskBatch = new GcTaskBatch();
            gcTaskBatch.setStatus(0);
            gcTaskBatch.setStartTaskId(startTaskId);
            gcTaskBatch.setEndTaskId(endTaskId);
            gcTaskBatch.setCreateTime(BaseUtils.currentSeconds());
            gcTaskBatch.setUpdateTime(BaseUtils.currentSeconds());
            gcTaskBatch.setIsDeleted(0);
            int lines = gcTaskBatchMapper.insert(gcTaskBatch);
            if (lines == 0){
                log.info("task新增任务失败");
                return null;
            }
            return gcTaskBatch.getId();
        }

        //更新数据

        GcTaskBatch gcTaskBatch = new GcTaskBatch();
        gcTaskBatch.setId(batchId);
        gcTaskBatch.setStatus(status);
        gcTaskBatch.setStartTime(startTime);
        gcTaskBatch.setEndTime(endTime);
        gcTaskBatch.setUpdateTime(BaseUtils.currentSeconds());
        int lines = gcTaskBatchMapper.update(gcTaskBatch);
        if (lines == 0){
            log.info("task更新任务失败");
            return null;
        }
        return batchId;
    }

    //获取批次信息
    public List<GcTaskBatch> getTaskBatches(){
        return gcTaskBatchMapper.getByStatus();
    }

    public boolean getLock(BigInteger batchId) {
        if (BaseUtils.isEmpty(batchId)){
            return false;
        }
        int lines = gcTaskBatchMapper.getLock(BaseUtils.currentSeconds(), batchId);
        return lines == 1;
    }

    public GcTaskBatch getLastTaskBatch() {
        return gcTaskBatchMapper.getLast();
    }
}
