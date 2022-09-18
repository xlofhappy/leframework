package com.qweather.leframework.core.constant;

import java.time.format.DateTimeFormatter;

/**
 * @author xiaole
 * @date 2022-09-08T14:48:16+08:00
 */
public class DateTimeFormatConstant {

    public static final DateTimeFormatter yyyyMMdd = DateTimeFormatter.ofPattern("yyyyMMdd");
    public static final DateTimeFormatter yyyy_MM_dd = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter yyyyMMddHHmmss = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    public static final DateTimeFormatter yyyy_MM_dd_HH_mm_ss = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter yyyy_MM_ddTHH_mm_ssxxx = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssxxx");

}
