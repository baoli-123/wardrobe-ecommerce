package com.itheima.model;

import java.time.LocalDateTime;

public class Cart {
    private int id;         //编号
    private int clothId;  //服装编号
    private Clothes clothes;  //服装信息
    private String clothSize; //服装尺码
    private int amount;   //服装数量
    private int userId;   //用户编号
    private User user;     //用户信息
    private LocalDateTime date;  //添加日期

    public Cart() {
    }

    public Cart(int clothId, int amount,int userId) {
        this.amount = amount;
        this.clothId = clothId;
        this.userId = userId;
    }

    public Cart(int id, int clothId, int amount, int userId, LocalDateTime date) {
        this.id = id;
        this.clothId = clothId;
        this.amount = amount;
        this.userId = userId;
        this.date = date;
    }

    public Cart(int clothId, String clothSize, int userId) {
        this.clothId = clothId;
        this.clothSize = clothSize;
        this.userId = userId;
    }

    public String getClothSize() {
        return clothSize;
    }

    public void setClothSize(String clothSize) {
        this.clothSize = clothSize;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClothId() {
        return clothId;
    }

    public void setClothId(int clothId) {
        this.clothId = clothId;
    }

    public Clothes getClothes() {
        return clothes;
    }

    public void setClothes(Clothes clothes) {
        this.clothes = clothes;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", clothId=" + clothId +
                ", clothes=" + clothes +
                ", amount=" + amount +
                ", userId=" + userId +
                ", user=" + user +
                ", date=" + date +
                '}';
    }
}
