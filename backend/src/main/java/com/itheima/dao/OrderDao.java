package com.itheima.dao;

import com.itheima.model.Order;
import com.itheima.utils.DruidUtils;
import com.itheima.utils.LocalDateTimeUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderDao {
    public int addOrder(Order order) throws SQLException {
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        String sql = "INSERT INTO t_order(clothes_details, " + "price, `status`, user_id, address, time)" + "VALUES (?,?,?,?,?,?)";
        String dateTime = LocalDateTimeUtil.formatLocalDateTime(LocalDateTime.now());
        Object[] params = {order.getClothesDetails(), order.getPrice(),order.getStatus(), order.getUserId(), order.getAddress(), dateTime};
        return qr.update(sql,params);
        }
    public List<Order> getOrderByUser(int userId, int status) throws SQLException{
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        String sql="SELECT id, clothes_details AS clothesDetails, price," + "`status`, user_id AS userId, address, time FROM t_order "+" WHERE user_id = ? AND status = ?";
        Object[] params = {userId,status};
        return qr.query(sql,new BeanListHandler<>(Order.class),params);
    }
    public int delOrderData(int id) throws SQLException{
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        String sql = "DELETE FROM t_order WHERE id=? AND " + " `status` NOT IN(1, 2)";
        return qr.update(sql,id);
    }
    public int updateOrderStatus(int id,int initStatus,int finalStatus) throws SQLException{
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        String sql = "UPDATE t_order SET status = ? WHERE status=? " + " AND id = ?";
        Object[] params={finalStatus,initStatus,id};
        return qr.update(sql,params);
    }
    public List<Order>getOrderByParams(String userName,Integer status) throws SQLException{
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        StringBuilder sql = new StringBuilder().append("SELECT t_order.id,clothes_details AS clothesDetails,"+"price,`status`,user_id AS userId,user_name,time"+" FROM t_order JOIN t_user ON user_id=t_user.id");
        List<Object>params = new ArrayList<>();
        if(userName!=null&&!userName.isEmpty()){
            sql.append(" AND user_name LIKE ?");
            params.add("%"+userName+"%");
        }
        if(status!=null){
            sql.append(" AND status=?");
            params.add(status);
        }
        return qr.query(sql.toString(),new BeanListHandler<>(Order.class),params.toArray());
    }
}