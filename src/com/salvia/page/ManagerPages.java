package com.salvia.page;

import com.salvia.SystemMain;
import com.salvia.data.ManagerTable;
import com.salvia.data.PetSuppliesTable;
import com.salvia.data.PetsTable;
import com.salvia.data.SuppliesTypeTable;
import com.salvia.pojo.Manager;
import com.salvia.pojo.PetSupply;
import com.salvia.pojo.SuppliesType;
import com.salvia.pojo.User;
import com.salvia.service.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.salvia.SystemMain.systemIndex;
import static com.salvia.page.UserPages.userTable;
import static com.salvia.service.SuppliesTypeService.printAllSuppliesType;
import static com.salvia.utils.EmailValidator.isValidEmail;
import static com.salvia.utils.Input.*;
import static com.salvia.utils.Print.printUtil;

public class ManagerPages {
    static ManagerTable managerTable = new ManagerTable();
    static ManagerService managerService = new ManagerService();
    UserService userService = new UserService();
    static PetSupplyService petSupplyService = new PetSupplyService();
   static SuppliesTypeService suppliesTypeService = new SuppliesTypeService();
    static PetsTable petsTable = new PetsTable();
    PetsService petsService = new PetsService();
    PetsPages petsPages = new PetsPages();
    static PetSuppliesTable petSuppliesTable = new PetSuppliesTable();
    static SuppliesTypeTable suppliesTypeTable = new SuppliesTypeTable();
    static PetSupply petSupply = new PetSupply();
    static Manager manager = new Manager();
    static User user = new User();
    static SuppliesType suppliesType = new SuppliesType();
    static List<SuppliesType> addNewPetSuppliesTypeList = new ArrayList<>();
    static UserPages userPages = new UserPages();
    PetSuppliesPages petSuppliesPages = new PetSuppliesPages();
    static PetSuppliesTypePages petSuppliesTypePages = new PetSuppliesTypePages();


    // 管理员登录
    public static void managerLogin(Manager manager) throws SQLException {
        String string = """
                ************************ 欢迎登录宠物管理系统 ************************
                ****** 请输入以下内容，以空格隔开 ********
                ****** 请输入管理员名（或邮箱）和密码 ******
                ****** 输入 0 退出管理员登录 ******
                             
                """;
        System.out.println(string);
        String[] inputs = getStringInputByStringNumAndReferExist(2,0);

        Manager manager1 = new Manager();
        if (inputs == null) {
            managerLogin(manager1);
        }
        else if(inputs.length == 0){
            systemIndex();
        }else {
            manager.setPassword(inputs[1]);
            if (isValidEmail(inputs[0])) {
                manager.setEmail(inputs[0]); // 说明管理员是 邮箱登录
            } else {
                manager.setName(inputs[0]); // 说明管理员是 管理员名登录
            }
              if (managerService.isManager(manager) == null){
                  System.out.println("请重新输入！");//验证密码
                  managerLogin(manager);
              }
            manager = managerService.isManager(manager);
            if (manager == null){ // 说明管理员不存在
                System.out.println("管理员不存在！");
                System.out.println("***** 请重新登录！ *****");
                managerLogin(manager);
            }else { // 管理员存在，显示管理员主页
                System.out.println("登录成功!");
                managerIndex(manager,addNewPetSuppliesTypeList);
            }
        }

    }


