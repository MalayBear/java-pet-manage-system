package com.salvia.page;

import com.salvia.SystemMain;
import com.salvia.data.PetsTable;
import com.salvia.data.UserTable;
import com.salvia.pojo.Manager;
import com.salvia.pojo.Pet;
import com.salvia.pojo.PetSupply;
import com.salvia.pojo.User;
import com.salvia.service.ManagerService;
import com.salvia.service.PetsService;
import com.salvia.service.UserService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.salvia.service.UserService.printUserInfo;
import static com.salvia.utils.EmailValidator.isValidEmail;
import static com.salvia.utils.Input.*;
import static com.salvia.utils.Print.printUtil;

public class UserPages {
    static UserTable userTable = new UserTable();
    static UserService userService = new UserService();
    static PetsTable petsTable = new PetsTable();
    static PetsService petsService = new PetsService();
    static PetsPages petsPages = new PetsPages();
    static PetSupply petSupply =new PetSupply();
    static PetSuppliesPages petSuppliesPages = new PetSuppliesPages();
    User user = new User();
    static Pet pet = new Pet();
    static ManagerService managerService = new ManagerService();

    // 用户登录
    public void userLogin(User user) throws SQLException {
        String string = """
                ************************ 欢迎登录宠物管理系统 ************************
                ****** 请输入以下内容，以空格隔开 ********
                ****** 请输入用户名（或邮箱）和密码 ******
                ******  输入 0 退出*****************
                """;
        System.out.println(string);
        User user1 = new User();
        String[] inputs = getStringInputByStringNumAndReferExist(2,0);
        if (inputs == null){
            userLogin(user1);
        } else if (inputs.length == 0){
            SystemMain.systemIndex();
        }else {
            user.setPassword(inputs[1]);
            if (isValidEmail(inputs[0])) {
                user.setEmail(inputs[0]); // 说明用户是 邮箱登录
            }else {
                user.setName(inputs[0]); // 说明用户是 用户名登录
            }
            user = userService.isUser(user);
            if (user == null){ // 说明用户不存在
                System.out.println("***** 请重新登录！ *****");
                userLogin(user1);

            }
            else { // 用户存在，显示用户主页
                System.out.println("登录成功！");
                List<Pet> addNewPetList = new ArrayList<>();
                if (userIndex(user, addNewPetList) != null) userLogin(user1); // 判断是否需要回调
            }
        }
    }


    // 用户主页 userIndex
    public static List<Pet> userIndex(User user, List<Pet> addNewPetList) throws SQLException {
        if (userTable.selectUserById(user) == null)
            return userIndex(user,addNewPetList);
        String string = "************************用户:" +user.getName()
                + """ 
                 宠物管理系统的个人主页 ************************
                ****** 请输入以下数字获取对应功能 ********
                ****** 1、显示个人信息 ******
                ****** 2、修改个人信息 ******
                ****** 3、显示名下所有宠物信息 ******
                ****** 4、修改宠物信息 ******
                ****** 5、删除宠物信息 ******
                ****** 6、寄养新宠物 ******
                ****** 7、进入宠物商店 ******
                ****** 8、退出登录 ******
                ****** 0、退出系统 ******
                """;
        System.out.println(string);
        int input = getIntInput();
        switch (input){
            case 1:{
//              ****** 1、显示个人信息 ******
               printUserInfo(user);
                break;
            }
            case 2:{
//              ****** 2、修改个人信息 ******
                user = updateUserPage(user,-1);
                break;
            }
            case 3:{
//              ****** 3、显示名下所有宠物信息 ******
                if (petsService.isUserHavePet(user)){
                    petsService.printUserAllPets(petsTable.selectUsersPet(user));
                }
                else {
                    System.out.println("您名下没有宠物，查看失败！");
                    userIndex(user, addNewPetList);
                }
                break;
            }
            case 4:{
//              ****** 4、修改宠物信息 ******
                if (petsService.isUserHavePet(user)){
                    petsPages.updateUsersPetPage(user);
                }
                else {
                    System.out.println("您名下没有宠物，查看失败！");
                    userIndex(user, addNewPetList);
                }
                break;
            }
            case 5:{
//              ****** 5、删除宠物信息 ******
                if (petsService.isUserHavePet(user)){
                    petsPages.deleteUsersPetPage(user,pet);
                }
                else {
                    System.out.println("您名下没有宠物，查看失败！");
                    userIndex(user, addNewPetList);
                }
                break;

            }
            case 6:{
//               ****** 6、寄养新宠物 ******
                addNewPetList = petsPages.addUsersPetPage(user,addNewPetList); // 显示 新增宠物页面
                break;
            }
            case 7:{
                //   ****** 7、进入宠物商店 ******
                petSuppliesPages.petSuppliesPagesIndex(user);
                break;
            }
            case 8:{
                System.out.println("已退出登录!");
                SystemMain.systemIndex();
                return null;
            }
            case 0:
                // 退出系统
                System.exit(200);
            default:
                System.out.println("输入无效，请重新输入");
        }
        userIndex(user, addNewPetList);
        return addNewPetList;
    }

