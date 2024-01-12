package com.salvia.data;

import com.salvia.config.MysqlConfig;
import com.salvia.pojo.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.salvia.utils.Md5.encodeMd5;

public class UserTable {
    MysqlConfig mysqlConfig = new MysqlConfig();


    public UserTable(){
    }

    // 新建 User 表
    public void createUserTable() throws SQLException {
        String sql = """
                CREATE TABLE if NOT exists users(
                    id INT PRIMARY KEY AUTO_INCREMENT,
                    name VARCHAR(50) NOT NULL,
                    password VARCHAR(50) NOT NULL,
                    email VARCHAR(50) NOT NULL,
                    tel VARCHAR(20),
                    age INT,
                    sex VARCHAR(10),
                    address VARCHAR(100),
                    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
                );
                """;
        System.out.println(mysqlConfig.statement().executeUpdate(sql) == 0 ? "建表 UserTable 成功" : "建表 UserTable 失败！");
    }

    // 插入 User
    public int insertUser(User user) throws SQLException {
        String sql = "INSERT INTO users (name, password, email, tel, age, sex, address) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = getUpdateSqlStatement(user, sql,false);
        return statement.executeUpdate();
    }

    // 删除 User
    public int deleteUser(User user) throws SQLException {
        String sql = "DELETE FROM users WHERE id=?";
        PreparedStatement statement = mysqlConfig.preparedStatement(sql);
        statement.setInt(1, user.getId());
        return statement.executeUpdate();
    }
    // 更新 User
    public int updateUser(User user) throws SQLException {
        String sql = "UPDATE users SET name=?, password=?, email=?, tel=?, age=?, sex=?, address=? WHERE id=?";
        PreparedStatement statement = getUpdateSqlStatement(user, sql, true);
        statement.setInt(8, user.getId());
        return statement.executeUpdate();
    }
    // 更新 User 的密码
    public int updateUserPassword(User user) throws SQLException {
        String sql = "UPDATE users SET  password=? WHERE id=?";
        PreparedStatement statement = mysqlConfig.preparedStatement(sql);
        statement.setString(1, user.getPassword());
        statement.setInt(2, user.getId());
        return statement.executeUpdate();
    }
    // 根据用户邮箱查询 User
    public User selectUserByEmail(User user) throws SQLException {
        User user1 = null;
        String sql = "SELECT * FROM users WHERE email=?";
        PreparedStatement statement = mysqlConfig.preparedStatement(sql);
        statement.setString(1, user.getEmail());
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            user1 = returnUser(resultSet);
        }
        return user1;
    }
    // 根据用户名查询 User
    public User selectUserByName(User user) throws SQLException {
        User user1 = null;
        String sql = "SELECT * FROM users WHERE name=?";
        PreparedStatement statement = mysqlConfig.preparedStatement(sql);
        statement.setString(1, user.getName());
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            user1 = returnUser(resultSet);
        }
        return user1;
    }

    //根据手机号查询 User
    public User selectUserByTel(User user) throws SQLException {
        User user1 = null;
        String sql = "SELECT * FROM users WHERE tel=?";
        PreparedStatement statement = mysqlConfig.preparedStatement(sql);
        statement.setString(1, user.getTel());
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            user1 = returnUser(resultSet);
        }
        return user1;
    }

    // 根据 用户ID 查询 User
    public User selectUserById(User user) throws SQLException {
        User user1 = null;
        String sql = "SELECT * FROM users WHERE id=?";
        PreparedStatement statement = mysqlConfig.preparedStatement(sql);
        statement.setInt(1, user.getId());
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            user1 = returnUser(resultSet);
        }
        return user1;
    }

    // 查询 所有 User
    public List<User> selectAllUser() throws SQLException {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM users";
        PreparedStatement statement = mysqlConfig.preparedStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            userList.add(returnUser(resultSet));
        }
        return userList;
    }



    // 从查询语句返回的 ResultSet 获取 user 对象
    public User returnUser(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("password"),
                resultSet.getString("email"),
                resultSet.getString("tel"),
                resultSet.getInt("age"),
                resultSet.getString("sex"),
                resultSet.getString("address"),
                resultSet.getTimestamp("create_time"),
                resultSet.getTimestamp("update_time")
        );
    }


    // 根据 User 表来匹配预编译 getUpdateSqlStatement; type => true 表示为更新， false 表示为 插入
    private PreparedStatement getUpdateSqlStatement(User user, String sql, boolean type) throws SQLException {
        String password = type ? user.getPassword() : encodeMd5(user.getPassword());
        PreparedStatement statement = mysqlConfig.preparedStatement(sql);
        statement.setString(1, user.getName());
        statement.setString(2, password);
        statement.setString(3, user.getEmail());
        statement.setString(4, user.getTel());
        statement.setInt(5, user.getAge());
        statement.setString(6, user.getSex());
        statement.setString(7, user.getAddress());
        return statement;
    }

}
