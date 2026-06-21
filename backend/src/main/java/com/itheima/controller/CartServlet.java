package com.itheima.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.model.Cart;
import com.itheima.service.CartService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class CartServlet extends HttpServlet {
    CartService cartService = new CartService();
    @Override
    public void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter pw=resp.getWriter();
        String action=req.getServletPath();
        ObjectMapper om = new ObjectMapper();
        BufferedReader reqReader = req.getReader();
        switch (action){
            case"/addToCart":
                System.out.println("添加购物车");
                Cart cart=om.readValue(reqReader, Cart.class);
                String addToCartMsg = cartService.addToCart(cart);
                om.writeValue(pw,addToCartMsg);
                break;
            case"/getCartDataByUser":
                System.out.println("查询指定用户的购物车数据");
                int userId=Integer.parseInt(req.getParameter("userId"));
                List<Cart> allCartData=
                        cartService.getAllCartData(userId);
                om.writeValue(pw,allCartData);
                break;
            case"/updateCartData":
                System.out.println("修改购物车数据");
                cart=om.readValue(reqReader,Cart.class);
                int updateCount=cartService.updateCartData(cart.getId(),
                        cart.getAmount());
                om.writeValue(pw,updateCount);
                break;
            case"/delCartData":
                System.out.println("删除购物车数据");
                cart=om.readValue(reqReader,Cart.class);
                String msg=cartService.delCartData(cart.getId());
                om.writeValue(pw,msg);
                break;
        }
    }
}
