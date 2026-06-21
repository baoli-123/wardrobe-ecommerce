package com.itheima.service;

import com.itheima.dao.CartDao;
import com.itheima.model.Cart;

import java.util.List;

public class CartService {
    CartDao cartDao = new CartDao();
    public String addToCart(Cart cart){
        try{
            return cartDao.addClothesToCart(cart);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    public List<Cart> getAllCartData(int userId){
        try{
            return cartDao.allCartData(userId);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    public int updateCartData(int id,int amount){
        try{
            return cartDao.updateClothData(id,amount);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    public String delCartData(int id){
        try{
            return cartDao.delCartData(id);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
