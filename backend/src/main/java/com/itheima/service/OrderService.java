package com.itheima.service;

import com.itheima.dao.CartDao;
import com.itheima.dao.OrderDao;
import com.itheima.dao.UserDao;
import com.itheima.model.Order;
import com.itheima.model.User;

import java.util.List;

public class OrderService {
    OrderDao orderDao = new OrderDao();
    CartDao cartDao = new CartDao();
    UserDao userDao = new UserDao();
    public String addOrder(Order order){
        try{
            int count = orderDao.addOrder(order);
            if(count>0){
                cartDao.delUserCartData(order.getUserId());
                return"订单提交成功！";
            }else{
                return"订单提交失败！";
            }
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    public List<Order> getOrderByUser(int userId, int status){
        try{
            List<Order>orderByUser = orderDao.getOrderByUser(userId,status);
            return orderByUser;
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    public String delOrderData(int id){
        try{
            int count = orderDao.delOrderData(id);
            if(count>0){
                return"删除成功！";
            }else{
                return"删除失败！";
            }
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    public int upDateOrderStatus(int id,int initStatus,int finalStatus){
        try{
            int count=orderDao.updateOrderStatus(id,initStatus,finalStatus);
            return count;
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    public List<Order>getOrderByParams(String userName,Integer status){
        try{
            List<Order>orders=orderDao.getOrderByParams(userName,status);
            for (Order o:orders){
                User user = userDao.getUserById(o.getUserId());
                o.setUser(user);
            }
            return orders;
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
