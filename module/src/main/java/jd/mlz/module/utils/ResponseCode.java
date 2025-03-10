package jd.mlz.module.utils;

import java.util.HashMap;
import java.util.Map;

public class ResponseCode {
    private static final Map<Integer, String> statusMap = new HashMap<Integer, String>();

    static {
        statusMap.put(1001, "OK");
        statusMap.put(1002, "没有登录哦~");
        statusMap.put(1010, "账号密码不匹配或账号不存在");
        statusMap.put(1011, "验证码发送成功");
        statusMap.put(1012, "任务添加成功");
        statusMap.put(1013, "缺少参数");

        statusMap.put(1003,"账号已登陆");
        //create user and forget password
        statusMap.put(2014, "账号尚未注册");



        //crond
        statusMap.put(3001, "任务添加成功");
        statusMap.put(3002, "任务添加失败");
        statusMap.put(3003, "任务删除成功");
        statusMap.put(3004, "任务删除失败");
        statusMap.put(3005, "任务更新成功");
        statusMap.put(3006, "任务更新失败");

        statusMap.put(4003, "没有权限");
        statusMap.put(4004, "链接超时");
        statusMap.put(4005, "操作失败");
    }

    public static String getMsg(Integer code) {
        return statusMap.get(code);
    }
}
