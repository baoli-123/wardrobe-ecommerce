package com.itheima.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.model.Order;
import com.itheima.service.OrderService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class OrderServlet extends HttpServlet {
    OrderService orderService = new OrderService();
    @Override
    public void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter pw=resp.getWriter();
        String action=req.getServletPath();
        ObjectMapper om = new ObjectMapper();
        BufferedReader reqReader=req.getReader();
        switch(action){
            case"/addOrder":
                String addOrderMsg="";
                List<Order> orderList=om.readValue(reqReader,new TypeReference<List<Order>>(){});
                for (Order order : orderList) {
                    addOrderMsg = orderService.addOrder(order);
                }
                om.writeValue(pw,addOrderMsg);
                break;
            case"/getOrderByUser":
                int userId=Integer.parseInt(req.getParameter("userId"));
                int status=Integer.parseInt(req.getParameter("status"));
                List<Order> orderByUser = orderService.getOrderByUser(userId,status);
                om.writeValue(pw,orderByUser);
                break;
            case"/payOrder":
                Order payOrder=om.readValue(reqReader,Order.class);
                int payCount = orderService.upDateOrderStatus(payOrder.getId(),0,1);
                if(payCount>0){
                    om.writeValue(pw,"支付成功！");
                }else{
                    om.writeValue(pw,"支付失败！");
                }
                break;
            case"/delOrderData":
                Order delOrder=om.readValue(reqReader,Order.class);
                String msg=orderService.delOrderData(delOrder.getId());
                om.writeValue(pw,msg);
                break;
            case"/receiveOrder":
                Order receiveOrder=om.readValue(reqReader,Order.class);
                int receiveCount = orderService.upDateOrderStatus(receiveOrder.getId(),2,3);
                if(receiveCount>0){
                    om.writeValue(pw,"收货成功！");
                }else{
                    om.writeValue(pw,"收货失败！");
                }
                break;
        }
    }
}
