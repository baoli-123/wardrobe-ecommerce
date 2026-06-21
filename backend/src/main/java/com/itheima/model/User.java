package com.itheima.model;

public class User {
    private int id;          //编号
    private String userName;    //用户名
    private String password;    //密码
    private String phone;       //电话
    private String address;    //地址
    private int role;          //角色（普通用户2、管理员1）
    private String token;      //token

    public User() {
    }

    public User(int id, String userName, String password, String phone, String address, int role, String token) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.role = role;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
