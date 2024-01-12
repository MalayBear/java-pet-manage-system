package com.salvia.page;

import com.salvia.data.PetsTable;
import com.salvia.pojo.Manager;
import com.salvia.pojo.Pet;
import com.salvia.pojo.User;
import com.salvia.service.ManagerService;
import com.salvia.service.PetsService;
import com.salvia.utils.Print;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.salvia.page.UserPages.userIndex;
import static com.salvia.utils.Input.*;

public class PetsPages {
    static PetsTable petsTable = new PetsTable();
    PetsService petsService = new PetsService();
    static ManagerService managerService = new ManagerService();
    List<Pet> addNewPetList = new ArrayList<>();

    Pet pet =new Pet();
    // 用户新增宠物页面
    // addNewPetList 存储新插入的 pet 信息
    public List<Pet> addUsersPetPage(User user, List<Pet> addNewPetList) throws SQLException {
        String string = """
            ************************ 用户:""" + user.getName() + """
            新增个人宠物 ************************
            ****** 请输入以下内容，以空格隔开 ********
            ****** 宠物的 名字 ******
            """
            + "****** " + "宠物的 物种" + " ******" + "\n"
            + "****** " + "宠物的 年龄" + " ******" + "\n"
            + "****** " + "宠物的 性别" + " ******" + "\n"
            + "****** " + "宠物的 个性" + " ******" + "\n"
            + "****** " + "宠物的 体重" + " ******" + "\n";

        System.out.println(string);
        Print.printUtil("宠物名字,物种,年龄,性别,个性,体重/kg");
        String[] strings = getStringInputByStringNum(6);
        Pet pet = new Pet();
        if (strings == null) {
            addUsersPetPage(user,addNewPetList);
        }else {
            pet = new Pet(strings[0],strings[1],Integer.parseInt(strings[2]),strings[3],strings[4],Double.parseDouble(strings[5]),user.getId());
            petsTable.insertPet(pet);
            addNewPetList.add(petsTable.selectPetByName(pet));
            petsService.printAddPet(addNewPetList);
            System.out.println("寄养新宠物成功！");
        }
        return addNewPetList;
    }

    // 用户删除宠物页面
    public void deleteUsersPetPage(User user,Pet pet) throws SQLException {
        String string = """
            ************************ 用户:""" + user.getName() + """
                    删除个人宠物 ************************
                         ****** 请输入要删除的宠物ID ********
          
            """;
        System.out.println(string);
        // 打印出所有的宠物信息
        petsService.printUserAllPets(petsTable.selectUsersPet(user));
        if (petsService.isUsersPet(user,pet)) {
            // 校验 ID 对应的宠物是否存在，和 ID 对应的主人是否为当前登录用户
            petsTable.deletePet(pet);
            System.out.println("删除宠物成功！");
        }else {
            System.out.println("查找不到对应的宠物");
            userIndex(user,addNewPetList);
        }

    }
    // 用户更改宠物信息页面
    public void updateUsersPetPage(User user) throws SQLException {
        String string = """
                ************************ 用户:
                """ + user.getName() + """
                更改个人宠物 ************************
                ****** 请输入要更改的宠物ID ********
                """;
        System.out.println(string);
        petsService.printUserAllPets(petsTable.selectUsersPet(user));
        // 校验 ID 对应的宠物是否存在，和 ID 对应的主人是否为当前登录用户
        if (petsService.isUsersPet(user,pet)) {
            updateUsersPet(user,pet);
        } else {
            System.out.println("查找不到对应的宠物");
            updateUsersPetPage(user);
        }
    }
        public void updateUsersPet (User user,Pet pet) throws SQLException {
        //直接进行修改，会影响到原始对象pet
            pet = petsTable.selectById(pet);
          String string = "************************用户:" + user.getName() +
                    "修改宠物 " + pet.getName() + " 的信息\n" +
                    """
                            ****** 请输入以下数字获取对应功能 ********
                            ****** 1、修改宠物名字 ******
                            ****** 2、修改宠物种类 ******
                            ****** 3、修改宠物年龄 ******
                            ****** 4、修改宠物性别 ******
                            ****** 5、修改宠物性格 ******
                            ****** 6、修改宠物体重 ******
                            ****** 7、退出修改 ******
                            ****** 0、退出系统 ******
                            """;
            System.out.println(string);
            int input = getIntInput();
                // 哪里的问题更新语句 这个的更新语句吗是的呀
            switch (input) {
                case 1:
                    // 修改宠物名字
                    System.out.println("请输入宠物名字");
                    pet.setName(getStringInput());
                    break;
                case 2:
                    // 修改宠物种类
                    System.out.println("请输入宠物种类");
                    pet.setSpecies(getStringInput());
                    break;
                case 3:
                    // 修改宠物年龄
                    System.out.println("请输入宠物年龄");
                    pet.setAge(Integer.parseInt(Objects.requireNonNull(getStringInput())));
                    break;
                case 4:
                    // 修改宠物性别
                    System.out.println("请输入宠物性别");
                    pet.setGender(getStringInput());
                    break;
                case 5:
                    // 修改宠物性格
                    System.out.println("请输入宠物性格");
                    pet.setPersonality(getStringInput());
                    break;
                case 6:
                    // 修改宠物体重
                    System.out.println("请输入宠物体重");
                    pet.setWeight(Double.parseDouble(Objects.requireNonNull(getStringInput())));
                    break;
                case 7:
                    System.out.println("退出成功！");
                    userIndex(user,addNewPetList);
                    break;
                case 0:
                    // 退出系统
                    System.exit(200);
                default:
                    System.out.println("输入无效，请重新输入");
            }
            // 将新的Pet对象插入到数据库中
            petsTable.updatePet(pet);
            System.out.println("更新成功！");
            petsService.managerPrintPetById(pet);
            updateUsersPet(user,pet);

    }
    // 管理员删除宠物信息
    public static void deletePetPage(Manager manager) throws SQLException {
        String string = """
                ************************ 管理员:
                """ + manager.getName() + """
                删除宠物信息 ************************
                ****** 请输入要删除的宠物ID ********
                """;
        System.out.println(string);
        // 打印出所有的宠物信息
        managerService.printAllPets();
        int input = getIntInput();
        Pet pet = new Pet();
        pet.setId(input);
        System.out.println(petsTable.deletePet(pet) == 1 ? "删除成功!" : "删除失败!");
        managerService.printAllPets();
    }
}
