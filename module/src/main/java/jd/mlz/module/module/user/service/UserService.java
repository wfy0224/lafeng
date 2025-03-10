package jd.mlz.module.module.user.service;

import jd.mlz.module.module.user.dto.UserDTO;
import jd.mlz.module.module.user.entity.User;
import jd.mlz.module.module.user.mapper.UserMapper;
import jd.mlz.module.utils.BaseUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;

/**
 * @author wangfeiyu
 * * @date 2025-03-08
 */

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    public User getById(BigInteger id) {
        if (BaseUtils.isEmpty(id)){
            return null;
        }
        return userMapper.getById(id);
    }

    public List<UserDTO> getNameListByIds(List<BigInteger> idList) {
        if (BaseUtils.isEmpty(idList)){
            return null;
        }
        StringBuilder ids = new StringBuilder();
        for (BigInteger bigInteger : idList) {
            ids.append(bigInteger).append(",");
        }
        return userMapper.getNameByIds(ids.substring(0,ids.length()-1).toString());
    }
}
