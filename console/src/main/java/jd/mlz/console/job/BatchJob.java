package jd.mlz.console.job;

import jd.mlz.module.module.gcRecord.entity.GcExportTask;
import jd.mlz.module.module.gcRecord.entity.GcTaskBatch;
import jd.mlz.module.module.gcRecord.service.GcRecordBaseService;
import jd.mlz.module.utils.BaseUtils;
import jd.mlz.module.utils.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;

/**
* @author wangfeiyu
** @date 2025-03-15
*/
@Slf4j
@DisallowConcurrentExecution
@Component
public class BatchJob implements Job {

    @Autowired
    private GcRecordBaseService gcRecordBaseService;
    //生成批次
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        log.info("BatchJob开始执行任务");
        //获取最后批次的endTaskId
        GcTaskBatch lastBatch = gcRecordBaseService.getLastTaskBatch();
        if (BaseUtils.isEmpty(lastBatch)){
            log.info("没有批次,尝试创建第一批次");
            BigInteger id = new BigInteger(String.valueOf(1));
            BigInteger batchId = gcRecordBaseService.addTaskBatch(id,id);
            if (BaseUtils.isEmpty(batchId)){
                log.info("生成批次失败");
            }
        }else {
            batchGeneration(lastBatch);
        }

    }

    //batchGeneration
    void batchGeneration(GcTaskBatch lastBatch){

        //根据截止id获取至今的所有任务id，进行批次生成
        List<BigInteger> taskIdList = gcRecordBaseService.getTaskIdList(lastBatch.getEndTaskId());
        if (BaseUtils.isEmpty(taskIdList)){
            log.info("没有多余任务");
            return;
        }
        //获取每批次的任务数量
        int count = Integer.parseInt(SpringUtils.getProperty("batch.size"));

        //任务数不足批次数量时
        if (taskIdList.size() <= count && !BaseUtils.isEmpty(taskIdList)){
            //最长等待时间
            int waitTime = Integer.parseInt(SpringUtils.getProperty("wait.time"));
            if (BaseUtils.currentSeconds() - lastBatch.getCreateTime() < waitTime){
                log.info("任务数不足批次数量，且等待时间未到");
                return;
            }
            //超时，生成批次
            BigInteger batchId = gcRecordBaseService.addTaskBatch(lastBatch.getEndTaskId().add(BigInteger.ONE),taskIdList.get(taskIdList.size()-1));
            if (BaseUtils.isEmpty(batchId)){
                log.info("生成批次失败");
            }
        }
        //任务数大于批次数量时
        int batchCount = taskIdList.size()/count;
        BigInteger startTaskId = lastBatch.getEndTaskId().add(BigInteger.ONE);
        BigInteger endTaskId = null;
        for (int i = 0; i < batchCount; i++){
            endTaskId = startTaskId.add(BigInteger.valueOf(count));
            BigInteger batchId = gcRecordBaseService.addTaskBatch(startTaskId,endTaskId);
            if (BaseUtils.isEmpty(batchId)){
                log.info("生成批次失败。id范围："+startTaskId+"~"+endTaskId);
            }
            startTaskId = endTaskId.add(BigInteger.ONE);
        }
    }
}
