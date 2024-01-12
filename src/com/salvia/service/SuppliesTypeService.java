package com.salvia.service;

import com.salvia.data.SuppliesTypeTable;
import com.salvia.pojo.SuppliesType;

import java.sql.SQLException;
import java.util.List;

import static com.salvia.utils.Print.printUtil;

//  SuppliesType 的服务层
public class SuppliesTypeService {
    static SuppliesTypeTable suppliesTypeTable = new SuppliesTypeTable();
    static SuppliesType suppliesType = new SuppliesType();
    //打印表头
    public static void printAddSuppliesTypeTitle(){
        printUtil("宠物用品名称,宠物用品类别,宠物用品价格/元");
    }
    // 查询所有的 商品信息，并且打印出来
    public static void printAllSuppliesType() throws SQLException {
        // 打印出终端 表头,要与 toString 一一对应
        printUtil("商品ID,宠物用品名称,宠物用品类别,宠物用品价格,宠物用品描述,宠物用品图片链接");
//        public String toString() {
//            // printUtil是按照 ”,“ 来进行分割的，所以，需要重写 SuppliesType 实体的 toString
//            // 只需要把 字段 用 "," 连接起来就可以了
//            return  id + "," + name + "," + category + ", " + price + ", " + description + "," + imageUrl;
//        }
        suppliesTypeTable.selectAllPetSuppliesTest().forEach(suppliesType->{
            printUtil(suppliesType.toString());
            // 打印出结果
            // printUtil是按照 ”,“ 来进行分割的，所以，需要重写 SuppliesType 实体的 toString
        });
    }
    public static void printAddSuppliesType(List<SuppliesType> addSuppliesTypeList) throws SQLException {
        printUtil("商品ID,宠物用品名称,宠物用品类别,宠物用品价格,宠物用品描述,宠物用品图片链接");
//        System.out.println("addSuppliesTypeList = " + addSuppliesTypeList);
        addSuppliesTypeList.forEach(addSuppliesType->{
//            printUtil(suppliesType.toString());
            printUtil(addSuppliesType.toString());
        });
    }
    //管理员打印特定商品情况
    public static void managerPrintPetSupplyById(SuppliesType suppliesType) throws SQLException {
        printUtil("商品ID,宠物用品名称,宠物用品类别,宠物用品价格,宠物用品描述,宠物用品图片链接");
        suppliesType = suppliesTypeTable.ManagerSelectSuppliesTypeById(suppliesType);
        printUtil(suppliesType.toString());


    }

}
