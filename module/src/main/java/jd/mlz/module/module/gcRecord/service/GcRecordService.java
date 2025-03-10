package jd.mlz.module.module.gcRecord.service;

import jd.mlz.module.module.gcRecord.entity.GcRecord;
import jd.mlz.module.module.gcRecord.mapper.GcRecordMapper;
import jd.mlz.module.utils.BaseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;

/**
 * @author wangfeiyu
 * * @date 2025-03-08
 */

@Service
@Slf4j
public class GcRecordService {

    @Resource
    private GcRecordMapper gcRecordMapper;

    public List<GcRecord> getGcRecordList(BigInteger regionId, Integer recordStartTime, Integer recordEndTime,
                                          Integer pageNum,Integer pageSize,Integer result) {
        if (BaseUtils.isEmpty(regionId) || BaseUtils.isEmpty(recordStartTime) || BaseUtils.isEmpty(recordEndTime)
                ||BaseUtils.isEmpty(pageNum) || BaseUtils.isEmpty(pageSize)){
            return null;
        }
        if (BaseUtils.isEmpty(result) || result==0 || result==1 || result==2){
            Integer offset = (pageNum - 1) * pageSize;
            return gcRecordMapper.getGcRecordList(regionId,recordStartTime,recordEndTime,offset,pageSize,result);
        }else {
            log.info("result 校验失败");
            return null;
        }

    }

    public List<GcRecord> getGcRecordList(BigInteger regionId, Integer recordStartTime, Integer recordEndTime,Integer result) {
        if (BaseUtils.isEmpty(regionId) || BaseUtils.isEmpty(recordStartTime) || BaseUtils.isEmpty(recordEndTime)){
            return null;
        }
        if (BaseUtils.isEmpty(result) || result==0 || result==1 || result==2){
            return gcRecordMapper.getGcRecordList(regionId,recordStartTime,recordEndTime,null,null,result);
        }else {
            log.info("result 校验失败");
            return null;
        }

    }

    public GcRecord getById(BigInteger id) {
        if (BaseUtils.isEmpty(id)){
            return null;
        }
        return gcRecordMapper.getById(id);
    }

    public Integer getRecordTotal(BigInteger regionId, Integer recordStartTime, Integer recordEndTime, Integer result) {
        if (BaseUtils.isEmpty(regionId) || BaseUtils.isEmpty(recordStartTime) || BaseUtils.isEmpty(recordEndTime)){
            return null;
        }
        return gcRecordMapper.getTotal(regionId,recordStartTime,recordEndTime,result);
    }
}
