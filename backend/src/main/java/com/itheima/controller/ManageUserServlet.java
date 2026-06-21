package com.itheima.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.model.User;
import com.itheima.model.UserVo;
import com.itheima.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ManageUserServlet extends HttpServlet {
    UserService userService = new UserService();
    @Override
    public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter pw=resp.getWriter();
        String action = req.getServletPath();
        ObjectMapper om=new ObjectMapper();
        BufferedReader reqReader=req.getReader();
        switch(action){
            case"/getAllUser":
                List<User> allUser=userService.getUserByParam(null);
                om.writeValue(pw,allUser);
                break;
            case"/delUser":
                User user=om.readValue(reqReader,User.class);
                String msg=userService.delUser(user.getId());
                om.writeValue(pw,msg);
                break;
            case"/searchUser":
                String nameOrPhone=req.getParameter("nameOrPhone");
                List<User>list = userService.getUserByParam(nameOrPhone);
                om.writeValue(pw,list);
                break;
            case"/editUser":
                UserVo uv=om.readValue(reqReader,UserVo.class);
                User u=userService.getCurrentUser(uv.getId());
                String tem=u.getPassword();
                uv.setNewpsw(uv.getPassword());
                uv.setPassword(tem);
                String updateMsg=userService.updateUser(uv);
                om.writeValue(pw,updateMsg);
                break;
            case"/addUser":
                user=om.readValue(reqReader,User.class);
                String addMsg=userService.register(user);
                om.writeValue(pw,addMsg);
                break;
        }
    }
}
