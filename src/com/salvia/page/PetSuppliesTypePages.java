package com.salvia.page;

import com.salvia.data.SuppliesTypeTable;
import com.salvia.pojo.Manager;
import com.salvia.pojo.SuppliesType;
import com.salvia.service.SuppliesTypeService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.salvia.page.ManagerPages.*;
import static com.salvia.service.SuppliesTypeService.printAllSuppliesType;
import static com.salvia.utils.Input.*;

public class PetSuppliesTypePages {
    //删除商品信息
    public static void deletePetSupplyTypePage(Manager manager) throws SQLException {
        String string = """
                ************************ 管理员:
                """ + manager.getName() + """
                删除商品信息 ************************
                ****** 请输入要删除的商品ID(用空格隔开) ********
                 ******** 0、退出删除商品页面 *********
                """;
        System.out.println(string);
        printAllSuppliesType();
        String s = getStringInput();
        if (s != null) {
            String[] strings = getStringInputByReferExist(s, 0);
            if (strings.length == 0) {
                System.out.println("退出成功！");
                managerIndex(manager,addNewPetSuppliesTypeList);
            }
            List<Integer> delectSuppliesTypeList = new ArrayList<>();
            //校验输入 id 是否为整数
            for (String string1 : strings) {
                try {
                    delectSuppliesTypeList.add(Integer.parseInt(string1));
                } catch (Exception e) {
                    System.out.println("输入的id有误，请重新输入！");

                }
            }
            //校验 id是否存在
            for (int id : delectSuppliesTypeList) {
                    suppliesType.setId(id);
                    if (suppliesTypeTable.managerDeleteSuppliesType(suppliesType) > 0) {
                        System.out.println("删除成功");
                        printAllSuppliesType();
                    }
               else {
                    System.out.println("输入的id不存在，请重新输入！");
                    deletePetSupplyTypePage(manager);
                    break;
                }
            }
            deletePetSupplyTypePage(manager);
        }

    }
    //管理员添加商品
    public static List<SuppliesType> addPetSuppliesTypePage(Manager manager, List<SuppliesType> addNewPetSuppliesTypeList) throws SQLException {
        String string = """
                ************************ 管理员:
                """ + manager.getName() + """
                添加商品 ************************
                 ****** 输入以下内容，以空格隔开 ********
                 ****** 请输入宠物用品名称,宠物用品类别 ,宠物用品价格******
                 ******  输入 0 退出*****************
                """;
        System.out.println(string);
        SuppliesTypeService.printAddSuppliesTypeTitle();
        String [] inputs = getStringInputByStringNumAndReferExist(3,0);
        if(inputs == null){
            System.out.println("输入的内容有误，请重新输入！");
            return addPetSuppliesTypePage(manager,addNewPetSuppliesTypeList);
        }
        if (inputs.length == 0 ) {
            System.out.println("退出成功！");
            managerIndex(manager,addNewPetSuppliesTypeList);
        }
        try {
            suppliesType.setPrice(Double.parseDouble(inputs[2]));
        }catch (Exception e){
            System.out.println("输入的价格有误，请重新输入");
            // 不加 return 的话，当它执行完后还会回来接着往下走
            return addPetSuppliesTypePage(manager,addNewPetSuppliesTypeList);
        }

        suppliesType.setName(inputs[0]);
        suppliesType.setCategory(inputs[1]);
        //校验添加的商品是否存在
        if (suppliesTypeTable.selectSuppliesTypeByName(suppliesType) == null){
            SuppliesTypeTable.insertSuppliesType(suppliesType);
            addNewPetSuppliesTypeList.add(suppliesTypeTable.managerSelectSuppliesTypeByName(suppliesType));
            SuppliesTypeService.printAddSuppliesType(addNewPetSuppliesTypeList);
            return addPetSuppliesTypePage(manager,addNewPetSuppliesTypeList);
        }
        else {
            System.out.println("输入的商品名称已存在，请重新输入！");
            return addPetSuppliesTypePage(manager,addNewPetSuppliesTypeList);
        }
    }
    public static void updateSuppliesTypeTablePage(Manager manager) throws SQLException {
        String string = """
                ************************ 管理员:
                """ + manager.getName() + """
                修改商品信息 ************************
                ****** 请输入要更改的商品ID ********
                ****** 输入 0 退出更改 ********
                """;
        System.out.println(string);
        printAllSuppliesType();
        String s = getStringInput();
        if (s != null) {
            String[] strings = getStringInputByReferExist(s, 0);
            if (strings.length == 0) {
                System.out.println("退出成功！");
                managerIndex(manager,addNewPetSuppliesTypeList);
            }
            //校验输入 id 是否为整数
            try {
                suppliesType.setId(Integer.parseInt(s));
                if (suppliesTypeTable.ManagerSelectSuppliesTypeById(suppliesType)!= null){
                    updateSuppliesTypeTable(manager,suppliesType,-1);
                }
            } catch (Exception e) {
                System.out.println("输入的id有误，请重新输入！");
                updateSuppliesTypeTablePage(manager);
            }

        }


    }

