package jd.mlz.module.module.user.mapper;

import jd.mlz.module.module.user.dto.UserDTO;
import jd.mlz.module.module.user.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import java.math.BigInteger;
import java.util.List;

/**
 * <p>
 * 系统用户表 Mapper 接口
 * </p>
 *
 * @author wangfeiyu
 * @since 2025-03-08
 */
@Mapper
public interface UserMapper{

    int insert(@Param("user") User user);

    int update(@Param("user") User user);

    @Select("select * from user where id=#{id} and is_deleted=0 limit 1")
    User getById(@Param("id") BigInteger id);

    @Select("select * from user where id=#{id} limit 1")
    User extractById(@Param("id") BigInteger id);

    @Update("update user set update_time=#{time},is_deleted=1 where id = #{id} limit 1")
    int delete(@Param("id") BigInteger id,@Param("time")Integer time);

    @Select("select id,real_name from user where id in (${ids})")
    List<UserDTO> getNameByIds(@Param("ids") String ids);
}
