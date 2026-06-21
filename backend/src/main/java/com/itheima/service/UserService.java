package com.itheima.service;
import com.itheima.dao.UserDao;
import com.itheima.model.User;
import com.itheima.model.UserVo;
import com.itheima.utils.TokenUtils;
import java.sql.SQLException;
import java.util.List;

public class UserService {
    UserDao userDao = new UserDao();

    public User findUserByUserName(String userName) {
        try {
            return userDao.isUserNameExist(userName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String register(User user){
        try {
            if (userDao.isUserNameExist(user.getUserName()) != null) {
                return "用户名已存在，请更换！";
            }
            if (userDao.isPhoneExist(user.getPhone()) != null) {
                return "手机号已存在，请确认！";
            }
            user.setRole(2);
            userDao.addUser(user);
            return "注册成功，请登录！";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "注册失败！";
    }
    public User login(String userInfo,String password) {
        User user = null;
        try {
            user = userDao.login(userInfo,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (user != null) {
            String token = TokenUtils.generateToken(user.getId(),
                    user.getPassword());
            user.setToken(token);
        }
        return user;
    }
    public User getCurrentUser(int id){
        try {
            return userDao.getUserById(id);
        } catch(Exception e) {
            return null;
        }
    }
    public String updateUser(UserVo userVo){
        try {
            User currentUser=this.getCurrentUser(userVo.getId());
            if(!currentUser.getPassword().equals(userVo.getPassword())){
                return"原始密码输入错误，请重新输入！";
            }
            User userNameExist=userDao.isUserNameExist(userVo.getUserName());
            User phoneExits=userDao.isPhoneExist(userVo.getPhone());
            if(userNameExist !=null && userNameExist.getId() != userVo.getId()){
                return"用户名已存在，请输入其他用户名！";
            }
            if(phoneExits != null && phoneExits.getId() != userVo.getId()){
                return"手机号已存在，请确认！";
            }
            userVo.setPassword(userVo.getNewpsw());
            int updateCount = userDao.updateUser(userVo);
            return updateCount>0?"修改成功！":"修改失败！";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "修改失败！";
    }
    public List<User> getUserByParam(String queryStr){
        List<User>list=null;
        try{
            list=userDao.getUserByParam(queryStr);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }
    public String delUser(int id){
        try{
            int count = userDao.delUser(id);
            return count>0?"删除成功！":"删除失败！";
        } catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
