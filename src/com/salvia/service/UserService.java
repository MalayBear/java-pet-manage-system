package com.salvia.service;

import com.salvia.data.UserTable;
import com.salvia.pojo.User;

import java.sql.SQLException;

import static com.salvia.utils.Md5.encodeMd5;
import static com.salvia.utils.Print.printUtil;

public class UserService {
    static UserTable userTable = new UserTable();
    User returnUser = new User();
    boolean bool = false;
    // 查询用户 校验用户名（邮箱）和密码， 返回 User 或 null
    public User isUser(User user) throws SQLException {
        if (user.getName() != null){
            if (isUserExistByName(user)) {
               return isUserPasswordValidatorByName(user);
            }
        }else {
            if (isUserExistByEmail(user)) {
                return isUserPasswordValidatorByEmail(user);
            }
        }
        return null;
    }


    // 查询用户是否存在，存在返回 true,不存在返回 false
    private boolean isUserExistByEmail(User user) throws SQLException {
        bool = userTable.selectUserByEmail(user) != null;
        System.out.println(bool ? "" : "用户不存在！");
        return bool;
    }
    // 查询用户是否存在，存在返回 true,不存在返回 false
    private boolean isUserExistByName(User user) throws SQLException {
        bool = userTable.selectUserByName(user) != null;
        System.out.println(bool ? "" : "用户不存在！");
        return bool;
    }

    //通过 用户名 邮箱 手机号判断用户是否存在 ,返回值 true 为存在, false 为不存在
    public static boolean isUserExistByNameOrEmailOrTel(User user) throws SQLException {
//        if (userTable.selectUserByEmail(user) != null ||userTable.selectUserByName(user) != null ||
//        userTable.selectUserByTel(user) != null){
//            System.out.println("用户已存在，注册失败，请返回登录！");
//          return false;
//        }
//             return true;
        return userTable.selectUserByEmail(user) != null || userTable.selectUserByName(user) != null ||
                userTable.selectUserByTel(user) != null;
    }



    // 查询用户密码是否正确
    private User isUserPasswordValidatorByEmail(User user) throws SQLException {
        returnUser = userTable.selectUserByEmail(user);
        bool = returnUser.getPassword().equalsIgnoreCase(encodeMd5(user.getPassword()));
        System.out.println(bool ? "" : "用户密码错误！");
        return bool ? returnUser:null;
    }
    // 查询用户密码是否正确
    private User isUserPasswordValidatorByName(User user) throws SQLException {
        returnUser = userTable.selectUserByName(user);
        bool = returnUser.getPassword().equalsIgnoreCase(encodeMd5(user.getPassword()));
        System.out.println(bool ? "" : "用户密码错误！");
        return bool ? returnUser:null;
    }

    //显示个人信息
    public static void printUserInfo(User user) throws SQLException {
        printUtil("ID,姓名,邮箱,电话,年龄,性别,地址,创建时间,更新时间");
        userTable.updateUser(user);
        printUtil(user.toString());

    }




}
