package jd.mlz.module.module.gcRecord.service;

import jd.mlz.module.module.gcRecord.dto.GcExportTaskDTO;
import jd.mlz.module.module.gcRecord.entity.GcExportTask;
import jd.mlz.module.module.gcRecord.mapper.GcExportTaskMapper;
import jd.mlz.module.utils.BaseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author wangfeiyu
 * * @date 2025-03-08
 */

@Service
@Slf4j
public class GcExportTaskService {

    @Resource
    private GcExportTaskMapper gcExportTaskMapper;

    @Transactional
    public BigInteger editTask(BigInteger taskId,BigInteger regionId,Integer recordStartTime,Integer recordEndTime,
                               Integer result,String url,Integer processTime,BigInteger userId){
        // 如果taskId为空，则新增一条记录
        if (BaseUtils.isEmpty(taskId)){
            GcExportTask gcExportTask = new GcExportTask();
            gcExportTask.setTaskType(1);
            gcExportTask.setRegionId(regionId);
            gcExportTask.setStartTime(recordStartTime);
            gcExportTask.setEndTime(recordEndTime);
            gcExportTask.setResult(result);
            gcExportTask.setFileUrl(null);
            gcExportTask.setUserId(userId);
            gcExportTask.setStatus(0);
            gcExportTask.setCreateTime(BaseUtils.currentSeconds());
            gcExportTask.setUpdateTime(BaseUtils.currentSeconds());
            gcExportTask.setIsDeleted(0);
            gcExportTask.setProcessTime(0);
            Integer lines = gcExportTaskMapper.insert(gcExportTask);
            if (lines == 0){
                log.info("task新增任务失败");
                return null;
            }
            return gcExportTask.getId();
        }

        // 如果taskId不为空，则更新记录

        //回写url
        if (BaseUtils.isEmpty(url)){
            log.info("url为空");
            return null;
        }
        GcExportTask gcExportTask = new GcExportTask();
        gcExportTask.setId(taskId);
        gcExportTask.setFileUrl(url);
        gcExportTask.setStatus(1);
        gcExportTask.setProcessTime(processTime);
        int lines = gcExportTaskMapper.update(gcExportTask);
        if (lines == 0){
            log.info("更新任务失败");
            return null;
        }
        return taskId;
    }

    //获取任务列表
    public List<GcExportTaskDTO> getDownloadList(BigInteger userId, Integer page, Integer pageSize){
        page = page == null ? 1 : page;
        if (BaseUtils.isEmpty(userId)){
            return null;
        }
        Integer offset = (page - 1) * pageSize;

        //查任务列表
        List<GcExportTask> taskList = gcExportTaskMapper.getByUserId(userId,offset,pageSize);
        if (BaseUtils.isEmpty(taskList)){
            return null;
        }

        //查列表中任务状态
        List<BigInteger> idList = new ArrayList<>();
        for (GcExportTask task : taskList){
            idList.add(task.getId());
        }

        //拼接
        List<GcExportTaskDTO> taskDTOList = new ArrayList<>();
        for (GcExportTask task : taskList){
            GcExportTaskDTO gcExportTaskDTO = new GcExportTaskDTO();
            gcExportTaskDTO.setId(task.getId());
            gcExportTaskDTO.setTaskType(task.getTaskType());
            gcExportTaskDTO.setRegionId(task.getRegionId());
            gcExportTaskDTO.setStartTime(task.getStartTime());
            gcExportTaskDTO.setEndTime(task.getEndTime());
            gcExportTaskDTO.setResult(task.getResult());
            gcExportTaskDTO.setUrl(task.getFileUrl());
            gcExportTaskDTO.setStatus(task.getStatus()==0?"待执行":task.getProcessTime()==0?"执行中":"执行完成");
            gcExportTaskDTO.setCreateTime(task.getCreateTime());
            taskDTOList.add(gcExportTaskDTO);
        }

        return taskDTOList;
    }

    public Integer getDownloadListCount(BigInteger userId){
        if (BaseUtils.isEmpty(userId)){
            return null;
        }
        return gcExportTaskMapper.getByUserIdCount(userId);
    }

    public GcExportTask getTaskByTaskId(BigInteger taskId) {
        if (BaseUtils.isEmpty(taskId)){
            return null;
        }
        return gcExportTaskMapper.getById(taskId);
    }

    public List<GcExportTask> getExecutableTasks() {
        return gcExportTaskMapper.getByStatus();
    }

    public boolean getLock(BigInteger taskId){
        if (BaseUtils.isEmpty(taskId)){
            return false;
        }
        int lines = gcExportTaskMapper.getLock(taskId);
        return lines != 0;
    }

    public List<GcExportTask> getExecutableTasksByBatch(BigInteger startTaskId, BigInteger endTaskId) {
        if (BaseUtils.isEmpty(startTaskId) || BaseUtils.isEmpty(endTaskId)){
            return null;
        }
        return gcExportTaskMapper.getByBatch(startTaskId,endTaskId);
    }

    public List<BigInteger> getTaskIdList(BigInteger endTaskId) {
        if (BaseUtils.isEmpty(endTaskId)){
            return null;
        }
        return gcExportTaskMapper.getTaskIdList(endTaskId);
    }
}
