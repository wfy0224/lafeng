package jd.mlz.module.module.gcRecord.service;

import jd.mlz.module.module.gcRecord.entity.GcExportTaskCrond;
import jd.mlz.module.module.gcRecord.mapper.GcExportTaskCrondMapper;
import jd.mlz.module.utils.BaseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

/**
 * @author wangfeiyu
 * * @date 2025-03-08
 */
@Service
@Slf4j
public class GcExportTaskCrondService {

    @Resource
    private GcExportTaskCrondMapper gcExportTaskCrondMapper;

    @Transactional
    public BigInteger editTaskStatus(BigInteger taskId, Boolean isRun, Boolean isEnd) {
        if (BaseUtils.isEmpty(taskId)) {
            return null;
        }
        //校验taskId是否存在
        GcExportTaskCrond gcExportTaskCrond = gcExportTaskCrondMapper.getById(taskId);
        //taskId不存在，则新增记录
        if (BaseUtils.isEmpty(gcExportTaskCrond)) {
            GcExportTaskCrond newGcExportTaskCrond = new GcExportTaskCrond();
            newGcExportTaskCrond.setTaskId(taskId);
            newGcExportTaskCrond.setIsRun(0);
            newGcExportTaskCrond.setIsEnd(0);
            newGcExportTaskCrond.setCreateTime(BaseUtils.currentSeconds());
            newGcExportTaskCrond.setUpdateTime(BaseUtils.currentSeconds());
            newGcExportTaskCrond.setIsDeleted(0);
            Integer result = gcExportTaskCrondMapper.insert(newGcExportTaskCrond);
            if (result == 0) {
                log.info("新增任务失败");
                return null;
            }
            return newGcExportTaskCrond.getId();
        }

        // 如果taskId存在，则更新记录

        GcExportTaskCrond updateGcExportTaskCrond = new GcExportTaskCrond();
        updateGcExportTaskCrond.setTaskId(taskId);
        updateGcExportTaskCrond.setIsRun(isRun ? 1 : 0);
        updateGcExportTaskCrond.setIsEnd(isEnd ? 1 : 0);
        updateGcExportTaskCrond.setUpdateTime(BaseUtils.currentSeconds());
        updateGcExportTaskCrond.setIsDeleted(0);
        Integer result = gcExportTaskCrondMapper.update(updateGcExportTaskCrond);
        if (result == 0) {
            log.info("更新任务失败");
            return null;
        }
        return taskId;
    }

    // 获取任务状态
    public GcExportTaskCrond getTaskStatus(BigInteger taskId) {
        if (BaseUtils.isEmpty(taskId)) {
            return null;
        }
        return gcExportTaskCrondMapper.getById(taskId);
    }

    //批量获取任务状态
    public List<GcExportTaskCrond> getTaskStatus(List<BigInteger> taskIdList) {
        if (BaseUtils.isEmpty(taskIdList)) {
            return null;
        }
        String ids = BaseUtils.convertIdsToString(taskIdList);
        return gcExportTaskCrondMapper.getByIds(ids);
    }

    // 获取未开始任务
    public List<BigInteger> getNotExecutedTaskId() {
        return gcExportTaskCrondMapper.getByStatus();
    }

}
