package jd.mlz.console.controller.gcRecord;

import jd.mlz.console.domain.GcRecordVO;
import jd.mlz.console.domain.PagingVO;
import jd.mlz.module.module.gcRecord.entity.GcRecord;
import jd.mlz.module.module.gcRecord.service.GcRecordBaseService;
import jd.mlz.module.module.region.dto.RegionDTO;
import jd.mlz.module.module.region.service.RegionService;
import jd.mlz.module.module.user.dto.UserDTO;
import jd.mlz.module.module.user.service.UserService;
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
import java.util.HashSet;
import java.util.List;

/**
 * @author wangfeiyu
 * * @date 2025-03-08
 */

@RestController
public class GcRecordController {
    @Autowired
    private GcRecordBaseService gcRecordBaseService;

    @Autowired
    private UserService userService;

    @Autowired
    private RegionService regionService;
    @RequestMapping("/collection_records/violations/list")
    public Response getGcRecordList(@RequestParam("regionId")String regionId,
                                    @RequestParam("startTime")String startTime,
                                    @RequestParam("endTime")String endTime,
                                    @RequestParam("page")Integer page){
        if (BaseUtils.isEmpty(regionId) || BaseUtils.isEmpty(startTime) || BaseUtils.isEmpty(endTime)){
            return new Response(1013);
        }
        page = page==null?1:page;
        Integer pageSize = new Integer(SpringUtils.getProperty("pageSize"));
        //查record
        List<GcRecord> gcRecordList = gcRecordBaseService.getUnqualifiedGcRecordList(new BigInteger(regionId), new Integer(startTime), new Integer(endTime), page,
                pageSize);
        if (BaseUtils.isEmpty(gcRecordList)){
            return new Response(1001,new ArrayList<>());
        }
        List<BigInteger> userIdList = new ArrayList<>();
        List<BigInteger> regionIdList = new ArrayList<>();
        for (GcRecord gcRecord : gcRecordList){
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
        List<GcRecordVO> gcRecordVOList = new ArrayList<>();
        for (GcRecord gcRecord : gcRecordList){
            GcRecordVO gcRecordVO = new GcRecordVO();
            gcRecordVO.setId(gcRecord.getId().toString());
            gcRecordVO.setHomeownerName(userIdAndNameMap.get(gcRecord.getUserId()));
            gcRecordVO.setRegion(regionIdAndNameMap.get(gcRecord.getRegionId()));
            gcRecordVO.setGarbageClassification(gcRecord.getGarbageClassification().toString());
            gcRecordVO.setGarbageWeight(gcRecord.getGarbageWeight().toString()+"kg");
            gcRecordVO.setResult(gcRecord.getResult()==0?"不合格":gcRecord.getResult()==1?"一般":"优秀");
            gcRecordVO.setLastBinPhoto(gcRecord.getLastBinPhoto());
            gcRecordVO.setCollectorId(gcRecord.getContractorUserId().toString());
            gcRecordVO.setCreateTime(BaseUtils.timeStamp2Date(gcRecord.getCreateTime()));
            gcRecordVOList.add(gcRecordVO);
        }
        PagingVO pagingVO = new PagingVO();
        pagingVO.setList(gcRecordVOList);
        pagingVO.setPage(page);
        pagingVO.setTotal(gcRecordBaseService.getUnqualifiedGcRecordTotal(new BigInteger(regionId), new Integer(startTime), new Integer(endTime)));
        return new Response(1001,pagingVO);
    }

}
