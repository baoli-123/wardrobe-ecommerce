package com.itheima.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.model.User;
import com.itheima.model.UserVo;
import com.itheima.service.UserService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class UserServlet extends HttpServlet {
    UserService userService = new UserService();
    @Override
    public void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter pw=resp.getWriter();
        String action=req.getServletPath();
        ObjectMapper om = new ObjectMapper();
        BufferedReader reqReader=req.getReader();
        switch (action) {
            case"/getCurrentUser":
                int id=Integer.parseInt(req.getParameter("id"));
                User currentUser=userService.getCurrentUser(id);
                currentUser.setPassword("");
                om.writeValue(pw,currentUser);
                break;
            case"/updateUser":
                UserVo userVo=om.readValue(reqReader,UserVo.class);
                String updateMsg=userService.updateUser(userVo);
                om.writeValue(pw,updateMsg);
                break;
        }

    }
}
