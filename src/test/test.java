package test;

import com.salvia.data.*;
import com.salvia.page.*;
import com.salvia.pojo.*;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//package test;
//
//import com.salvia.data.SuppliesTypeTable;
//import org.junit.Test;
//
//import java.sql.SQLException;
//
public class test {
    UserTable userTable = new UserTable();
    ManagerPages managerPages = new ManagerPages();
    PetSuppliesPages petSuppliesPages = new PetSuppliesPages();
    UserPages userPages = new UserPages();
    PetsTable petsTable = new PetsTable();
    PetsPages petsPages = new PetsPages();
    PetSuppliesTable petSuppliesTable = new PetSuppliesTable();
    ManagerTable managerTable = new ManagerTable();
   List<SuppliesType> addNewPetSuppliesTypeList = new ArrayList<>();
   SuppliesType suppliesType = new SuppliesType();
   SuppliesTypeTable suppliesTypeTable = new SuppliesTypeTable();
    @Test
    public void test1() throws SQLException {
//        User user = new User();
//        user.setName("luyitong");
//        user = userTable.selectUserByName(user);
////        user.setEmail("123456@qq.com");
//        updateUserPage(user,-1);
        Manager manager = new Manager();
        manager.setName("Salvia");
        manager = ManagerTable.selectManagerByName(manager);
       managerPages.updateManagerPage(manager,-1);
    }
    @Test
    public void test2() throws SQLException {
        User user = new User();
        user.setName("luyitong");
        user = userTable.selectUserByName(user);
        petSuppliesPages.addUserSelectSuppliesPage(user);

    }
    @Test
    public void test3() throws SQLException {
        User user = new User();
        user.setName("luyitong");
        user = userTable.selectUserByName(user);
        List<Pet> addNewPetList = new ArrayList<>();
        UserPages.userIndex(user,addNewPetList);

    }


    @Test
    public void test5() throws SQLException {
        Manager manager = new Manager();
        manager.setName("Luyitong");
        manager = ManagerTable.selectManagerByName(manager);
        managerPages.addManagerPage(manager);
    }
    @Test
    public void test6(){
        System.out.println(Arrays.toString(new String[0]));
    }
    @Test
    public void test7() throws SQLException {
       User user = new User();
       user.setName("yt");
       user = userTable.selectUserByName(user);
       PetSupply petSupply = new PetSupply();
       petSupply.setId(38);
       PetSuppliesTable.updatePetSupplyByStatus(petSupply);
       petSuppliesPages.UserSelectPayPage(user);


    }
    @Test
    public void test8() throws SQLException {
        User user = new User();
        user.setName("yt");
        user = userTable.selectUserByName(user);
        petsPages.updateUsersPetPage(user);

    }
    @Test
    public void test9() throws SQLException {
        User user = new User();
        user.setName("yt");
        user = userTable.selectUserByName(user);
        PetSupply petSupply = new PetSupply();
        petSupply.setId(11);
        petSuppliesTable.selectPetSupplyByUser_Id(petSupply);
        petSuppliesPages.deleteUserSelectSuppliesPage(user);
    }
    @Test
    public void test10() throws SQLException {
        User user = new User();
        user.setName("yt");
        user = userTable.selectUserByName(user);
        PetSupply petSupply = new PetSupply();
       petSupply.setPetSuppliesType_id(25);
       petSuppliesTable.selectPetSupplyByPetSuppliesTypeId(petSupply);
      petSuppliesPages.addUserSelectSuppliesPage(user);
    }
@Test
    public void test11() throws SQLException {
        User user = new User();
        user.setName("yt");
        user = userTable.selectUserByName(user);
        petSuppliesPages.userSelectAllPetSuppliesByStatusIsPay(user);
    }
@Test
    public void test12() throws SQLException {
    User user = new User();
    user.setName("yt");
    user = userTable.selectUserByName(user);
   petSuppliesPages.deleteUserSelectSuppliesPage(user);
}
@Test
    public void test13() throws SQLException {
    User user = new User();
    user.setName("yt");
    user = userTable.selectUserByName(user);
    petSuppliesPages.userSelectAllPetSuppliesByStatus(user);
}
@Test

public void test14() throws SQLException {
    User user = new User();
    user.setName("yt");
    user = userTable.selectUserByName(user);
    Pet pet = new Pet();
    pet.setId(1);
    petsTable.selectById(pet);
    petsPages.updateUsersPetPage(user);
}
    @Test

    public void test19() throws SQLException {
        User user = new User();
        user.setName("yt");
        user = userTable.selectUserByName(user);
        petSuppliesPages.UserSelectPayPage(user);
    }
@Test
    public void test15() throws SQLException {
        Manager manager =new Manager();
        manager.setName("salvia");
        manager = managerTable.selectManagerByName(manager);
        managerPages.managerIndex(manager,addNewPetSuppliesTypeList);

}
@Test
public void test16() throws SQLException {
    List<SuppliesType> addNewPetSuppliesTypeList = new ArrayList<>();
    Manager manager =new Manager();
    manager.setName("salvia");
    manager = ManagerTable.selectManagerByName(manager);
//    suppliesType.setName("kaka");
//    suppliesType.setCategory("123");
//    suppliesType.setPrice(10.0);
//    addNewPetSuppliesTypeList.add(suppliesTypeTable.selectSuppliesTypeByName(suppliesType));
    PetSuppliesTypePages.addPetSuppliesTypePage(manager,addNewPetSuppliesTypeList);

}
    @Test
    public void test17() throws SQLException {
        Manager manager =new Manager();
        manager.setName("salvia");
        manager = ManagerTable.selectManagerByName(manager);
//    suppliesType.setName("kaka");
//    suppliesType.setCategory("123");
//    suppliesType.setPrice(10.0);
//    addNewPetSuppliesTypeList.add(suppliesTypeTable.selectSuppliesTypeByName(suppliesType));
        PetSuppliesTypePages.updateSuppliesTypeTablePage(manager);

    }
    @Test
    public void test18() throws SQLException {
        Manager manager =new Manager();
        manager.setName("salvia");
        manager = ManagerTable.selectManagerByName(manager);

        managerPages.managerSelectAllUserPetSupplyPage(manager);

    }
    @Test
    public void test20() throws SQLException {
        Manager manager =new Manager();
        manager.setName("salvia");
        manager = ManagerTable.selectManagerByName(manager);
        PetSuppliesTypePages.deletePetSupplyTypePage(manager);

    }
}


//
//}