    // 管理员主页 managerIndex
    public static List<SuppliesType> managerIndex(Manager manager,List<SuppliesType> addNewPetSuppliesTypeList) throws SQLException {
        if (managerTable.selectManagerById(manager) == null){
            return managerIndex(manager,addNewPetSuppliesTypeList);
        }
        String string = "************************管理员:" + manager.getName()
                + """
                 宠物管理系统的个人主页 ************************
                ****** 请输入以下数字获取对应功能 ********
                ****** 1、显示个人信息 ******
                ****** 2、修改个人信息 ******
                ****** 3、显示所有管理员信息 ******
                ****** 4、新增管理员 ******
                ****** 5、删除管理员 ******
                ****** 6、显示所有用户信息 ******
                ****** 7、删除用户信息 ******
                ****** 8、显示所有宠物信息 ******
                ****** 9、删除宠物信息 ********
                ****** 10、显示所有商品信息 ******
                ****** 11、删除商品信息 ******
                ****** 12、添加商品信息 ******
                ****** 13、修改商品信息 ******
                ****** 14、查看特定用户商品信息 ******
                ****** 15、显示所有用户商品信息 ******
                ****** 16、退出登录 ******
                ****** 0、退出系统 ******
                """;
        System.out.println(string);
        Scanner scanner = new Scanner(System.in);
        int input = scanner.nextInt();
        switch (input) {
            case 1: {
//              ****** 显示个人信息 ******
                managerService.printManager();
                break;
            }
            case 2: {
//              ****** 修改个人信息 ******
                manager = updateManagerPage(manager, -1);
                break;
            }
            case 3: {
//              ****** 显示所有管理员信息 ******
                managerService.printAllManagers();
                break;
            }
            case 4: {
                // ****** 新增管理员 ******
                addManagerPage(manager);
                break;
            }
            case 5: {
//           ****** 删除管理员信息 ******
                //当仅剩一个管理员时，不可操作
                    deleteManagerPage(manager);
                break;
            }

            case 6: {
                //   ****** 显示所有用户信息 ******
                managerService.printAllUsers();
                break;

            }
            case 7: {
                // ****** 删除用户信息 ******
                 userPages.deleteUserPage(manager);
                break;
            }

            case 8: {
                // ****** 显示所有宠物信息 ******
                if (petsTable.selectAllExistPets().isEmpty()){
                    System.out.println("宠物信息为空，查看失败！");
                    return managerIndex(manager,addNewPetSuppliesTypeList);
                }
                managerService.printAllPets();
                break;

            }
            case 9: {
//               ****** 删除宠物信息 ******
                if (petsTable.selectAllExistPets().isEmpty()){
                    System.out.println("宠物信息为空，删除失败！");
                    return managerIndex(manager,addNewPetSuppliesTypeList);
                }
                    PetsPages.deletePetPage(manager);
                break;
            }
            case 10: {
//                     ******显示所有商品信息 ******
                if (suppliesTypeTable.selectAllPetSupplies().isEmpty()){
                    System.out.println("商品信息为空，查看失败");
                    return managerIndex(manager,addNewPetSuppliesTypeList);
                }
                printAllSuppliesType();
                break;

            }
            case 11: {
//              ****** 删除商品信息 ******
                if (suppliesTypeTable.selectAllPetSupplies().isEmpty()){
                    System.out.println("商品为空，删除失败！");
                    return managerIndex(manager,addNewPetSuppliesTypeList);
                }
                    PetSuppliesTypePages.deletePetSupplyTypePage(manager);
                break;
            }
            case 12: {
//                ****** 添加商品信息 ******/
                petSuppliesTypePages.addPetSuppliesTypePage(manager,addNewPetSuppliesTypeList);
                break;
            }
            case 13: {
//                ****** 修改商品信息 ******/
                if (suppliesTypeTable.selectAllPetSupplies().isEmpty()){
                    System.out.println("商品为空，修改失败！");
                    return managerIndex(manager,addNewPetSuppliesTypeList);
                }
                petSuppliesTypePages.updateSuppliesTypeTablePage(manager);
                break;
            }
            case 14: {
//                     ******查看特定用户商品信息 ******
                managerSelectAllUserPetSupplyPage(manager);
                break;
            }
            case 15: {
                //   ****** 显示所有用户商品信息 ******
                PetSupplyService.managerPrintPetSupply();
                break;
            }
            case 16: {
//                ****** 退出登录 ******/
                System.out.println("退出登录");
                SystemMain.systemIndex();
                break;
            }
            case 0:
                // 退出系统
                System.exit(200);
            default:
                System.out.println("输入无效，请重新输入");

        }
        managerIndex(manager,addNewPetSuppliesTypeList);
        return addNewPetSuppliesTypeList;
    }

