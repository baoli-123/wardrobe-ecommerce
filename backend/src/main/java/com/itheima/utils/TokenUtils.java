package com.itheima.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.util.Calendar;
import java.util.Date;
public class TokenUtils {
    //生成token
    public static String generateToken(int userId, String password) {
        return JWT.create()
                    .withAudience(String.valueOf(userId))    //保存token载荷
                    .withExpiresAt(offsetHour(new Date(), 2)) //设置过期时间
                    .sign(Algorithm.HMAC256(password));    //设置token秘钥
    }
    //获取当前时间之后的两个小时的时间对象
    public static Date offsetHour(Date currentDate, int hoursToAdd) {
        // 创建一个Calendar实例，并设置其时间为传入的currentDate
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        // 添加指定的小时数
        calendar.add(Calendar.HOUR_OF_DAY, hoursToAdd);
        // 获取新的Date对象
        return calendar.getTime();
    }
}