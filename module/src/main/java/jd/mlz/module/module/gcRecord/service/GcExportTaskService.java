package jd.mlz.module.module.gcRecord.service;

import jd.mlz.module.module.gcRecord.dto.GcExportTaskDTO;
import jd.mlz.module.module.gcRecord.entity.GcExportTask;
import jd.mlz.module.module.gcRecord.entity.GcExportTaskCrond;
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

    @Resource
    private GcExportTaskCrondService gcExportTaskCrondService;
    @Transactional
    public BigInteger editTask(BigInteger taskId,BigInteger regionId,Integer recordStartTime,Integer recordEndTime,
                               Integer result,String fileName,String ossBucket, String ossPath,String ossUrl,
                               String errorMsg,Integer processTime,BigInteger userId){
        // 如果taskId为空，则新增一条记录
        if (BaseUtils.isEmpty(taskId)){
            GcExportTask gcExportTask = new GcExportTask();
            gcExportTask.setTaskType(1);
            gcExportTask.setRegionId(regionId);
            gcExportTask.setStartTime(recordStartTime);
            gcExportTask.setEndTime(recordEndTime);
            gcExportTask.setResult(result);
            gcExportTask.setFileName(null);
            gcExportTask.setOssBucket(null);
            gcExportTask.setOssPath(null);
            gcExportTask.setOssUrl(null);
            gcExportTask.setErrorMsg(null);
            gcExportTask.setUserId(userId);
            gcExportTask.setCreateTime(BaseUtils.currentSeconds());
            gcExportTask.setUpdateTime(BaseUtils.currentSeconds());
            gcExportTask.setIsDeleted(0);
            gcExportTask.setProcessTime(0);
            Integer lines = gcExportTaskMapper.insert(gcExportTask);

            BigInteger crondId = gcExportTaskCrondService.editTaskStatus(gcExportTask.getId(), false, false);

            if (lines == 0){
                log.info("task新增任务失败");
                return null;
            }
            if (BaseUtils.isEmpty(crondId)){
                log.info("crond新增任务失败");
                return null;
            }
            return gcExportTask.getId();
        }

        // 如果taskId不为空，则更新记录
        //参数校验
        if (BaseUtils.isEmpty(fileName) || BaseUtils.isEmpty(ossBucket) || BaseUtils.isEmpty(ossPath) || BaseUtils.isEmpty(ossUrl) ){
            log.info("参数校验失败");
            return null;
        }
        GcExportTask gcExportTask = new GcExportTask();
        gcExportTask.setId(taskId);
        gcExportTask.setFileName(fileName);
        gcExportTask.setOssBucket(ossBucket);
        gcExportTask.setOssPath(ossPath);
        gcExportTask.setOssUrl(ossUrl);
        gcExportTask.setErrorMsg(errorMsg);
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
        List<GcExportTaskCrond> taskStatusList = gcExportTaskCrondService.getTaskStatus(idList);
        HashMap<BigInteger,String> taskStatusMap = new HashMap<>();
        for (GcExportTaskCrond taskStatus : taskStatusList){
            String status = taskStatus.getIsRun() == 1 ? "运行中" : taskStatus.getIsEnd() == 1 ? "已完成" : "未开始";
            taskStatusMap.put(taskStatus.getTaskId(),status);
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
            gcExportTaskDTO.setUrl(task.getOssUrl());
            gcExportTaskDTO.setStatus(taskStatusMap.get(task.getId()));
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
}