    // 更新管理员信息 的页面
    public static Manager updateManagerPage(Manager manager, int input) throws SQLException {
        String string = "************************管理员:" + manager.getName() + """ 
                 修改个人信息 ************************
                ****** 请输入以下数字获取对应功能 ********
                ****** 1、修改管理员名 ******
                ****** 2、修改邮箱 ******
                ****** 3、修改密码 ******
                ****** 4、退出修改 ******
                ****** 0、退出系统 ******
                """;
        if (input == -1) {
            System.out.println(string);
            input = getIntInput();
        }
        // 判断是否为 修改邮箱，然后获取 终端对应的输入

        switch (input) {
            case 1:
                // 执行修改管理员名的操作
                System.out.println("请输入新的管理员名");
                break;
            case 2:
                // 执行修改邮箱的操作
                System.out.println("请输入新的邮箱");
                break;
            case 3:
                // 执行修改密码的操作
                System.out.println("请输入新的密码");
                break;
            case 4:
                System.out.println("退出成功！");
                managerIndex(manager,addNewPetSuppliesTypeList);
            case 0:
                // 退出系统
                System.exit(200);
            default:
                System.out.println("输入无效，请重新输入");
                return null;
        }
        String s = getSwitchEmailOrStringInput(3, 2, input);
        Manager manager1 = new Manager();
        if (s == null) {
            System.out.println("输入无效，请重新输入");
            return updateManagerPage(manager, -1);
        } else {
            switch (input) {
                case 1:
                    // 执行修改用户名的操作
                    manager1.setName(s);
                    if (ManagerTable.selectManagerByName(manager1) != null) {
                        System.out.println("更改无效，该用户名存在,请重新输入");
                        updateManagerPage(manager, 1);
                    } else manager.setName(s);
                    break;
                case 2:
                    // 执行修改邮箱的操作
                    manager1.setEmail(s);
                    if (ManagerTable.selectManagerByEmail(manager1) != null) {
                        System.out.println("更改无效，该邮箱存在,请重新输入");
                        updateManagerPage(manager, 2);
                    } else manager.setEmail(s);
                    break;
                case 3:
                    // 执行修改密码的操作
                    manager1.setPassword(s);
                    ManagerTable.updateManagerPassword(manager1);
                    System.out.println("更新成功！");
                    updateManagerPage(manager, -1);
                    return manager;

            }
            // 执行退出修改操作
            managerTable.updateManager(manager); // 更新语句
            System.out.println("更新成功！");
            updateManagerPage(manager, -1);
            return manager;
        }
    }


    public static void addManagerPage(Manager manager) throws SQLException {
        String string = """
                ************************ 新增宠物管理系统管理员 ************************
                ****** 请输入以下数字，获取对应功能 ********
                ****** 1、添加新管理员 ******
                ****** 0、退出系统 ******
                """;
        System.out.println(string);
        int input = getIntInput();
        switch (input) {
            case 1: {
                // 注册完整信息
                Manager manager1 = addManager();
                if (manager1 == null) {
                    managerIndex(manager,addNewPetSuppliesTypeList);
                }
                break;
            }
            case 0: {
                // 退出系统
                System.exit(200);
                break;
            }
            default: {
                System.out.println("输入的指令有误！请重新输入");
                addManagerPage(manager);
                break;
            }
        }
    }

    // 设置管理员名、邮箱和密码
    public static Manager addManager() throws SQLException {
        String string = """
                ************************ 添加新管理员 ************************
                ****** 请输入各项信息，以空格隔开 ********
                 ****** 输入 0 退出 ********
                """;
        System.out.println(string);
        printUtil("管理员名,邮箱,密码");
        String[] strings = getStringInputByStringNumAndEmailAndReferExist(3, 2, 0);

        if (strings == null) {
           managerIndex(manager,addNewPetSuppliesTypeList);
        } else if (strings.length == 0) {
            System.out.println("退出成功！");
            return null;
        } else if (managerService.isManagerByNameAndEmail(new Manager(strings[0], strings[1]))) {
            System.out.println("注册失败,管理员已存在！");
            addManager();
        } else {
            manager = new Manager(strings[0], strings[1], strings[2]);
            managerTable.insertManager(manager);
            System.out.println("新增管理员成功!");
            managerService.printAllManagers();
        }
        return manager;
    }// 删除用户信息





