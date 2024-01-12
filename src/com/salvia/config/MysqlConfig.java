package com.salvia.config;

import java.sql.*;

// 处理查询结果
//                while (resultSet.next()) {
//                    int id = resultSet.getInt("id");
//                    String name = resultSet.getString("name");
//                    String email = resultSet.getString("email");
//                    System.out.println("ID: " + id + ", Name: " + name + ", Email: " + email);
//                }
// 关闭连接和资源
//                resultSet.close();
//                statement.close();
//                connection.close();
//                // 在这里可以执行数据库操作
//                // ...
//                connection.close();

public class MysqlConfig {
    // 创建 数据库的连接
    public Connection connection() {
         //MySQL数据库连接信息
//        String url = "jdbc:mysql://127.0.0.1:3306/salvia";
//        String username = "root";
//        String password = "123456";
        String url = "jdbc:mysql://47.107.69.222:3306/salvia";
        String username = "salvia";
        String password = "yuntiansalvia";
        // 连接MySQL数据库
        try {
            Connection connection = DriverManager.getConnection(url, username, password);

            return connection;
        } catch (Exception e) {
            throw new RuntimeException("数据库连接失败!\n" + e);
        }
    }

    // 创建 Statement 对象
    public Statement statement() throws SQLException {
        return connection().createStatement();
    }

    // 创建 PreparedStatement 对象(防止 sql 注入)  对sql语句预编译
    public PreparedStatement preparedStatement(String sql) throws SQLException {
        try {
            return connection().prepareStatement(sql);
        } catch (Exception e) {
            throw new RuntimeException("SQL 语句有错误!\n" + e);
        }
    }
}
