package com.salvia;

import com.salvia.data.*;
import com.salvia.page.ManagerPages;
import com.salvia.page.UserPages;
import com.salvia.pojo.Manager;
import com.salvia.pojo.User;
import org.junit.Test;

import java.sql.SQLException;

import static com.salvia.utils.Input.getIntInput;


public class SystemMain {
    static UserPages userPages = new UserPages();
    static ManagerPages managerPages = new ManagerPages();

    static ManagerTable managerTable = new ManagerTable();
    static UserTable userTable = new UserTable();
    static PetsTable petsTable = new PetsTable();
    static  PetSuppliesTable petSuppliesTable = new PetSuppliesTable();
   static SuppliesTypeTable suppliesTypeTable = new SuppliesTypeTable();

    public static void main(String[] args) throws SQLException {
        systemInit();
        systemIndex();
    }


//    public static void main(String[] args) throws SQLException {
//        User user = new User("yuntian","Luyitong5201314","1578770127@qq.com");
//        Pet pet = new Pet(123,"hh","ss",3,"m","s",10.1,123);
//        String string = """
//                依次输入新增信息，用空格隔开
//                1、输入用户名（必须）
//                2、输入密码（必须）
//                3、输入邮箱（必须）
//                4、....
//                """;
//        System.out.println(string);
//        Scanner scanner =  new Scanner(System.in);
//        String choice = scanner.nextLine();
//        String[] strings = choice.split(" ");
//        user.setName(strings[0]);
//        user.setPassword(strings[1]);
//        user.setEmail(strings[2]);
//        System.out.println("正在新增...");
//        System.out.println(userTable.insertUser(user) == 1 ? "新增成功!" : "新增失败!");
//        System.out.println("新增用户信息如下:");
//        System.out.println(userTable.selectUserByEmail(user));
//        System.out.println("现有注册用户如下：");
//        System.out.println(userTable.selectAllUser());
//    }
    public static void systemInit() throws SQLException {
        System.out.println("正在初始化中......");
        try {
            System.out.println("正在尝试连接到 MySQL 数据库......");
            managerTable.createManagerTable();
            userTable.createUserTable();
            petsTable.createPetsTable();
            petSuppliesTable.createPetSuppliesTable();
            suppliesTypeTable.createSuppliesTypeTable();
            System.out.println("成功连接到 MySQL 数据库！");
            System.out.println("初始化完毕！");
        }catch (Exception e){
            System.out.println("连接到 MySQL 数据库失败！");
            System.out.println("初始化失败！");
        }

    }
    public static void systemIndex() throws SQLException {

        String string = """
                ************************ 欢迎访问宠物管理系统 ************************
                ****** 请输入以下数字，获取对应功能 ********
                ****** 1、登录宠物管理系统 ******
                ****** 2、注册宠物管理系统 ******
                ****** 3、管理员登录 ******
                ****** 0、退出 ******
                """;
        System.out.println(string);
        int input = getIntInput();
        User user = new User();
        Manager manager = new Manager();
        switch (input){
            case 1:{
                // 登录宠物管理系统
                userPages.userLogin(user);
                break;
            }
            case 2:{
                // 注册宠物管理系统
                userPages.userRegisterPage();
                break;
            }
            case 3:{
                // 管理员登录
                managerPages.managerLogin(manager);
                break;
            }
            case 0:{
                // 退出系统
                System.exit(200);
                break;
            }
            default:{
                System.out.println("输入的指令有误！请重新输入");
                systemIndex();
                break;
            }
        }
    }




    @Test
    public void test1(){
        String string = "private String name;\n" +
                "    private String password;\n" +
                "    private String email;\n" +
                "    private String tel;\n" +
                "    private int age;\n" +
                "    private String sex;\n" +
                "    private String address;";
        string.trim().replace("private","").replace("String","").replace("int","");
    }
    @Test
    public void test2(){
        for (int i = 0; i < 8; i++) {
            System.out.print("strings["+i+"],");
        }

    }
}