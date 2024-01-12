package com.salvia.data;

import com.salvia.config.MysqlConfig;
import com.salvia.pojo.PetSupply;
import com.salvia.pojo.SuppliesType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PetSuppliesTable {
    static MysqlConfig mysqlConfig = new MysqlConfig();
    static SuppliesTypeTable suppliesTypeTable = new SuppliesTypeTable();
    static SuppliesType suppliesType = new SuppliesType();
    public PetSuppliesTable(){}
    //建立一个商品表
    public static void createPetSuppliesTable() throws SQLException {
        String sql = """
               CREATE TABLE if NOT exists petSupplies (
                id INT PRIMARY KEY AUTO_INCREMENT,
                user_id INT COMMENT '用户ID',
                petSuppliesType_id INT COMMENT '宠物用品ID',
                name VARCHAR(100) COMMENT '宠物用品名称',
                num INT COMMENT '购买数量',
                status INT COMMENT '支付状态（1表示已支付，0表示待支付）',
                create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ,
                update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
              );
            """;
        System.out.println(mysqlConfig.statement().executeUpdate(sql) == 0 ? "PetSuppliesTable表 建表成功" : "PetSuppliesTable表 建表失败");

    }
    //新增购物车商品的信息
    //将 petSuppliesType_id,id,name，添加到petSupplies
    public static int insertPetSupply(PetSupply petSupply) throws SQLException {
        String sql = """
                INSERT INTO petSupplies (user_id,petSuppliesType_id,name,num, status) VALUE (?,?,?,?,0)
                """;
        PreparedStatement statement =getUpdateSqlStatement(petSupply,sql);
        return statement.executeUpdate();
    }

    //删除购物车商品信息,根据商品id 删除信息
    public  int deletePetSupply(PetSupply petSupply) throws SQLException {
        String sql = "DELETE FROM petSupplies WHERE PetSuppliesType_id=? AND status=0";
        PreparedStatement statement = mysqlConfig.preparedStatement(sql);
        statement.setInt(1, petSupply.getPetSuppliesType_id());
        return statement.executeUpdate();
    }
//删除购物车商品信息,根据id 删除信息
public  int deletePetSupplyById(PetSupply petSupply) throws SQLException {
    String sql = "DELETE FROM petSupplies WHERE id=? AND status=0";
    PreparedStatement statement = mysqlConfig.preparedStatement(sql);
    statement.setInt(1, petSupply.getId());
    return statement.executeUpdate();
}

    //更新购物车信息
    public int updatePetSupply(PetSupply petSupply) throws SQLException {
        String sql = "UPDATE petSupplies SET user_id=? ,petSuppliesType_id=?,name=?,num=?";
        PreparedStatement statement = getUpdateSqlStatement(petSupply,sql);
        return statement.executeUpdate();
    }
    public static int updatePetSupplyByStatus(PetSupply petSupply) throws SQLException {
        String sql = "UPDATE petSupplies SET status=1 where id=? ";
        PreparedStatement statement = mysqlConfig.preparedStatement(sql);
        statement.setInt(1,petSupply.getId());
        return statement.executeUpdate();
    }

    //已经支付的商品信息
    public static List<PetSupply> selectAllPetSupplyByStatusIsPay() throws SQLException {
        List<PetSupply> petSupplyList = new ArrayList<>();
        String sql = "SELECT * FROM petSupplies WHERE status=1";
        PreparedStatement statement = mysqlConfig.preparedStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next()){
            petSupplyList.add(returnPetSupply(resultSet));
        }
        return  petSupplyList;

    }


    //用户查询，已支付商品信息
    public static List<PetSupply> userSelectAllPetSupplyByStatusIsPay(PetSupply petSupply) throws SQLException {
        List<PetSupply> petSupplyList = new ArrayList<>();
        String sql = "SELECT * FROM petSupplies WHERE status=1 AND user_id=?";
        PreparedStatement statement = mysqlConfig.preparedStatement(sql);
        statement.setInt(1,petSupply.getUser_id());
        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next()){
            petSupplyList.add(returnPetSupply(resultSet));
        }
        return  petSupplyList;

    }
    //管理员 查看所有用户商品信息
    public static List<PetSupply> ManagerSelectAllUsersPetSupply(PetSupply petSupply) throws SQLException {
        List<PetSupply> petSupplyList = new ArrayList<>();
        String sql = "SELECT * FROM petSupplies ";
        PreparedStatement statement = getUpdateSqlStatement(petSupply,sql);
        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next()){
            petSupplyList.add(returnPetSupply(resultSet));
        }
        return  petSupplyList;

    }
    //管理员 查询，已支付商品信息
    public static List<PetSupply> ManagerSelectAllPetSupplyByStatusIsPay(PetSupply petSupply) throws SQLException {
        List<PetSupply> petSupplyList = new ArrayList<>();
        String sql = "SELECT * FROM petSupplies WHERE status=1 AND user_id=?";
        PreparedStatement statement = mysqlConfig.preparedStatement(sql);
        statement.setInt(1,petSupply.getUser_id());
        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next()){
            petSupplyList.add(returnPetSupply(resultSet));
        }
        return  petSupplyList;

    }
    //用户id查询已经加入购物车商品信息
    public static List<PetSupply> userSelectAllPetSupplyByStatus(PetSupply petSupply) throws SQLException {
        List<PetSupply> petSupplyList = new ArrayList<>();
        String sql = "SELECT * FROM petSupplies WHERE status=0 AND user_id=?";
        PreparedStatement statement = mysqlConfig.preparedStatement(sql);
        statement.setInt(1,petSupply.getUser_id());
        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next()){
            petSupplyList.add(returnPetSupply(resultSet));
        }
        return  petSupplyList;

    }
    //用户根据商品id 查询已经加入购物车商品信息
    public static PetSupply userSelectPetSupplyByStatus(PetSupply petSupply) throws SQLException {
        String sql = "SELECT * FROM petSupplies WHERE status=0 AND user_id=? AND id=?";
        PreparedStatement statement = mysqlConfig.preparedStatement(sql);
        statement.setInt(1,petSupply.getUser_id());
        statement.setInt(2,petSupply.getId());
        ResultSet resultSet = statement.executeQuery();
        if (!resultSet.next()){
            return null;
        }
        while(resultSet.next()){
           petSupply = returnPetSupply(resultSet);
        }
        return  petSupply;

    }
    //管理员通过用户id 查询未支付商品信息
    public static List<PetSupply> managerSelectAllPetSupplyByStatus(PetSupply petSupply) throws SQLException {
        List<PetSupply> petSupplyList = new ArrayList<>();
        String sql = "SELECT * FROM petSupplies WHERE status=0 AND user_id=?";
        PreparedStatement statement = mysqlConfig.preparedStatement(sql);
        statement.setInt(1,petSupply.getUser_id());
        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next()){
            petSupplyList.add(returnPetSupply(resultSet));
        }
        return  petSupplyList;

    }
    //商品本身id查询已经加入购物车商品信息
    public static List<PetSupply> userSelectAllPetSupplyById(PetSupply petSupply) throws SQLException {
        List<PetSupply> petSupplyList = new ArrayList<>();
        String sql = "SELECT * FROM petSupplies WHERE status=0 AND id=?";
        PreparedStatement statement = mysqlConfig.preparedStatement(sql);
        statement.setInt(1,petSupply.getId());
        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next()){
            petSupplyList.add(returnPetSupply(resultSet));
        }
        return  petSupplyList;

    }



    //用户根据商品名称查询要购买商品信息
    public PetSupply selectPetSupplyByName(PetSupply petSupply) throws SQLException {
        // 根据名字获取 SuppliesType 的 信息（主要是 ID 即 petSuppliesType_id）
        SuppliesType suppliesType = new SuppliesType();
        suppliesType.setName(petSupply.getName());
        suppliesType = suppliesTypeTable.selectSuppliesTypeByName(suppliesType);
        petSupply.setSuppliesType(suppliesType); //主要是 设置 petSuppliesType_id
        return selectPetSupplyByPetSuppliesTypeId(petSupply);
    }
    public PetSupply selectPetSupplyByPetSupplyId(PetSupply petSupply) throws SQLException {
        // 根据名字获取 SuppliesType 的 信息（主要是 ID 即 petSuppliesType_id）
        SuppliesType suppliesType = new SuppliesType();
        suppliesType.setId(petSupply.getPetSuppliesType_id());
        suppliesType = suppliesTypeTable.selectSuppliesTypeExistById(suppliesType);
        petSupply.setSuppliesType(suppliesType); //主要是 设置 petSuppliesType_id
        return selectPetSupplyByPetSuppliesTypeName(petSupply);
    }
    //用户根据商品id查询要购买商品信息
    public PetSupply UserSelectPetSupplyExistByPetSuppliesTypeId(PetSupply petSupply) throws SQLException {
        SuppliesType suppliesType1 = new SuppliesType();
        suppliesType1.setId(petSupply.getPetSuppliesType_id());
        suppliesType1 = suppliesTypeTable.selectSuppliesTypeExistById(suppliesType1);
        if (suppliesType1 == null){
            return null;
        }
        petSupply.setSuppliesType(suppliesType1);
        return petSupply;
    }
    public PetSupply UserSelectPetSupplyByPetSuppliesTypeId(PetSupply petSupply) throws SQLException {
        SuppliesType suppliesType1 = new SuppliesType();
        suppliesType1.setId(petSupply.getPetSuppliesType_id());
        suppliesType1 = suppliesTypeTable.selectSuppliesTypeById(suppliesType1);
        if (suppliesType1 == null){
            return null;
        }
        petSupply.setSuppliesType(suppliesType1);
        return petSupply;
    }

    //用户根据商品名称查询
    public PetSupply UserSelectPetSupplyByPetSuppliesTypeName(PetSupply petSupply) throws SQLException {
        SuppliesType suppliesType1 = new SuppliesType();
        suppliesType1.setName(petSupply.getName());
        suppliesType1 = suppliesTypeTable.selectSuppliesTypeByName(suppliesType1);
//        String sql = "SELECT * FROM petSuppliesType WHERE name=?";
//        PreparedStatement statement = mysqlConfig.preparedStatement(sql);
//        statement.setString(1, petSupply.getName());
//        ResultSet resultSet = statement.executeQuery();
//        while (resultSet.next()){
//            petSupply = returnPetSupply(resultSet);
//        }
        if (suppliesType1 == null)
            return null;
        petSupply.setSuppliesType(suppliesType1);
        return petSupply;
    }

    //查询所有商品
    public static List<PetSupply> selectAllPetSuppliesPay() throws SQLException {
        List<PetSupply> petSupplyList = new ArrayList<>();
        String sql = "SELECT * FROM petSupplies";
        PreparedStatement statement = mysqlConfig.preparedStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            petSupplyList.add( returnPetSupply(resultSet));
        }
        return petSupplyList;
    }
    //管理员通过 petSuppliesType_id 查询 PetSupply 商品信息
    public PetSupply selectPetSupplyByPetSuppliesTypeId(PetSupply petSupply) throws SQLException {
        String sql = "SELECT * FROM petSupplies WHERE petSuppliesType_id=?";
        PreparedStatement statement = mysqlConfig.preparedStatement(sql);
        statement.setInt(1,petSupply.getPetSuppliesType_id());
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            petSupply = returnPetSupply(resultSet);
        }
        return petSupply;
    }
    //管理员通过 petSuppliesType_id 查询 PetSupply 商品信息
    public PetSupply selectPetSupplyByPetSuppliesTypeName(PetSupply petSupply) throws SQLException {
        String sql = "SELECT * FROM petSupplies WHERE name=?";
        PreparedStatement statement = mysqlConfig.preparedStatement(sql);
        statement.setString(1,petSupply.getName());
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            petSupply = returnPetSupply(resultSet);
        }
        return petSupply;
    }

    //管理员通过id查询商品信息
    public PetSupply selectPetSupplyById(PetSupply petSupply) throws SQLException {
        String sql = "SELECT * FROM petSupplies WHERE id=?";
        PreparedStatement statement = mysqlConfig.preparedStatement(sql);
        statement.setInt(1, petSupply.getId());
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            petSupply = returnPetSupply(resultSet);
        }
        return petSupply;
    }
    //通过 用户Id 查询商品信息
    public PetSupply selectPetSupplyByUser_Id(PetSupply petSupply) throws SQLException {
        String sql = "SELECT * FROM petSupplies WHERE user_id=?";
        PreparedStatement statement = mysqlConfig.preparedStatement(sql);
        statement.setInt(1, petSupply.getUser_id());
        ResultSet resultSet = statement.executeQuery();
        if (!resultSet.next())
            return null;
        while (resultSet.next()){
                petSupply = returnPetSupply(resultSet);
            }
            return petSupply;


    }





    //预编译
    public static PreparedStatement getUpdateSqlStatement(PetSupply petSupply, String sql) throws SQLException {
        PreparedStatement statement = mysqlConfig.preparedStatement(sql);
        statement.setInt(1, petSupply.getUser_id());
        statement.setInt(2,petSupply.getPetSuppliesType_id());
        statement.setString(3,petSupply.getName());
        statement.setInt(4,petSupply.getNum());
        return statement;

    }
    //返回给实体
   public static PetSupply returnPetSupply(ResultSet resultSet) throws SQLException {
        return new PetSupply(
                resultSet.getInt("id"),
                resultSet.getInt("user_id"),
                resultSet.getInt("petSuppliesType_id"),
                resultSet.getString("name"),
                resultSet.getInt("num"),
                resultSet.getInt("status"),
                resultSet.getTimestamp("create_time"),
                resultSet.getTimestamp("update_time")

        );
   }


}
