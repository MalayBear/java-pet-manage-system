package com.salvia.data;

import com.salvia.config.MysqlConfig;
import com.salvia.pojo.Manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.salvia.utils.Md5.encodeMd5;

public class ManagerTable {
    static MysqlConfig mysqlConfig = new MysqlConfig();


    public ManagerTable(){
    }



    // 新建 Manage 表
    public void createManagerTable() throws SQLException {
        String sql = """
                CREATE TABLE if NOT exists managers(
                    id INT PRIMARY KEY AUTO_INCREMENT,
                    name VARCHAR(50) NOT NULL,
                    password VARCHAR(50) NOT NULL,
                    email VARCHAR(50) NOT NULL,
                    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
                );
                """;
        System.out.println(mysqlConfig.statement().executeUpdate(sql) == 0 ? "建表 ManagerTable 成功" : "建表 ManagerTable 失败！");
    }

    // 插入 Manager
    public int insertManager(Manager manager) throws SQLException {
        String sql = "INSERT INTO managers (name, password, email) VALUES (?, ?, ?)";
        PreparedStatement statement = getUpdateSqlStatement(manager, sql,false);
        return statement.executeUpdate();
    }

    // 删除 Manager
    public static int deleteManager(Manager manager) throws SQLException {
        String sql = "DELETE FROM managers WHERE id=?";
        PreparedStatement statement = mysqlConfig.preparedStatement(sql);
        statement.setInt(1, manager.getId());
        return statement.executeUpdate();
    }
    // 更新 Manager
    public int updateManager(Manager manager) throws SQLException {
        String sql = "UPDATE managers SET name=?,password=?,email=? WHERE id=?";
        PreparedStatement statement = getUpdateSqlStatement(manager, sql,true);
        statement.setInt(4, manager.getId());
        return statement.executeUpdate();
    }
    //更新密码
    public static int updateManagerPassword(Manager manager) throws SQLException {
        String sql = "UPDATE managers SET password=? WHERE id=?";
        PreparedStatement statement = mysqlConfig.preparedStatement(sql);
        statement.setString(1, manager.getPassword());
        statement.setInt(2,manager.getId());
        return statement.executeUpdate();
    }

    // 根据邮箱查询 Manager
    public static Manager selectManagerByEmail(Manager manager) throws SQLException {
        Manager manager1 = null;
        String sql = "SELECT * FROM managers WHERE email=?";
        PreparedStatement statement = mysqlConfig.preparedStatement(sql);
        statement.setString(1, manager.getEmail());
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            manager1 = returnManager(resultSet);
        }
        return manager1;
    }
    // 根据姓名查询 Manager
    public static Manager selectManagerByName(Manager manager) throws SQLException {
        Manager manager1 = null;
        String sql = "SELECT * FROM managers WHERE name=?";
        PreparedStatement statement = mysqlConfig.preparedStatement(sql);
        statement.setString(1, manager.getName());
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            manager1 = returnManager(resultSet);
        }
        return manager1;
    }
    // 根据 ID 查询 Manager
    public Manager selectManagerById(Manager manager) throws SQLException {
        Manager manager1 = null;
        String sql = "SELECT * FROM managers WHERE id=?";
        PreparedStatement statement = mysqlConfig.preparedStatement(sql);
        statement.setInt(1, manager.getId());
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            manager1 = returnManager(resultSet);
        }
        return manager1;
    }
    // 查询 所有 Manager
    public List<Manager> selectAllManager() throws SQLException {
        List<Manager> managerList = new ArrayList<>();
        String sql = "SELECT * FROM managers";
        PreparedStatement statement = mysqlConfig.preparedStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            managerList.add(returnManager(resultSet));
        }
        return managerList;
    }



    // 从查询语句返回的 ResultSet 获取 manage 对象
    public static Manager returnManager(ResultSet resultSet) throws SQLException {
        return new Manager(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("password"),
                resultSet.getString("email"),
                resultSet.getTimestamp("create_time"),
                resultSet.getTimestamp("update_time")
        );
    }


    // 根据 Manage 表来匹配预编译 getUpdateSqlStatement
    private static PreparedStatement getUpdateSqlStatement(Manager manager, String sql, boolean type) throws SQLException {
        String password = type ? manager.getPassword() : encodeMd5(manager.getPassword());
        PreparedStatement statement = mysqlConfig.preparedStatement(sql);
        statement.setString(1, manager.getName());
        statement.setString(2, password);
        statement.setString(3, manager.getEmail());
        return statement;
    }

}

