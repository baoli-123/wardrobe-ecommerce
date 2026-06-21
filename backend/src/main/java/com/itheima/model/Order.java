package com.itheima.model;
public class Order {
    private int id;
    private String clothesDetails;    //商品详细信息
    private double price;    //价格
    private int status; //订单状态（已付款：1，未付款：0）
    private int userId; //用户编号
    private User user;  //用户对象
    private String address;  //收货地址
    private String time;   //订单提交时间
    public Order() {
    }

    public Order(int id, String clothesDetails, double price, int status, int userId, User user, String address, String time) {
        this.id = id;
        this.clothesDetails = clothesDetails;
        this.price = price;
        this.status = status;
        this.userId = userId;
        this.user = user;
        this.address = address;
        this.time = time;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClothesDetails() {
        return clothesDetails;
    }

    public void setClothesDetails(String clothesDetails) {
        this.clothesDetails = clothesDetails;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
