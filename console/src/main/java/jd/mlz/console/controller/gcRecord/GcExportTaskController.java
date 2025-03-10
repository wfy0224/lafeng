package jd.mlz.console.controller.gcRecord;

import jd.mlz.console.domain.GcExportTaskVO;
import jd.mlz.console.domain.PagingVO;
import jd.mlz.module.module.gcRecord.dto.GcExportTaskDTO;
import jd.mlz.module.module.gcRecord.service.GcRecordBaseService;
import jd.mlz.module.module.region.dto.RegionDTO;
import jd.mlz.module.module.region.service.RegionService;
import jd.mlz.module.utils.BaseUtils;
import jd.mlz.module.utils.Response;
import jd.mlz.module.utils.SpringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author wangfeiyu
 * * @date 2025-03-08
 */

@RestController
public class GcExportTaskController {

    @Autowired
    private GcRecordBaseService gcRecordBaseService;

    @Autowired
    private RegionService regionService;
    @RequestMapping("/collection_records/violations/add_download_task")
    public Response addDownloadTask(@RequestParam("regionId")String regionId,
                                    @RequestParam("startTime")String startTime,
                                    @RequestParam("endTime")String endTime){

        //参数校验
        if (BaseUtils.isEmpty(regionId)){
            return new Response(1013);
        }

        //存任务到数据库
        BigInteger taskId = gcRecordBaseService.addTask(new BigInteger(regionId),new Integer(startTime),new Integer(endTime),new BigInteger("1"));
        if (BaseUtils.isEmpty(taskId)){
            return new Response(3002);
        }
        return new Response(3001);
    }

    @RequestMapping("/collection_records/violations/download_list")
    public Response getDownloadList(@RequestParam("page")Integer page, @RequestParam("userId")String userId){
        page = page == null ? 1 : page;
        Integer pageSize = new Integer(SpringUtils.getProperty("pageSize"));
        //校验参数
        if (BaseUtils.isEmpty(userId)){
            return new Response(1013);
        }
        //分页查出任务列表
        List<GcExportTaskDTO> taskDTOList = gcRecordBaseService.getTaskDownloadList(new BigInteger(userId),page,pageSize);
        int total = gcRecordBaseService.getTaskTotal(new BigInteger(userId));

        //通过regionId查出区域名称
        List<BigInteger> regionIdList = new ArrayList<>();
        for (GcExportTaskDTO taskDTO : taskDTOList){
            regionIdList.add(taskDTO.getRegionId());
        }
        List<RegionDTO> regionIdAndNameDTO = regionService.getRegionNameByIds(regionIdList);
        HashMap<BigInteger,String> regionIdAndNameMap = new HashMap<>();
        for (RegionDTO regionDTO : regionIdAndNameDTO){
            regionIdAndNameMap.put(regionDTO.getId(),regionDTO.getRegionFullName());
        }


        List<GcExportTaskVO> taskVoList = new ArrayList<>();
        for (GcExportTaskDTO taskDTO : taskDTOList){
            String keyword = regionIdAndNameMap.get(taskDTO.getRegionId())+BaseUtils.timeStamp2Date(taskDTO.getStartTime())+"至"+BaseUtils.timeStamp2Date(taskDTO.getEndTime());
            GcExportTaskVO taskVo = new GcExportTaskVO();
            taskVo.setId(taskDTO.getId());
            taskVo.setResult(taskDTO.getResult()==0?"不合格":taskDTO.getResult()==1?"一般":"优秀");
            taskVo.setKeyword(keyword);
            taskVo.setStatus(taskDTO.getStatus());
            taskVo.setFileUrl(taskDTO.getUrl());
            taskVo.setCreateTime(BaseUtils.timeStamp2Date(taskDTO.getCreateTime()));
            taskVoList.add(taskVo);
        }

        PagingVO pagingVO = new PagingVO();
        pagingVO.setList(taskVoList);
        pagingVO.setPage(page);
        pagingVO.setTotal(total);
        return new Response(1001,pagingVO);
    }
}
