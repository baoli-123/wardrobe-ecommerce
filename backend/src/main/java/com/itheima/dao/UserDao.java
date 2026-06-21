package com.itheima.dao;

import com.itheima.model.User;
import com.itheima.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

public class UserDao {

    /**
     * 添加用户
     */
    public int addUser(User user) throws SQLException {
        QueryRunner r = new QueryRunner(DruidUtils.getDataSource());
        String sql = "INSERT INTO t_user " +
                "(user_name, password, phone, address, role) VALUES (?, ?, ?, ?, ?)";
        return r.update(sql,
                user.getUserName(),
                user.getPassword(),
                user.getPhone(),
                user.getAddress(),
                user.getRole());
    }

    /**
     * 根据用户名和密码查询用户（登录用）
     */
    public User findUser(String userName, String password) throws SQLException {
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        String sql = "SELECT * FROM t_user WHERE user_name = ? AND password = ?";
        return qr.query(sql, new BeanHandler<>(User.class), userName, password);
    }

    /**
     * 根据用户名查询用户是否存在
     */
    public User isUserNameExist(String userName) throws SQLException {
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        String sql = "SELECT id,user_name AS userName " +
                "FROM t_user WHERE user_name = ?";
        User u = qr.query(sql, new BeanHandler<>(User.class), userName);
        return u;
    }
    /**
     * 根据用户名查询完整用户信息
     */
    public User findUserByName(String userName) throws SQLException {
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        String sql = "SELECT * FROM t_user WHERE user_name = ?";
        return qr.query(sql, new BeanHandler<>(User.class), userName);
    }
    public User isPhoneExist(String phone) throws SQLException{
            QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
            String sql = "SELECT id,phone FROM t_user WHERE phone = ?";
            User u = qr.query(sql,new BeanHandler<>(User.class),phone);
            return u;
    }
    public User login(String info,String password) throws SQLException {
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        String sql = "SELECT id,user_name AS userName, password, phone," +"address,role " + "FROM t_user WHERE user_name = ? AND password = ?";
        Object[] params={info,password};
        User user=qr.query(sql,new BeanHandler<>(User.class),params);
        if(user==null) {
            sql = "SELECT id,user_name AS userName,password,phone," + "address,role " + "FROM t_user WHERE phone = ? AND password = ?";
            user=qr.query(sql,new BeanHandler<>(User.class),params);
        }
        return user;
    }
    public User getUserById(int id) throws SQLException{
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        String sql="SELECT id, user_name AS userName, password, phone, " + "address, role FROM t_user WHERE id = ?";
        User u=qr.query(sql,new BeanHandler<>(User.class),id);
        return u;
    }
    public int updateUser(User user) throws SQLException{
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        String sql="UPDATE t_user SET user_name = ?, password = ?, " + "phone = ?, address = ? WHERE id = ?";
        Object[]params={user.getUserName(),user.getPassword(),user.getPhone(),user.getAddress(),user.getId()};
        return qr.update(sql,params);
    }
    public List<User> getUserByParam(String queryStr) throws SQLException{
        QueryRunner qr=new QueryRunner(DruidUtils.getDataSource());
        StringBuilder sql = new StringBuilder("SELECT id, user_name AS userName, password, phone, " + "address, role FROM t_user WHERE role = 2");
        List<User>list=null;
        if(queryStr!=null&&!queryStr.isEmpty()){
            sql.append(" AND user_name LIKE ?");
            list = qr.query(sql.toString(),new BeanListHandler<>(User.class),"%"+queryStr+"%");
            if(list==null||list.size()==0){
                String replace = sql.toString().replace("AND user_name LIKE ?","");
                replace+="AND phone LIKE ?";
                list=qr.query(replace,new BeanListHandler<>(User.class),"%"+queryStr+"%");
            }
            return list;
        }
        list=qr.query(sql.toString(),new BeanListHandler<>(User.class));
        return list;
    }
    public int delUser(int id) throws SQLException{
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        String sql="DELETE FROM t_user WHERE id = ?";
        return qr.update(sql, id);
    }
 }

