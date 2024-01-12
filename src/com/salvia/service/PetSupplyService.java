package com.salvia.service;

import com.salvia.data.PetSuppliesTable;
import com.salvia.pojo.PetSupply;
import com.salvia.pojo.User;

import java.sql.SQLException;

import static com.salvia.utils.Print.printUtil;

public class PetSupplyService {
    boolean bool = false;
    static PetSuppliesTable petSuppliesTable = new PetSuppliesTable();
    static PetSupply petSupply = new PetSupply();

    //打印petSupplyTable供管理员查看
    public static void printAllPetSuppliesPay() throws SQLException {
        printUtil("id,用户ID,宠物用品ID,宠物商品名称,购买数量,支付状态,加入购物车时间,支付时间");
        petSuppliesTable.selectAllPetSuppliesPay().forEach(petSupply -> {
            printUtil(petSupply.toString());
        });

    }

    //校验添加购物车的商品 id 是否存在
    private boolean isPetSupplyById(PetSupply petSupply) throws SQLException {
     bool =  petSuppliesTable.selectPetSupplyByPetSupplyId(petSupply) != null;
        System.out.println(bool ? "" : "商品不存在！请重新输入");
        return bool;
    }
    //校验添加购物车的商品 名称 是否存在
    private boolean isPetSupplyByName(PetSupply petSupply) throws SQLException {
        bool = petSuppliesTable.selectPetSupplyByName(petSupply) != null;
        System.out.println(bool ? "" : "商品不存在！请重新输入");
        return bool;
}

//查询 用户输入的为商品id 还是 名称
//    public boolean isPetSupply(PetSupply petSupply) throws SQLException {
////       if (strings[0].equals(petSupply.getName())){
////           petSupply.setName(strings[0]);
////       }
////        if (isPetSupplyById(petSupply)) {
////                petSupply.setPetSuppliesType_id(Integer.parseInt(strings[0]));
////            }
//    }

    //打印商品是否处于 支付 后的状态
    public  void printPetSupplyStatusIsPay(User user) throws SQLException {
        printUtil("id,用户ID,宠物用品ID,宠物商品名称,购买数量,支付状态,加入购物车时间,支付时间");
        petSupply.setUser_id(user.getId());
        PetSuppliesTable.userSelectAllPetSupplyByStatusIsPay(petSupply).forEach(petSupply1->{
            printUtil(petSupply1.toString());
        });

    }
    //打印添加到购物车的商品（未支付状态）
    public  void printPetSupplyStatus(User user) throws SQLException {
        printUtil("id,用户ID,宠物用品ID,宠物商品名称,购买数量,支付状态,加入购物车时间,支付时间");
        petSupply.setUser_id(user.getId());
        PetSuppliesTable.userSelectAllPetSupplyByStatus(petSupply).forEach(petSupply1->{
            printUtil(petSupply1.toString());
        });

    }
    //管理员打印用户添加到购物车的商品（未支付状态）
    public  void managerPrintUserPetSupplyStatus(User user) throws SQLException {
        printUtil("id,用户ID,宠物用品ID,宠物商品名称,购买数量,支付状态,加入购物车时间,支付时间");
        petSupply.setUser_id(user.getId());
        PetSuppliesTable.managerSelectAllPetSupplyByStatus(petSupply).forEach(petSupply1->{
            printUtil(petSupply1.toString());
        });

    }
    //管理员打印用户添加到购物车的商品（已支付状态）
    public  void managerPrintUserPetSupplyStatusIsPay(User user) throws SQLException {
        printUtil("id,用户ID,宠物用品ID,宠物商品名称,购买数量,支付状态,加入购物车时间,支付时间");
        petSupply.setUser_id(user.getId());
        PetSuppliesTable.ManagerSelectAllPetSupplyByStatusIsPay(petSupply).forEach(petSupply1->{
            printUtil(petSupply1.toString());
        });

    }
    //管理员打印所有用户购买商品的情况
    public static void managerPrintPetSupply() throws SQLException {
        printUtil("id,用户ID,宠物用品ID,宠物商品名称,购买数量,支付状态,加入购物车时间,支付时间");
        PetSuppliesTable.selectAllPetSuppliesPay().forEach(petSupply1->{
            printUtil(petSupply1.toString());
        });

    }

}