    //删除管理员信息
    public static void deleteManagerPage(Manager manager) throws SQLException {
        String string = """
                ************************ 管理员:
                """ + manager.getName() + """
                删除管理员信息 ************************
                ****** 请输入要删除的管理员ID ********
                """;
        System.out.println(string);
        managerService.printAllManagers();
        int input = getIntInput();
        Manager manager1 = new Manager();
        manager1.setId(input);
        if (managerTable.selectManagerById(manager1) == null){
            System.out.println("该管理员不存在，删除失败");
            deleteManagerPage(manager);
        }else if (manager.getId() == manager1.getId()){
            System.out.println("删除有误！不得删除个人管理员信息");
            managerIndex(manager,addNewPetSuppliesTypeList);
        }
        else {
            System.out.println(ManagerTable.deleteManager(manager1) == 1 ? "删除成功!" : "删除失败!");
            managerLogin(manager);
        }

    }


    public static void managerSelectAllUserPetSupplyPage(Manager manager) throws SQLException {
        managerService.printAllUsers();
        String string = """
                ************************ 管理员:
                 """ + manager.getName() + """
                选择查看用户的商品信息 ************************
                ****** 请输入要查询用户的ID ********
                ****** 0、退出查询 ******
                """;
        System.out.println(string);
        String s = getStringInput();
        User user = new User();
        if (s != null) {
            String[] strings = getStringInputByReferExist(s, 0);
            if (strings.length == 0) {
                System.out.println("退出成功！");
               managerIndex(manager,addNewPetSuppliesTypeList);
            }
            try {
                user.setId(Integer.parseInt(s));
            }catch (Exception e){
                System.out.println("输入的id有误，请重新输入！");
                managerSelectAllUserPetSupplyPage(manager);
            }
        }
        //校验user id 是否存在
        if (userTable.selectUserById(user) != null) {
            petSupply.setUser_id(user.getId());
            //校验用户是否有购买商品信息
            if (petSuppliesTable.selectPetSupplyByUser_Id(petSupply) == null ){
                System.out.println("该用户没有任何商品信息，查看失败！");
                managerSelectAllUserPetSupplyPage(manager);
            }
            else {
                managerSelectAllUserPetSupplyStatement(manager,user);
            }
        } else {
            System.out.println("查找不到用户，请重新输入！");
            managerSelectAllUserPetSupplyPage(manager);
        }



    }

    public static void managerSelectAllUserPetSupplyStatement(Manager manager, User user) throws SQLException {
        user = userTable.selectUserById(user);
        String string = """
                ************************ 管理员:
                 """ + manager.getName() + """
                查询""" + user.getName()+ """
                用户商品信息 ************************
                ****** 1,用户加入购物车的商品 ********
                ****** 2,用户已支付的商品 ******
                ****** 3,退出查询该用户 ******
                ****** 3,退出查询功能 ******
                ****** 0,退出系统 ******
                """;
        System.out.println(string);
        int input = getIntInput();
        switch (input){
            case 1:
                petSupplyService.managerPrintUserPetSupplyStatus(user);
                managerSelectAllUserPetSupplyStatement(manager,user);
                break;
            case 2:

                petSupplyService.managerPrintUserPetSupplyStatusIsPay(user);
                managerSelectAllUserPetSupplyStatement(manager,user);
                break;
            case 3:
                managerSelectAllUserPetSupplyPage(manager);
                managerSelectAllUserPetSupplyStatement(manager,user);
                break;
            case 4:
               managerIndex(manager,addNewPetSuppliesTypeList);
                break;
            case 5:
                System.exit(200);
                break;
        }

    }



}





