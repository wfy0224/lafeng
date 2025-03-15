package jd.mlz.module.module.gcRecord.service;

import jd.mlz.module.module.gcRecord.dto.GcExportTaskDTO;
import jd.mlz.module.module.gcRecord.entity.GcExportTask;
import jd.mlz.module.module.gcRecord.entity.GcRecord;
import jd.mlz.module.module.gcRecord.entity.GcTaskBatch;
import jd.mlz.module.module.region.service.RegionService;
import jd.mlz.module.utils.BaseUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;

/**
 * @author wangfeiyu
 * * @date 2025-03-08
 */

@Service
public class GcRecordBaseService {
    @Resource
    private GcRecordService gcRecordService;
    @Resource
    private GcExportTaskService gcExportTaskService;

    @Resource
    private RegionService regionService;

    @Resource
    private GcTaskBatchService gcTaskBatchService;
    //recordList

    public List<GcRecord> getGcRecordList(BigInteger regionId, Integer recordStartTime, Integer recordEndTime,
                                          Integer pageNum, Integer pageSize) {
        if (BaseUtils.isEmpty(regionId) || BaseUtils.isEmpty(pageNum) || BaseUtils.isEmpty(pageSize) || BaseUtils.isEmpty(regionService.getById(regionId))) {
            return null;
        }
        return gcRecordService.getGcRecordList(regionId, recordStartTime, recordEndTime, pageNum, pageSize, null);
    }

    public List<GcRecord> getUnqualifiedGcRecordList(BigInteger regionId, Integer recordStartTime, Integer recordEndTime) {
        if (BaseUtils.isEmpty(regionId) ||  BaseUtils.isEmpty(regionService.getById(regionId))) {
            return null;
        }
        return gcRecordService.getGcRecordList(regionId, recordStartTime, recordEndTime,0);
    }

    public GcRecord getRcRecordById(BigInteger id) {
        if (BaseUtils.isEmpty(id)) {
            return null;
        }
        return gcRecordService.getById(id);
    }

    public List<GcRecord> getUnqualifiedGcRecordList(BigInteger regionId, Integer recordStartTime, Integer recordEndTime,
                                                     Integer pageNum, Integer pageSize) {
        if (BaseUtils.isEmpty(regionId) || BaseUtils.isEmpty(pageNum) || BaseUtils.isEmpty(pageSize) || BaseUtils.isEmpty(regionService.getById(regionId))) {
            return null;
        }
        return gcRecordService.getGcRecordList(regionId, recordStartTime, recordEndTime, pageNum, pageSize, 0);
    }

    public Integer getUnqualifiedGcRecordTotal(BigInteger regionId, Integer recordStartTime, Integer recordEndTime){
        return gcRecordService.getRecordTotal(regionId, recordStartTime, recordEndTime, 0);
    }

    //task
    public BigInteger editTask(BigInteger taskId,String ossUrl,Integer processTime){
        return gcExportTaskService.editTask(taskId, null, null, null, null, ossUrl, processTime,null);
    }
    public BigInteger addTask(BigInteger regionId, Integer recordStartTime, Integer recordEndTime,BigInteger userId) {
        if (BaseUtils.isEmpty(regionId) || BaseUtils.isEmpty(recordStartTime) || BaseUtils.isEmpty(recordEndTime)
                || BaseUtils.isEmpty(regionService.getById(regionId)) || BaseUtils.isEmpty(userId)) {
            return null;
        }
        return gcExportTaskService.editTask(null, regionId, recordStartTime, recordEndTime, 0, null, null,userId);
    }

    public Integer getTaskTotal(BigInteger userId){
        return gcExportTaskService.getDownloadListCount(userId);
    }

    public List<GcExportTaskDTO> getTaskDownloadList(BigInteger userId, Integer page, Integer pageSize){
        return gcExportTaskService.getDownloadList(userId,page,pageSize);
    }

    public List<GcExportTask> getExecutableTasks() {
        return gcExportTaskService.getExecutableTasks();
    }

    public GcExportTask getTaskById(BigInteger taskId) {
        return gcExportTaskService.getTaskByTaskId(taskId);
    }
    public BigInteger editTaskToEnd(BigInteger taskId,String url,Integer processTime) {

        return gcExportTaskService.editTask(taskId, null, null, null, null,url,processTime,null);
    }

    public boolean getTaskLock(BigInteger taskId) {
        return gcExportTaskService.getLock(taskId);
    }

    public List<GcExportTask> getExecutableTasksByBatch(BigInteger startTaskId, BigInteger endTaskId) {
        if (BaseUtils.isEmpty(startTaskId) || BaseUtils.isEmpty(endTaskId)){
            return null;
        }
        return gcExportTaskService.getExecutableTasksByBatch(startTaskId,endTaskId);
    }

    //batch
    public List<GcTaskBatch> getTaskBatches(){
        return gcTaskBatchService.getTaskBatches();
    }
    public boolean getBatchLock(BigInteger batchId) {
        return gcTaskBatchService.getLock(batchId);
    }


    public BigInteger editTaskBatchEndTime(BigInteger id) {
        if (BaseUtils.isEmpty(id)){
            return null;
        }
        return gcTaskBatchService.edit(id,null,null,null,null,BaseUtils.currentSeconds());
    }

    public GcTaskBatch getLastTaskBatch() {
        return gcTaskBatchService.getLastTaskBatch();
    }

    public List<BigInteger> getTaskIdList(BigInteger endTaskId) {
        if (BaseUtils.isEmpty(endTaskId)){
            return null;
        }
        return gcExportTaskService.getTaskIdList(endTaskId);
    }

    public BigInteger addTaskBatch(BigInteger startTaskId, BigInteger endTaskId) {
        if (BaseUtils.isEmpty(startTaskId) || BaseUtils.isEmpty(endTaskId)){
            return null;
        }
        return gcTaskBatchService.edit(null,0,startTaskId,endTaskId,null,null);
    }
}
