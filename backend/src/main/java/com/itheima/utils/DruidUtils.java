package com.itheima.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
public class DruidUtils {
    // 声明连接池对象
    private static DataSource dataSource;
    // 初始化连接池容器
    static {
        try {
            // 加载druid.properties配置文件
            Properties properties = new Properties();
            InputStream propIn =  DruidUtils.class.getClassLoader()
                     .getResourceAsStream("druid.properties");
            properties.load(propIn);
            // 通过Druid的工厂，创建连接池对象
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            throw new RuntimeException("Druid连接池初始化失败...");
        }
    }
    // 获取连接池对象的静态方法
    public static DataSource getDataSource() {
        return dataSource;
    }
    // 获取数据库连接对象的静态方法
    public static Connection getConnection() throws Exception {
        return dataSource.getConnection();
    }
    // 释放资源的方法（Connection对象不是销毁，而是归还到连接池）
    public static void release(ResultSet resultSet, Statement statement,
                               Connection connection) {
        // 关闭ResultSet
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        // 关闭Statement
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        // 关闭Connection
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}