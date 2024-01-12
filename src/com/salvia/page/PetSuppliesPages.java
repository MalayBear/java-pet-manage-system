package com.salvia.page;

import com.salvia.data.PetSuppliesTable;
import com.salvia.data.UserTable;
import com.salvia.pojo.Pet;
import com.salvia.pojo.PetSupply;
import com.salvia.pojo.SuppliesType;
import com.salvia.pojo.User;
import com.salvia.service.PetSupplyService;
import com.salvia.service.SuppliesTypeService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.salvia.data.PetSuppliesTable.updatePetSupplyByStatus;
import static com.salvia.service.PetSupplyService.printAllPetSuppliesPay;
import static com.salvia.utils.Input.*;

public class PetSuppliesPages {
    PetSupplyService petSupplyService = new PetSupplyService();
    PetSuppliesTable petSuppliesTable = new PetSuppliesTable();
    PetSupply petSupply = new PetSupply();
    SuppliesType suppliesType = new SuppliesType();
    UserTable userTable = new UserTable();
    public void addUserSelectSuppliesPage(User user) throws SQLException {
           String string =  """
               ************************ 新增要购买的宠物商品 ************************
                   ****** 请输入宠物用品的Id(或宠物用品名称),购买的数量  以空格隔开********
                              ******** 0、退出新增商品 *********
                """;
        System.out.println(string);
        String [] strings = getStringInputByStringNumAndReferExist(2,0);
        PetSupply petSupply1 = new PetSupply();
        if (strings != null){
            if (strings.length == 0) {
                System.out.println("退出成功！");
                petSuppliesPagesIndex(user);
            }
            petSupply.setUser_id((user.getId()));
            try {   //校验输入的id 或名称是否存在
                    petSupply1.setPetSuppliesType_id(Integer.parseInt(strings[0]));
                if (petSuppliesTable.UserSelectPetSupplyExistByPetSuppliesTypeId(petSupply1) == null){
                    System.out.println("输入的id不存在，请重新输入！");
                    addUserSelectSuppliesPage(user);
                    return;
                }
                else {
                    petSupply.setPetSuppliesType_id(Integer.parseInt(strings[0]));
                    petSupply = petSuppliesTable.UserSelectPetSupplyByPetSuppliesTypeId(petSupply);
                }
            }catch (Exception e){
                    petSupply1.setName(strings[0]);
                if (petSuppliesTable. UserSelectPetSupplyByPetSuppliesTypeName(petSupply1) == null){
                    System.out.println("输入的商品名称有误！请重新输入！");
                    addUserSelectSuppliesPage(user);
                    return;
                }
                else {
                    petSupply.setName(strings[0]);
                    petSupply = petSuppliesTable.UserSelectPetSupplyByPetSuppliesTypeName(petSupply);
                }
            }
            try{
                petSupply.setNum(Integer.parseInt(strings[1]));
                if (PetSuppliesTable.insertPetSupply(petSupply) > 0){
                    System.out.println("新增成功！已添加购物车");
                    UserSelectPayPage(user);
                    return;
                }
            }catch (Exception e){
                System.out.println("输入的数量不为整数！");
            }
        }
        addUserSelectSuppliesPage(user);

    }
    public void UserSelectPayPage(User user) throws SQLException {
        String string = """
                 ************************ 是否要支付宠物商品 ************************
                                   ********1，支付*********
                                   ********2，继续添加宠物商品 *********
                                   ********0，退出支付页面 *********
                """;
        System.out.println(string);
        printAllPetSuppliesPay();
        int input = getIntInput();
        switch (input){
            case 1:{
                UserPetSuppliesPay(user);
                break;
            }
            case 2:{
                printAllPetSuppliesPay();
                addUserSelectSuppliesPage(user);
                break;
            }
            case 0:{
                System.out.println("退出成功！");
                petSuppliesPagesIndex(user);
                break;
            }
            default:{
                System.out.println("输入的指令有误，请重新输入！");
                UserSelectPayPage(user);
                break;
            }

        }
    }
    public void deleteUserSelectSuppliesPage(User user) throws SQLException {
        String string = """
                ************************  删除购物车的宠物商品  ************************
                               ******    请输入要删除Id (以空格隔开)********
                               ******** 0、退出删除商品页面 *********
                 """;
            System.out.println(string);
            petSupplyService.printPetSupplyStatus(user);
            String s = getStringInput();
         if (s != null) {
            String[] strings = getStringInputByReferExist(s, 0);
            if (strings.length == 0) {
                System.out.println("退出成功！");
                petSuppliesPagesIndex(user);
            }
            List<Integer> delectSuppliesList = new ArrayList<>();
            //校验输入 id 是否为整数
            for (String string1 : strings) {
                try {
                    delectSuppliesList.add(Integer.parseInt(string1));
                } catch (Exception e) {
                    System.out.println("输入的id有误，请重新输入！");
                    deleteUserSelectSuppliesPage(user);
                }
            }
            //校验 id是否存在
            for (int id : delectSuppliesList) {
                    petSupply.setId(id);
                    if (PetSuppliesTable.userSelectPetSupplyByStatus(petSupply) != null){
                        if (petSuppliesTable.deletePetSupplyById(petSupply) > 0) {
                            System.out.println("删除成功");
                            petSupplyService.printPetSupplyStatus(user);
                    }
                    else {
                            System.out.println("输入的id不存在，请重新输入！");
                            deleteUserSelectSuppliesPage(user);
                            break;
                        }

                }
            }
            deleteUserSelectSuppliesPage(user);
        }
    }

