  package com.salvia.data;

  import com.salvia.config.MysqlConfig;
  import com.salvia.pojo.SuppliesType;

  import java.sql.PreparedStatement;
  import java.sql.ResultSet;
  import java.sql.SQLException;
  import java.util.ArrayList;
  import java.util.List;

  public class SuppliesTypeTable {
      static MysqlConfig mysqlConfig = new MysqlConfig();
      public SuppliesTypeTable(){
      }
      public static void createSuppliesTypeTable() throws SQLException {
         String sqls = """
              drop table if exists petSuppliesType;
               CREATE TABLE if NOT exists petSuppliesType (
                id INT PRIMARY KEY AUTO_INCREMENT ,
                name VARCHAR(100) COMMENT '宠物用品名称',
                category VARCHAR(50) COMMENT '宠物用品类别',
                price DOUBLE COMMENT '宠物用品价格',
                description TEXT COMMENT '宠物用品描述',
                image_url VARCHAR(200) COMMENT '宠物用品图片链接'
              );
              INSERT INTO petSuppliesType (name, category, price, description, image_url)
              VALUES ('狗零食', '狗用品', 14.99, '美味的狗零食，适合奖励训练', 'src/com/salvia/imgs/goulinshi.jpg'),
                     ('鸟飞行笼', '鸟类用品', 69.99, '宽敞的鸟飞行笼，适合训练鸟类', 'src/com/salvia/imgs/niaolong.jpg'),
                     ('狗洗澡池', '狗用品', 79.99, '方便洗澡的狗洗澡池，适合大型犬', 'src/com/salvia/imgs/gouxizaochi.png'),
                     ('鱼饲料', '水族用品', 9.99, '营养丰富的鱼饲料，适合各种鱼类', 'src/com/salvia/imgs/yusiliao.jpg'),
                     ('兔子食盆', '兔子用品', 9.99, '耐用的兔子食盆，适合养兔子', 'src/com/salvia/imgs/tuzishipen.jpg'),
                     ('猫玩具球', '猫用品', 4.99, '有趣的猫玩具球，适合猫咪玩耍', 'src/com/salvia/imgs/maowangjuqiu.jpg'),
                     ('狗床', '狗用品', 59.99, '舒适的狗床，适合中型', 'src/com/salvia/imgs/gouchaung.webp')
              
              """;
        String[] strings = sqls.split(";");
          for (String string : strings) {
              try {
                  mysqlConfig.statement().executeUpdate(string);
                  System.out.println("SQL 语句执行成功！");
              }catch (Exception e){
                  System.out.println("SQL 语句执行失败！"+e);
              }
          }

    }
      //新增要购买商品的信息
      //将 petSuppliesType,id,name，添加到petSupplies
      public static int insertSuppliesType(SuppliesType suppliesType) throws SQLException {
          String sql = "INSERT INTO petSuppliesType (name,category,price,description,image_url) VALUE (?,?,?,?,?)";
          PreparedStatement statement = getUpdateSqlStatement(suppliesType,sql);
          return statement.executeUpdate();
      }

      //管理员删除要购买商品信息,根据id 删除信息
      public  int managerDeleteSuppliesType(SuppliesType SuppliesType) throws SQLException {
          String sql = "DELETE FROM petSuppliesType WHERE id = ?";
          PreparedStatement statement = mysqlConfig.preparedStatement(sql);
          statement.setInt(1,SuppliesType.getId());
          return statement.executeUpdate();
      }

      //更新未支付商品信息
      public int updateSuppliesType(SuppliesType SuppliesType ) throws SQLException {
          String sql = "UPDATE petSuppliesType SET name=?,category=?,price=?,description=?,image_url=? WHERE id=?";
          PreparedStatement statement = ManagerGetUpdateSqlStatement(SuppliesType,sql);
          return statement.executeUpdate();
      }

      //用户根据商品名称查询要购买商品信息
      public  SuppliesType selectSuppliesTypeByName(SuppliesType suppliesType) throws SQLException {
          String sql = "SELECT * FROM petSuppliesType WHERE name=?";
          PreparedStatement statement = mysqlConfig.preparedStatement(sql);
          statement.setString(1,suppliesType.getName());
          ResultSet resultSet = statement.executeQuery();
          if (!resultSet.next()){ // 指针 已经往下移了 一位
              // 如果不存在，直接返回 null
              return null;
          }else {
              // 因为查询到的结果只有一位或者没有，这里直接给值即可
              return returnSuppliesType(resultSet);
          }
      }
      //管理员根据商品名称查询要购买商品信息
      public  SuppliesType managerSelectSuppliesTypeByName(SuppliesType suppliesType) throws SQLException {
          SuppliesType suppliesType1 = new SuppliesType();
          String sql = "SELECT * FROM petSuppliesType WHERE name=?";
          PreparedStatement statement = mysqlConfig.preparedStatement(sql);
          statement.setString(1,suppliesType.getName());
          ResultSet resultSet = statement.executeQuery();
          while (resultSet.next()){
             suppliesType1 = returnSuppliesType(resultSet);
          }
          return suppliesType1;
      }

      //查询购买商品
      public List<SuppliesType> selectAllPetSupplies() throws SQLException {
          List<SuppliesType> SuppliesTypeList = new ArrayList<>();
          String sql = "SELECT * FROM petSuppliesType";
          PreparedStatement statement = mysqlConfig.preparedStatement(sql);
          ResultSet resultSet = statement.executeQuery();
          if (!resultSet.next()){
              return new ArrayList<>();
          }
          while (resultSet.next()){
              SuppliesTypeList.add(returnSuppliesType(resultSet));
          }
          return SuppliesTypeList;
      }
      //查询购买商品
      public List<SuppliesType> selectAllPetSuppliesTest() throws SQLException {
          List<SuppliesType> SuppliesTypeList = new ArrayList<>();
          String sql = "SELECT * FROM petSuppliesType";
          PreparedStatement statement = mysqlConfig.preparedStatement(sql);
          ResultSet resultSet = statement.executeQuery();
          while (resultSet.next()){
              SuppliesTypeList.add(returnSuppliesType(resultSet));
          }
          return SuppliesTypeList;
      }


      //用户通过SuppliesType id 查询商品信息,判断是否为null
      //如果查询结果集中有多条记录，只会返回最后一条记录
      public SuppliesType selectSuppliesTypeExistById(SuppliesType suppliesType) throws SQLException {
          String sql = "SELECT * FROM petSuppliesType WHERE id=?";
          PreparedStatement statement = mysqlConfig.preparedStatement(sql);
          statement.setInt(1,suppliesType.getId());
          ResultSet resultSet = statement.executeQuery();
          //如果查询结果有多条记录，只会返回最后一条记录。
          //判断结果集中是否有数据,查询结果不为空，返回true
          if (!resultSet.next()){
              return null;
          }
              while (resultSet.next()){
              suppliesType = returnSuppliesType(resultSet);

          }
          return  suppliesType;
      }
      //用户通过SuppliesType id 查询商品信息
      //会返回查询结果集中的第一条记录
      public SuppliesType selectSuppliesTypeById(SuppliesType suppliesType) throws SQLException {
          String sql = "SELECT * FROM petSuppliesType WHERE id=?";
          PreparedStatement statement = mysqlConfig.preparedStatement(sql);
          statement.setInt(1,suppliesType.getId());
          ResultSet resultSet = statement.executeQuery();
          //会返回查询结果集中的第一条记录
          while (resultSet.next()){
              suppliesType = returnSuppliesType(resultSet);

          }
          return  suppliesType;
      }
      //管理员通过 id 查询商品信息
      public SuppliesType ManagerSelectSuppliesTypeById(SuppliesType suppliesType) throws SQLException {
          String sql = "SELECT * FROM petSuppliesType WHERE id=?";
          PreparedStatement statement = mysqlConfig.preparedStatement(sql);
          statement.setInt(1,suppliesType.getId());
          ResultSet resultSet = statement.executeQuery();
          if (!resultSet.next()){
              return  null;
          }
             else{
                 while (resultSet.next()){
                  suppliesType = returnSuppliesType(resultSet);
              }
          }

          return  suppliesType;
      }
      public SuppliesType ManagerSelectSuppliesTypeExistById(SuppliesType suppliesType) throws SQLException {
          String sql = "SELECT * FROM petSuppliesType WHERE id=?";
          PreparedStatement statement = mysqlConfig.preparedStatement(sql);
          statement.setInt(1,suppliesType.getId());
          ResultSet resultSet = statement.executeQuery();
              while (resultSet.next()){
                  suppliesType = returnSuppliesType(resultSet);
              }
          return  suppliesType;
      }




      //预编译
      public static PreparedStatement getUpdateSqlStatement(SuppliesType suppliesType, String sql) throws SQLException {
          PreparedStatement statement = mysqlConfig.preparedStatement(sql);
          statement.setString(1, suppliesType.getName());
          statement.setString(2, suppliesType.getCategory());
          statement.setDouble(3,suppliesType.getPrice());
          statement.setString(4, suppliesType.getDescription());
          statement.setString(5, suppliesType.getImageUrl());
          return statement;

      }
      //用于更新语句
      public static PreparedStatement ManagerGetUpdateSqlStatement(SuppliesType suppliesType, String sql) throws SQLException {
          PreparedStatement statement = mysqlConfig.preparedStatement(sql);
          statement.setString(1, suppliesType.getName());
          statement.setString(2, suppliesType.getCategory());
          statement.setDouble(3,suppliesType.getPrice());
          statement.setString(4, suppliesType.getDescription());
          statement.setString(5, suppliesType.getImageUrl());
          statement.setInt(6, suppliesType.getId());
          return statement;

      }
      //返回给实体
      public SuppliesType  returnSuppliesType(ResultSet resultSet) throws SQLException {
          return new SuppliesType(
                  resultSet.getInt("id"),
                  resultSet.getString("name"),
                  resultSet.getString("category"),
                  resultSet.getDouble("price"),
                  resultSet.getString("description"),
                  resultSet.getString("image_url")
          );
      }

}
