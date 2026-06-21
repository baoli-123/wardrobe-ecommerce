package com.itheima.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.model.Order;
import com.itheima.service.OrderService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ManageOrderServlet extends HttpServlet {
    OrderService orderService = new OrderService();
    @Override
    public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter pw=resp.getWriter();
        String action=req.getServletPath();
        ObjectMapper om=new ObjectMapper();
        BufferedReader reqReader=req.getReader();
        switch(action){
            case"/allOrderData":
                List<Order> orderData = orderService.getOrderByParams(null,null);
                om.writeValue(pw,orderData);
                break;
            case"/searchOrder":
                String userName=req.getParameter("userName");
                String statusName=req.getParameter("status");
                Integer status=null;
                if(statusName!=null&&!statusName.isEmpty()){
                    status=Integer.parseInt(statusName);
                }
                List<Order>searchOrders= orderService.getOrderByParams(userName,status);
                om.writeValue(pw,searchOrders);
                break;
            case"/deliveryOrder":
                Order updateOrder=om.readValue(reqReader,Order.class);
                int count= orderService.upDateOrderStatus(updateOrder.getId(),1,2);
                if(count>0){
                    om.writeValue(pw,"发货成功！");
                }else{
                    om.writeValue(pw,"发货失败！");
                }
                break;
        }
    }
}
