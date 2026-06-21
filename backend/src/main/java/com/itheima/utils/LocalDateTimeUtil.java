package com.itheima.utils;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class LocalDateTimeUtil {  
  
    // 定义日期时间格式  
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
  
    /**  
     * 将 LocalDateTime 转换为字符串  
     *  
     * @param localDateTime LocalDateTime 对象  
     * @return 格式化后的字符串  
     */  
    public static String formatLocalDateTime(LocalDateTime localDateTime) {
        return localDateTime.format(DATE_TIME_FORMATTER);  
    }  
  
    /**  
     * 将字符串转换为 LocalDateTime  
     *  
     * @param dateTimeStr 字符串表示的日期时间  
     * @return LocalDateTime 对象  
     */  
    public static LocalDateTime parseLocalDateTime(String dateTimeStr) {  
        return LocalDateTime.parse(dateTimeStr, DATE_TIME_FORMATTER);  
    }  

}