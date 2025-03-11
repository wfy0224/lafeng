package jd.mlz.console.crond;

import jd.mlz.console.domain.GcExportExcelVO;
import jd.mlz.module.module.gcRecord.entity.GcExportTask;
import jd.mlz.module.module.gcRecord.entity.GcRecord;
import jd.mlz.module.module.gcRecord.service.GcRecordBaseService;
import jd.mlz.module.module.region.dto.RegionDTO;
import jd.mlz.module.module.region.service.RegionService;
import jd.mlz.module.module.user.dto.UserDTO;
import jd.mlz.module.module.user.service.UserService;
import jd.mlz.module.utils.BaseUtils;
import jd.mlz.module.utils.OSSUtils;
import jd.mlz.module.utils.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * @author wangfeiyu
 * * @date 2025-03-09
 */

@Slf4j
@DisallowConcurrentExecution
@Component
public class TaskJob extends QuartzJobBean {

    @Autowired
    private GcRecordBaseService gcRecordBaseService;

    @Autowired
    private UserService userService;
    @Autowired
    private RegionService regionService;
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        //拉取需要执行的任务
        List<GcExportTask> taskList = gcRecordBaseService.getExecutableTasks();
        for (GcExportTask task : taskList){
            //尝试获取乐观锁
            if (!BaseUtils.isEmpty(gcRecordBaseService.getLock(task.getId()))){
                int timeStart = (int) System.currentTimeMillis() ;

                //根据条件获取不合格记录列表
                List<GcRecord> unqualifiedGcRecordList = gcRecordBaseService.getUnqualifiedGcRecordList(task.getRegionId(), task.getStartTime(), task.getEndTime());
                List<BigInteger> userIdList = new ArrayList<>();
                List<BigInteger> regionIdList = new ArrayList<>();
                for (GcRecord gcRecord : unqualifiedGcRecordList){
                    userIdList.add(gcRecord.getUserId());
                    regionIdList.add(gcRecord.getRegionId());
                }
                // 查用户名
                List<UserDTO> userIdAndNameList = userService.getNameListByIds(userIdList);
                HashMap<BigInteger,String> userIdAndNameMap = new HashMap<>();
                for (UserDTO userDTO : userIdAndNameList){
                    userIdAndNameMap.put(userDTO.getId(),userDTO.getRealName());
                }

                // 查区域全名
                List<RegionDTO> regionDTOList = regionService.getRegionNameByIds(regionIdList);
                HashMap<BigInteger,String> regionIdAndNameMap = new HashMap<>();
                for (RegionDTO regionDTO : regionDTOList){
                    regionIdAndNameMap.put(regionDTO.getId(),regionDTO.getRegionFullName());
                }

                //组装
                List<GcExportExcelVO> excelVOList = new ArrayList<>();
                for (GcRecord gcRecord : unqualifiedGcRecordList){
                    GcExportExcelVO excelVO = new GcExportExcelVO();
                    excelVO.setId(gcRecord.getId().toString());
                    excelVO.setHomeownerName(userIdAndNameMap.get(gcRecord.getUserId()));
                    excelVO.setRegion(regionIdAndNameMap.get(gcRecord.getRegionId()));
                    excelVO.setGarbageClassification(gcRecord.getGarbageClassification().toString());
                    excelVO.setGarbageWeight(gcRecord.getGarbageWeight().toString()+"kg");
                    excelVO.setResult(gcRecord.getResult()==0?"不合格":gcRecord.getResult()==1?"一般":"优秀");
                    excelVO.setCollectorId(gcRecord.getContractorUserId().toString());
                    excelVO.setCreateTime(BaseUtils.timeStamp2Date(gcRecord.getCreateTime()));
                    excelVOList.add(excelVO);
                }
                //执行任务
                String uuid = UUID.randomUUID().toString().replace("-", "");
                String fileName = uuid+".xlsx";
                try {
                    String url = OSSUtils.uploadExcelStreamToOSS(excelVOList, GcExportExcelVO.class, fileName);
                    int timeEnd = (int)System.currentTimeMillis();
                    BigInteger id = gcRecordBaseService.editTaskToEnd(task.getId(),url, timeEnd-timeStart);
                    if (BaseUtils.isEmpty(id)){
                        throw new RuntimeException("任务表回写失败"+task.getId());
                    }
                } catch (IOException e) {
                    log.info("导出Excel失败"+e.getMessage());
                    throw new RuntimeException(e);
                }

            }
        }
    }
}