    // 更新用户信息 的页面
   public static User updateUserPage(User user,int input) throws SQLException {
       if (userTable.selectUserById(user) == null)
           return updateUserPage(user,input);
        String string = "************************用户:" +user.getName()+""" 
                 修改个人信息 ************************
                ****** 请输入以下数字获取对应功能 ********
                ****** 1、修改用户名 ******
                ****** 2、修改邮箱 ******
                ****** 3、修改密码 ******
                ****** 4、修改电话 ******
                ****** 5、修改年龄 ******
                ****** 6、修改性别 ******
                ****** 7、修改地址 ******
                ****** 8、显示修改后的全部个人信息 ******
                ****** 9、退出修改 ******
                ****** 0、退出系统 ******
                """;
        if (input == -1) {
            System.out.println(string);
            input = getIntInput();
        }
        // 判断是否为 修改邮箱，然后获取 终端对应的输入
       switch (input) {
           case 1:
               // 执行修改用户名的操作
               System.out.println("请输入新的用户名");
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
               // 执行修改电话的操作
               System.out.println("请输入新的电话");
               break;
           case 5:
               // 执行修改年龄的操作
               System.out.println("请输入新的年龄");
               break;
           case 6:
               // 执行修改性别的操作
               System.out.println("请输入新的性别");
               break;
           case 7:
               // 执行修改地址的操作
               System.out.println("请输入新的地址");
               break;
           case 8:
               printUserInfo(user);
               updateUserPage(user,-1);
               break;
           case 9:
               System.out.println("退出成功！");
               List<Pet> addNewPetList = new ArrayList<>();
               UserPages.userIndex(user,addNewPetList);
           case 0:
               // 退出系统
               System.exit(200);
           default:
               System.out.println("输入无效，请重新输入");
               return null;
       }
       String s = getSwitchEmailOrStringInput(9,2,input);
       User user1 = new User();
        if (s == null){
            System.out.println("输入无效，请重新输入");
            return updateUserPage(user,-1);
        }else {

            switch (input) {
                case 1:
                    // 执行修改用户名的操作
                    //用户名不存在
                    user1.setName(s);
                 if (userTable.selectUserByName(user1) != null) {
                     System.out.println("更改无效，该用户名存在,请重新输入");
                     updateUserPage(user,1);
//                     String s1 = getStringInput();
//                     user.setName(s1);
                 }
                 else user.setName(s);
                 break;
                case 2:
                    // 执行修改邮箱的操作
                    user1.setEmail(s);
                    if (userTable.selectUserByEmail(user1) != null){
                        System.out.println("更改无效，该邮箱存在,请重新输入");
                        updateUserPage(user,2);
                    }else  user.setEmail(s);
                    break;
                case 3:
                    // 执行修改密码的操作
                    user1.setPassword(s);
                    userTable.updateUserPassword(user1);
                    System.out.println("更新成功！");
                    updateUserPage(user,-1);
                    return user;
                case 4:
                    // 执行修改电话的操作
                    if (UserService.isUserExistByNameOrEmailOrTel(user1)){
                        System.out.println("更改无效，该手机号已存在,请重新输入");
                        updateUserPage(user,3);
                    }else  user.setTel(s);
                    break;
                case 5:
                    // 执行修改年龄的操作
                    try {
                        user.setAge(Integer.parseInt(s));
                    }catch (Exception e){
                        System.out.println("输入的年龄不为整数! 请重新输入：");
                        updateUserPage(user,-1);
                    }
                    break;
                case 6:
                    // 执行修改性别的操作
                    user.setSex(s);
                    break;
                case 7:
                    // 执行修改地址的操作
                    user.setAddress(s);
                    break;
            }
            userTable.updateUser(user); // 更新语句
            System.out.println("更新成功！");
            updateUserPage(user,-1);
            return user;
        }
   }

