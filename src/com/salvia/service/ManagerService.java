package com.salvia.service;

import com.salvia.data.ManagerTable;
import com.salvia.data.PetsTable;
import com.salvia.data.UserTable;
import com.salvia.pojo.Manager;
import com.salvia.pojo.Pet;
import com.salvia.pojo.User;

import java.sql.SQLException;

import static com.salvia.utils.Md5.encodeMd5;
import static com.salvia.utils.Print.printUtil;

public class ManagerService {
    ManagerTable managerTable = new ManagerTable();
    Manager returnManager = new Manager();
    UserTable userTable = new UserTable();
    PetsTable petsTable = new PetsTable();
    boolean bool = false;
    // 查询管理员 校验管理员名（邮箱）和密码， 返回 Manager 或 null
    public Manager isManager(Manager manager) throws SQLException {
        if (manager.getName() != null){
            if (isManagerExistByName(manager)) {
                return isManagerPasswordValidatorByName(manager);
            }
        }else {
            if (isManagerExistByEmail(manager)) {
                return isManagerPasswordValidatorByEmail(manager);
            }
        }
        return null;
    }
    // 查询管理员 校验管理员名（邮箱）和密码， 返回 true 表示存在， 返回false 表示不存在
    public boolean isManagerByNameAndEmail(Manager manager) throws SQLException {
       return ManagerTable.selectManagerByName(manager) != null || ManagerTable.selectManagerByEmail(manager) != null;
    }
    //    打印管理员个人信息
  public  void printManager(){
      printUtil("ID,姓名,邮箱,创建时间,更新时间");
      printUtil(returnManager.toString());
  }

    // 打印所有用户信息
    public void printAllUsers() throws SQLException {
        int b = 0;
        for (User user:userTable.selectAllUser()) {
            if (b++ == 0) printUtil("ID,姓名,邮箱,电话,年龄,性别,地址,创建时间,更新时间");
            printUtil(user.toString());
        }
    }
    // 打印所有宠物信息
    public void printAllPets() throws SQLException {
        int b = 0;
        for (Pet pet:petsTable.selectAllPets()) {
            if (b++ == 0) printUtil("ID,姓名, 物种, 年龄, 性别, 个性, 体重, 主人编号");
            printUtil(pet.toString());
        }
    }
    // 打印 所有管理员信息
    public void printAllManagers() throws SQLException {
        int b = 0;
        for (Manager manager:managerTable.selectAllManager()) {
            if (b++ == 0)  printUtil("ID,姓名,邮箱,创建时间,更新时间");
            printUtil(manager.toString());
        }
    }


    // 查询管理员是否存在，存在返回 true,不存在返回 false
   private boolean isManagerExistByEmail(Manager manager) throws SQLException {
       return ManagerTable.selectManagerByEmail(manager) != null;
    }
    // 查询管理员是否存在，存在返回 true,不存在返回 false
    private boolean isManagerExistByName(Manager manager) throws SQLException {
        return ManagerTable.selectManagerByName(manager) != null;
    }


    // 查询管理员密码是否正确
    private Manager isManagerPasswordValidatorByEmail(Manager manager) throws SQLException {
        returnManager = ManagerTable.selectManagerByEmail(manager);
        bool = returnManager.getPassword().equalsIgnoreCase(encodeMd5(manager.getPassword()));
        System.out.println(bool ? "" : "管理员密码错误！");
        return bool ? returnManager:null;
    }
    // 查询管理员密码是否正确
    private Manager isManagerPasswordValidatorByName(Manager manager) throws SQLException {
        returnManager = managerTable.selectManagerByName(manager);
        bool = returnManager.getPassword().equalsIgnoreCase(encodeMd5(manager.getPassword()));
        System.out.println(bool ? "" : "管理员密码错误！");
        return bool ? returnManager:null;
    }

    
}
