package com.itheima.test;

import com.itheima.dao.UserDao;
import com.itheima.model.User;

/**
 * 快速测试 UserDao，直接右键运行 main 方法
 */
public class UserDaoTest {
    public static void main(String[] args) {
        UserDao dao = new UserDao();

        try {
            // 1. 测试添加用户
            User newUser = new User();
            newUser.setUserName("张三");
            newUser.setPassword("123456");
            newUser.setPhone("13800138000");
            newUser.setAddress("北京市朝阳区");
            newUser.setRole(2);

            int rows = dao.addUser(newUser);
            System.out.println("添加用户结果: " + (rows > 0 ? "成功" : "失败") + "，影响行数: " + rows);

            // 2. 测试按用户名查询
            User found = dao.findUserByName("张三");
            if (found != null) {
                System.out.println("查询到用户: " + found.getUserName());
                System.out.println("  ID: " + found.getId());
                System.out.println("  Phone: " + found.getPhone());
                System.out.println("  Role: " + found.getRole());
            } else {
                System.out.println("未查到该用户");
            }

            // 3. 测试登录查询
            User loginUser = dao.findUser("张三", "123456");
            if (loginUser != null) {
                System.out.println("\n登录验证成功！用户: " + loginUser.getUserName());
            } else {
                System.out.println("\n用户名或密码错误！");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
