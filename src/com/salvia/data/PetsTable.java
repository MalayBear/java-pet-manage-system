package com.salvia.data;

import com.salvia.config.MysqlConfig;
import com.salvia.pojo.Pet;
import com.salvia.pojo.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PetsTable {
    MysqlConfig mysqlConfig = new MysqlConfig();
    public PetsTable(){

    }
    //新建一个宠物表
    public void createPetsTable() throws SQLException {
       String sql = """
                 CREATE TABLE if NOT exists pets (
                    id INT PRIMARY KEY AUTO_INCREMENT,
                    name VARCHAR(50) NOT NULL COMMENT '宠物名字',
                    species VARCHAR(50) NOT NULL COMMENT '宠物种类',
                    age INT COMMENT '宠物年龄',
                    gender VARCHAR(10) COMMENT '宠物性别',
                    personality VARCHAR(50) COMMENT '宠物性格',
                    weight DECIMAL(5,2) COMMENT '宠物体重',
                    owner_id INT COMMENT '宠物主人ID'
                ) COMMENT '宠物表';
                                
                """;
        System.out.println(mysqlConfig.statement().executeUpdate(sql) == 0 ? "建表PetsTable成功" : "建表PetsTable失败");
    }

    //增加 Pet
    public  int insertPet (Pet pet) throws SQLException{
        String sql ="INSERT INTO pets(name, species, age, gender, personality, weight, owner_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = getUpdateSqlStatement(pet, sql);
        return statement.executeUpdate();
    }
//删除 pet
    public int deletePet(Pet pet) throws SQLException{
        String sql = "DELETE FROM pets WHERE id=?";
        PreparedStatement statement = mysqlConfig.preparedStatement(sql);
        statement.setInt(1,pet.getId());
        return statement.executeUpdate();
    }


    //更新pet
    public  int updatePet(Pet pet) throws SQLException{
        String sql ="UPDATE pets SET name=?, species=?, age=?, gender=?, personality=?, weight=?, owner_id=? where id=?";
        PreparedStatement statement = getUpdateSqlStatementInUpdate(pet,sql);
        return statement.executeUpdate();
    }


    //用户查询pet的姓名
    public Pet selectPetByName(Pet pet) throws SQLException{
        Pet pet1 = new Pet();
        String sql ="SELECT * FROM pets WHERE name=?";
        PreparedStatement statement = mysqlConfig.preparedStatement(sql);
        statement.setString(1, pet.getName());
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            pet1 = returnPet(resultSet);
        }
        return pet1;

    }
    //根据 id 查询pet
    public Pet selectById(Pet pet) throws SQLException{
        Pet pet1 = new Pet();
        String sql = "SELECT * FROM pets WHERE id=?";
        PreparedStatement statement = mysqlConfig.preparedStatement(sql);
        statement.setInt(1,pet.getId());
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            pet1 = returnPet(resultSet);
        }
        return pet1;
    }

    // 根据 User 用户的 Id 查询宠物信息
    public List<Pet> selectUsersPet(User user) throws SQLException {
        List<Pet> petList = new ArrayList<>();
        String sql = "SELECT * FROM pets WHERE owner_id=?";
        PreparedStatement statement = mysqlConfig.preparedStatement(sql);
        statement.setInt(1,user.getId());
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            petList.add(returnPet(resultSet));
        }
        return petList;
    }
    //查询所有的pets
    public List<Pet> selectAllExistPets() throws SQLException {
        List<Pet> petList = new ArrayList<>();
        String sql = "SELECT * FROM pets";
        PreparedStatement statement = mysqlConfig.preparedStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        if (!resultSet.next()){
            return new ArrayList<>();
        }
        while (resultSet.next()){
            petList.add(returnPet(resultSet));
        }
        return petList;
    }
    //查询所有的pets
    public List<Pet> selectAllPets() throws SQLException {
        List<Pet> petList = new ArrayList<>();
        String sql = "SELECT * FROM pets";
        PreparedStatement statement = mysqlConfig.preparedStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            petList.add(returnPet(resultSet));
        }
        return petList;
    }

    // 对 Sql 语句中的 实体对应的信息进行 预编译，防止 SQL注入
    // 将对应位置的 ? 代替为 值
    // "SELECT * FROM pets WHERE owner_id=?"; statement.setString(1, pet.getName());
    // 将第一个 ? 的值替换为安全的值  pet.getName()
    private PreparedStatement getUpdateSqlStatement(Pet pet, String sql) throws SQLException {
        PreparedStatement statement = mysqlConfig.preparedStatement(sql);
        statement.setString(1, pet.getName());
        statement.setString(2, pet.getSpecies());
        statement.setInt(3, pet.getAge());
        statement.setString(4, pet.getGender());
        statement.setString(5, pet.getPersonality());
        statement.setDouble(6, pet.getWeight());
        statement.setInt(7, pet.getOwnerId());
        return statement;
    }

    //用于更新语句
    private PreparedStatement getUpdateSqlStatementInUpdate(Pet pet, String sql) throws SQLException {
        PreparedStatement statement = mysqlConfig.preparedStatement(sql);
        statement.setString(1, pet.getName());
        statement.setString(2, pet.getSpecies());
        statement.setInt(3, pet.getAge());
        statement.setString(4, pet.getGender());
        statement.setString(5, pet.getPersonality());
        statement.setDouble(6, pet.getWeight());
        statement.setInt(7, pet.getOwnerId());
        statement.setInt(8,pet.getId());
        return statement;
    }
    // ResultSet 数据库查询的信息（查询的结果是实体对象 Pet），将 查询的对应信息 赋值给 实体对应的属性
    public Pet returnPet(ResultSet resultSet) throws SQLException {
        return new Pet(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("species"),
                resultSet.getInt("age"),
                resultSet.getString("gender"),
                resultSet.getString("personality"),
                resultSet.getDouble("weight"),
                resultSet.getInt("owner_id")
        );
    }


}
