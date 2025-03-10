package jd.mlz.module.utils;


import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class BaseUtils {
    public static int currentSeconds() {
        return (int) (System.currentTimeMillis() / 1000);
    }

    /**
     * 判断参数是否为空
     *
     * @param obj 校验的对象
     * @return
     */
    public static boolean isEmpty(Object obj) {
        if (obj instanceof List) {
            return obj == null || ((List<?>) obj).size() == 0;
        } else if (obj instanceof Set) {
            return obj == null || ((Set<?>) obj).size() == 0;
        } else if (obj instanceof Number) {
            DecimalFormat decimalFormat = new DecimalFormat();
            try {
                return decimalFormat.parse(obj.toString()).doubleValue() == 0;
            } catch (ParseException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return obj == null || "".equals(obj.toString());
        }
    }

    /**
     * md5加密
     *
     * @param text 待加密字符串
     * @return
     */
    public static String md5(String text) {
        String encodeStr = "";
        try {
            encodeStr = DigestUtils.md5Hex(text);
        } catch (Exception e) {
            return encodeStr;
        }
        return encodeStr;
    }

    /**
     * 时间戳转换成日期格式字符串
     *
     * @param seconds 精确到秒的字符串
     * @return
     */
    public static String timeStamp2Date(int seconds) {
        return timeStamp2Date(seconds, null);
    }

    public static String timeStamp2Date(int seconds, String format) {
        if (isEmpty(seconds)) {
            return "";
        }
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds + "000")));
    }

    public static String timeStamp2DateGMT(int seconds, String format) {
        if (isEmpty(seconds)) {
            return "";
        }
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        return sdf.format(new Date(Long.valueOf(seconds + "000")));
    }

    /**
     * 日期格式字符串转换成时间戳
     *
     * @param date_str 字符串日期
     * @return
     */
    public static int date2TimeStamp(String date_str) {
        return date2TimeStamp(date_str, null);
    }

    public static int date2TimeStamp(String date_str, String format) {
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            String timestamp = String.valueOf(sdf.parse(date_str).getTime() / 1000);
            int length = timestamp.length();
            if (length > 3) {
                return Integer.valueOf(timestamp.substring(0, length));
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获得指定长度的随机数
     *
     * @param num
     * @return
     */
    public static String getRandNumber(int num) {
        Random random = new Random();
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < num; i++) {
            result.append(random.nextInt(9)+1);
        }
        return result.toString();
    }

    public static String getRandString(int size) {
        String abc = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890";
        //指定长度size = 30
        //指定取值范围 abc 如果不指定取值范围，中文环境下会乱码
        String str = RandomStringUtils.random(size, abc);
        return str;
    }

    public static String formatPrice(int price) {
        String prefix = "￥";
        return prefix + formatPriceNum(price);
    }

    public static String formatPrice(BigInteger price) {
        int priceInt = price.intValue();
        return formatPrice(priceInt);
    }

    public static String formatPriceNum(int price){
        int points = (price % 100);
        if (points == 0) {
            return (price / 100) + ".00";
        } else {
            String pointStr = new DecimalFormat("00").format(points);
            return (price / 100) + "." + pointStr;
        }
    }

    public static String formatPriceNum(BigInteger price){
        int priceInt = price.intValue();
        return formatPriceNum(priceInt);
    }

    public static String formatWeight(int weight) {
        String endFix = "Kg";
        int points = (weight % 1000);
        if (points == 0) {
            return (weight / 1000) + ".00" + endFix;
        } else {
            return (weight / 1000) + "." + points + endFix;
        }
    }

    public static String implodeSearchParam(String param1, String param2) {
        String result;
        if (BaseUtils.isEmpty(param1) && BaseUtils.isEmpty(param2)) {
            result = null;
        } else if (!BaseUtils.isEmpty(param1) && !BaseUtils.isEmpty(param2)) {
            result = param1 + "," + param2;
        } else {
            result = BaseUtils.isEmpty(param1) ? param2 : param1;
        }
        return result;
    }

    public static double getFileSize(Long len, String unit) {
        double fileSize = 0;
        if ("B".equals(unit.toUpperCase())) {
            fileSize = (double) len;
        } else if ("K".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1024;
        } else if ("M".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1048576;
        } else if ("G".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1073741824;
        }
        return fileSize;
    }

    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }


    public static String getShortTime(Integer dateline) {
        String shortstring = null;
        String time = timeStamp2Date(dateline);
        Date date = getDateByString(time);
        if(date == null) return shortstring;

        long now = Calendar.getInstance().getTimeInMillis();
        long deltime = (now - date.getTime())/1000;
        if(deltime > 365*24*60*60) {
            shortstring = (int)(deltime/(365*24*60*60)) + "年前";
        } else if(deltime > 24*60*60) {
            shortstring = (int)(deltime/(24*60*60)) + "天前";
        } else if(deltime > 60*60) {
            shortstring = (int)(deltime/(60*60)) + "小时前";
        } else if(deltime > 60) {
            shortstring = (int)(deltime/(60)) + "分前";
        } else if(deltime > 1) {
            shortstring = deltime + "秒前";
        } else {
            shortstring = "1秒前";
       }
       return shortstring;
   }

    public static Date getDateByString(String time) {
        Date date = null;
        if (time == null)
            return date;

        String date_format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat format = new SimpleDateFormat(date_format);
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();

        }
        return date;
    }

    /**
     * 将 BigInteger 列表转换为逗号分隔的字符串，用于 SQL IN 语句。
     *
     * @param taskIdList 输入的 BigInteger 列表
     * @return 逗号分隔的字符串，如果列表为空或为 null，返回空字符串
     */
    public static String convertIdsToString(List<BigInteger> taskIdList) {
        if (taskIdList == null || taskIdList.isEmpty()) {
            return ""; // 返回空字符串，避免 SQL 语法错误
        }

        // 使用 Set 去重
        Set<BigInteger> uniqueIds = new HashSet<>(taskIdList);

        StringJoiner joiner = new StringJoiner(",");
        for (BigInteger id : uniqueIds) {
            if (id != null) { // 跳过 null 值
                joiner.add(id.toString());
            }
        }

        return joiner.toString();
    }
}
