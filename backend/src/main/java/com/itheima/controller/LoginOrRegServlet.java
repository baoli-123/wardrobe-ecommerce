package com.itheima.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.model.User;
import com.itheima.service.UserService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;

@WebServlet(urlPatterns ={"/register","/login"})
public class LoginOrRegServlet extends HttpServlet{
    UserService userService = new UserService ();

    @Override
    public void service(HttpServletRequest req,HttpServletResponse resp)
        throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        String action = req.getServletPath();
        ObjectMapper om = new ObjectMapper();
        PrintWriter pw = resp.getWriter();

        if ("/register".equals(action)) {
            // 注册：前端POST JSON body
            BufferedReader reqReader = req.getReader();
            User user = om.readValue(reqReader, User.class);
            String registerMsg = userService.register(user);
            om.writeValue(pw, registerMsg);
        } else if ("/login".equals(action)) {
            // 登录：前端GET请求，参数在URL上
            String userInfo = req.getParameter("userInfo");
            String password = req.getParameter("password");
            boolean isAdminLogin =
                    Boolean.parseBoolean(req.getParameter("isAdminLogin"));

            User loginUser = userService.login(userInfo, password);
            if (loginUser == null) {
                om.writeValue(pw, "用户名或密码错误！");
                return;
            }
            if (isAdminLogin) {
                int role = loginUser.getRole();
                if (role != 1) {
                    om.writeValue(pw, "您没有权限！");
                    return;
                }
            }
            om.writeValue(pw, loginUser);
        }
    }
}