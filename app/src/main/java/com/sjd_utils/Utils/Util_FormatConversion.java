package com.sjd_utils.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sjd on 2017/2/17.
 *
 * 关于数据类型转换的工具类
 */

public class Util_FormatConversion {
    /**
     * 时间戳转换成字符串（1402733340）输出（"2014-06-14"）
     *
     * @param time
     * @return
     */
    public static String getTimeString(Long time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd");
        String times = sdr.format(new Date(time * 1000L));
        return times;
    }

    /**
     * 将字符串转为时间戳
     */
    public static Long getTimeDate(String user_time) {
        Long re_time = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d;
        try {
            d = sdf.parse(user_time);
            re_time = d.getTime();
            re_time = re_time / 1000L;//多了三位毫秒级的数字
        } catch (ParseException e) {
            // TODO Auto-generated catch block e.printStackTrace();
        }
        return re_time;
    }
}
