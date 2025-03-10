package jd.mlz.module.module.region.service;

import jd.mlz.module.module.region.dto.RegionDTO;
import jd.mlz.module.module.region.entity.Region;
import jd.mlz.module.module.region.mapper.RegionMapper;
import jd.mlz.module.utils.BaseUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * @author wangfeiyu
 * * @date 2025-03-08
 */

@Service
public class RegionService {
    @Resource
    private RegionMapper regionMapper;

    //getById
    public List<RegionDTO> getRegionNameByIds(List<BigInteger> idList) {
        if (BaseUtils.isEmpty(idList)){
            return null;
        }
        //全查
        List<Region> regionList = regionMapper.getAll();
        HashMap<BigInteger, Region> regionMap = new HashMap<>();
        //存到map
        for (Region region : regionList){
            regionMap.put(region.getId(),region);
        }

        //查找多个区域全名
        List<RegionDTO> regionDTOList = new ArrayList<>();

        for (BigInteger id : idList){
            //找父级并拼接
            RegionDTO regionDTO = new RegionDTO();
            StringBuilder fullName = new StringBuilder();
            getFullRegionName(regionMap,id,fullName);
            regionDTO.setId(id);
            regionDTO.setRegionFullName(fullName.toString());
            regionDTOList.add(regionDTO);
        }

        return regionDTOList;
    }

    void getFullRegionName(HashMap<BigInteger, Region> regionMap, BigInteger id, StringBuilder sb) {
        if (!BaseUtils.isEmpty(id) && !id.equals(BigInteger.ZERO)) {
            getFullRegionName(regionMap, regionMap.get(id).getParentId(), sb);
            sb.append(regionMap.get(id).getRegionName());
        }
    }

    public Region getById(BigInteger regionId) {
        if (BaseUtils.isEmpty(regionId)){
            return null;
        }
        return regionMapper.getById(regionId);
    }
}