    //修改商品
    //input用于判断更改信息是否存在，不存在返回对应操作
    public static SuppliesTypeTable updateSuppliesTypeTable(Manager manager,SuppliesType suppliesType,int input) throws SQLException {
        suppliesType = suppliesTypeTable.ManagerSelectSuppliesTypeExistById(suppliesType);
        String string = "************************管理员:" +manager.getName()+
                "修改商品 "+suppliesType.getName()+" 的信息"+
                """ 
                ************************
                ****** 请输入以下数字获取对应功能 ********
                ****** 1、修改宠物用品名称 ******
                ****** 2、修改宠物用品类别 ******
                ****** 3、修改宠物用品价格 ******
                ****** 4、修改宠物用品描述 ******
                ****** 5、修改宠物用品图片链接 ******
                ****** 6、退出该商品的修改 ******
                ****** 0、退出系统 ******
                """;

        if (input == -1) {
            System.out.println(string);
            input = getIntInput();
        }
        // 判断是否为 修改邮箱，然后获取 终端对应的输入
        switch (input) {
            case 1:
                System.out.println("请输入新的宠物用品名称");
                break;
            case 2:
                System.out.println("请输入新的宠物用品类别");
                break;
            case 3:
                System.out.println("请输入新的宠物用品价格");
                break;
            case 4:
                System.out.println("请输入新的宠物用品描述");
                break;
            case 5:
                System.out.println("请输入新的宠物用品图片链接");
                break;

            case 6:
                System.out.println("退出成功！");
                managerIndex(manager,addNewPetSuppliesTypeList);
            case 0:
                // 退出系统
                System.exit(200);
            default:
                System.out.println("输入无效，请重新输入");
                return null;
        }
        String s = getStringInput();
        if (s == null){
            System.out.println("输入无效，请重新输入");
            return updateSuppliesTypeTable(manager,suppliesType,-1);
        }else {
            SuppliesType suppliesType1 = new SuppliesType();
            switch (input) {
                case 1: {
                    suppliesType1.setName(s);
                    if (suppliesTypeTable.selectSuppliesTypeByName(suppliesType1) != null) {
                        System.out.println("更改无效，该商品已存在,请重新输入");
                        updateSuppliesTypeTable(manager, suppliesType,1);
                    } else suppliesType.setName(s);
                    break;
                }
                case 2:
                    suppliesType.setCategory(s);
                    break;
                case 3:
                    // 执行修改密码的操作
                    try {
                        suppliesType.setPrice(Double.parseDouble(s));
                    }catch (Exception e){
                        System.out.println("输入的商品价格有误，请重新输入！");
                        updateSuppliesTypeTable(manager,suppliesType,3);
                    }
                    break;
                case 4:
                    suppliesType.setDescription(s);
                    break;
                case 5:
                    suppliesType.setImageUrl(s);
                    break;
            }
            suppliesTypeTable.updateSuppliesType(suppliesType);
            System.out.println("更新成功！");
            SuppliesTypeService.managerPrintPetSupplyById(suppliesType);
            updateSuppliesTypeTable(manager,suppliesType,-1);
            return suppliesTypeTable;
        }
    }


}
