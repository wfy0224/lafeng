package jd.mlz.module.module.user.entity;


import com.baomidou.mybatisplus.annotation.IdType;

import com.baomidou.mybatisplus.annotation.TableId;

import java.math.BigInteger;

import java.io.Serializable;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统用户表
 * </p>
 *
 * @author wangfeiyu
 * @since 2025-03-08
 */
@Data
@Accessors(chain = true)
public class User{

    private BigInteger id;

    //用户手机号码
    private String phone;

    //盐
    private String salt;

    //加密密码
    private String password;

    //真实姓名
    private String realName;

    //身份证号码
    private String idCard;

    //性别：0女，1男
    private Integer sex;

    //证件照
    private String photo;

    private Integer birthDate;

    private Integer createTime;

    private Integer updateTime;

    private Integer isDeleted;

}