    public void userSelectAllPetSuppliesByStatusIsPay(User user) throws SQLException {
       String string =  """
               ************************  以下为用户：
              """ +user.getName() + """ 
             已支付的商品 ************************
             """;
        System.out.println(string);
        petSupplyService.printPetSupplyStatusIsPay(user);
        System.out.println("输入0 退出查看");
        int input = getIntInput();
        if (input == 0) petSuppliesPagesIndex(user);
        else {
            System.out.println("输入的数字有误,请重新输入");
            userSelectAllPetSuppliesByStatusIsPay(user);
        }

    }
    public void userSelectAllPetSuppliesByStatus(User user) throws SQLException {
        String string =  """
               ************************  以下为用户：
              """ +user.getName() + """ 
             未支付的商品 ************************
             """;
        System.out.println(string);
        petSupplyService.printPetSupplyStatus(user);
        System.out.println("输入0 退出查看");
       int input = getIntInput();
       if (input == 0){
           System.out.println("退出成功！");
           petSuppliesPagesIndex(user);
       }
      else {
           System.out.println("输入的数字有误,请重新输入");
           userSelectAllPetSuppliesByStatus(user);

       }


    }

    public void UserPetSuppliesPay(User user) throws SQLException {
        String string = """
                ************************ 支付宠物商品  ************************
                               ****** 请输入id（用空格隔开）********
                               ******** 0、退出支付商品页面 *********
                 """;
        System.out.println(string);

        String s = getStringInput();
        if (s != null) {
            String[] strings = getStringInputByReferExist(s, 0);
            if (strings.length == 0)
            {
                System.out.println("退出成功！");
                petSuppliesPagesIndex(user);
            }

            List<Integer> id_list = new ArrayList<>();
            for (String string1 : strings) {
                try {
                    id_list.add(Integer.parseInt(string1));
                } catch (Exception e) {
                    System.out.println("输入的 id 有误");
                    UserSelectPayPage(user);
                    break;
                }
            }
            for (Integer id : id_list) {
                PetSupply petSupply1 = new PetSupply();
                petSupply1.setId(id);
                userTable.selectUserByName(user);
                petSupply1.setUser_id(user.getId());
                if (PetSuppliesTable.userSelectPetSupplyByStatus(petSupply1) == null){
                    System.out.println("输入的id不存在，支付失败！");
                    UserPetSuppliesPay(user);
                    break;
                }
                else {
                    updatePetSupplyByStatus(petSupply1);
                    System.out.println("支付成功");
                }

            }

        }
        petSupplyService.printPetSupplyStatusIsPay(user);
        UserSelectPayPage(user);

    }

    public void petSuppliesPagesIndex(User user) throws SQLException {

        String string = "************************欢迎用户:" +user.getName()+
                """
                 进入宠物商店 ************************
                 ****** 请输入以下数字获取对应功能 ********
                 ****** 1、新增宠物商品到购物车 ******
                 ****** 2、删除购物车的宠物商品  ******
                 ****** 3、查看所有添加到购物车的宠物商品 ******
                 ****** 4,查看所有已支付的商品 ******
                 ****** 0、退出商店 ******
                """;
        //打印商品信息
        SuppliesTypeService.printAllSuppliesType();

                System.out.println(string);
                int input = getIntInput();
                switch (input){
                    case 1:{
                        addUserSelectSuppliesPage(user);
                        break;
                    }
                    case 2:{
                            petSupply.setUser_id(user.getId());
                        if (petSuppliesTable.selectPetSupplyByUser_Id(petSupply) == null){
                            System.out.println("购物车为空！删除失败！");
                            petSuppliesPagesIndex(user);
                            return;
                        }
                        else deleteUserSelectSuppliesPage(user);
                        break;
                    }
                    case 3:{
                        userSelectAllPetSuppliesByStatus(user);
                        break;
                    }
                    case 4:{
                        userSelectAllPetSuppliesByStatusIsPay(user);
                        break;
                    }
                    case 0:
                        // 退出商店
                        List<Pet> addNewPetList = new ArrayList<>();
                        System.out.println("退出成功！");
                        UserPages.userIndex(user,addNewPetList);

                    default:{
                        System.out.println("输入无效，请重新输入");
                        petSuppliesPagesIndex(user);
                        break;
                    }
                }
    }

}