    public void userRegisterPage() throws SQLException {
        String string = """
                ************************ 欢迎注册宠物管理系统 ************************
                ****** 请输入以下数字，获取对应功能 ********
                ****** 1、注册完整信息 ******
                ****** 2、仅注册用户名、邮箱和密码 ******
                ****** 0、退出系统 ******
                """;
        System.out.println(string);
        int input = getIntInput();
        switch (input){
            case 1:{
                // 注册完整信息
                userLogin(registerUserAllInfoPage());
                break;
            }
            case 2:{
                // 仅注册用户名、邮箱和密码
                userLogin(registerUserByNameAndEmail());
                break;
            }
            case 0:{
                // 退出系统
                System.exit(200);
                break;
            }
            default:{
                System.out.println("输入的指令有误！请重新输入");
                userRegisterPage();
                break;
            }
        }
    }
    // 注册完整信息页面
    private User registerUserAllInfoPage() throws SQLException {
        String string = """
                ************************ 欢迎注册宠物管理系统 ************************
                ****** 请输入各项信息，以空格隔开 ********
                """;
        System.out.println(string);
        printUtil("用户名,密码,邮箱,电话号码,年龄,性别,地址");
        String[] strings = getStringOrEmailInputByStringNum(7,3);
        if (strings == null){
           return registerUserAllInfoPage();
        }else {
            user = new User(strings[0],strings[1],strings[2],strings[3],Integer.parseInt(strings[4]),strings[5],strings[6]);
            //用户存在
            if (UserService.isUserExistByNameOrEmailOrTel(user)) {
                System.out.println("用户已存在，注册失败，请返回登录！");
                userLogin(user);
            }else {
                userTable.insertUser(user);
                System.out.println("注册成功!");
            }
            userLogin(user);
            return user;
        }

    }
    // 注册用户名、邮箱和密码
    private User registerUserByNameAndEmail() throws SQLException {
        String string = """
                ************************ 欢迎注册宠物管理系统 ************************
                ****** 请输入各项信息，以空格隔开 ********
                """;
        System.out.println(string);
        printUtil("用户名,邮箱,密码");
        String[] strings = getStringOrEmailInputByStringNum(3,2);
        if (strings == null){
            return registerUserByNameAndEmail();
        }else {
            //添加数据库
            user = new User(strings[0],strings[1],strings[2]);
            //用户存在
            if (UserService.isUserExistByNameOrEmailOrTel(user)) {
                System.out.println("用户已存在，注册失败，请返回登录！");
            }else {
                userTable.insertUser(user);
                System.out.println("注册成功!");
            }
            userLogin(user);
            return user;
        }


        }

    //管理员删除用户信息
    public static void deleteUserPage(Manager manager) throws SQLException {
        String string = """
                ************************ 管理员:
                """ + manager.getName() + """
                删除用户信息 ************************
                ****** 请输入要删除的用户ID ********
                """;
        System.out.println(string);
        // 打印出所有的用户信息
        managerService.printAllUsers();
        int input = getIntInput();
        User user = new User();
        user.setId(input);
        System.out.println(userTable.deleteUser(user) == 1 ? "删除成功!" : "删除失败!");
        managerService.printAllUsers();

    }
}






