package com.itheima.filter;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.dao.UserDao;
import com.itheima.model.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.Filter;

public class AccessFilter implements Filter {
    UserDao userDao = new UserDao();
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response=(HttpServletResponse)resp;
        PrintWriter pw=resp.getWriter();
        ObjectMapper om=new ObjectMapper();
        String token=request.getHeader("token");
        if(token!=null){
            int userId;
            try{
                userId=Integer.parseInt(JWT.decode(token).getAudience().get(0));
                User user=userDao.getUserById(userId);
                if(user==null){
                    System.out.println("用户不存在");
                    return;
                }
                chain.doFilter(request,response);
            } catch (Exception e){
                throw new RuntimeException(e);
            }
        } else {
            om.writeValue(pw,"暂无权限！");
        }
    }
}
