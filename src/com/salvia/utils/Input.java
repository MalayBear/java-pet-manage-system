package com.salvia.utils;

import java.util.Scanner;

import static com.salvia.utils.EmailValidator.isValidEmail;

public class Input {

    //    获取终端 输入字符串 的函数
    public static String getStringInput() {
        Scanner scanner = new Scanner(System.in);
        String string = scanner.nextLine().trim();
        if (string.isEmpty() || string.isBlank()) {
            System.out.println("输入的内容有误!");
            return null;
        }
        return string;
    }

    // 校验 输入的字符串 是否为邮箱
    public static String getEmailInput() {
        String email = getStringInput();
        boolean bool = isValidEmail(email);
        System.out.println(bool ? "" : "输入的邮箱格式有误！");
        return bool ? email : null;
    }

    // 校验需要 邮箱 还是 字符串输入
    public static String getSwitchEmailOrStringInput(int range, int emailNum, int input) {
        // 判断输入的 input 数字存不存在
        boolean bool = true;
        for (int i = 1; i <= range; i++) {
            if (i == input) {
                bool = false;
                break;
            }
        }
        if (bool) return null;
        // 根据 邮箱的位置进行 匹配
        if (input == emailNum) {
            return getEmailInput();
        } else {
            return getStringInput();
        }
    }

    // 校验 输入的字段数是否 符合要求正确
    public static String[] getStringInputByStringNum(int num) {
        String string = getStringInput();
        boolean bool = string != null;
        String[] strings = new String[0];
        if (bool) strings = string.trim().split(" ");
        if (!bool || strings.length != num) {
            System.out.println("输入的字段不符合要求!");
            return null;
        }
        return strings;
    }

    // 校验 输入的字段数是否 符合要求正确, 和输入哪些值 停止输入，
    public static String[] getStringInputByStringNumAndReferExist(int num,int breakNum){
        String string = getStringInput();
        boolean bool = string != null;
        String[] strings = new String[0];
        //将整数转化字符串
        if (bool) strings = string.trim().split(" ");
        try{
            if (bool && Integer.parseInt(string.trim()) == breakNum  ) {
                return new String[0];
            }
        }catch (Exception ignored){
        }
        if (!bool || strings.length != num) {
            System.out.println("输入的字段不符合要求!");
            return null;
        }
        return strings;
    }
    // 输入返回数字 停止输入，
    public static String[] getStringInputByReferExist(String s,int breakNum){
        boolean bool = s != null;
        String[] strings = new String[0];
        //将整数转化字符串
        if (bool) strings = s.trim().split(" ");
        try{
            if (bool && Integer.parseInt(s.trim()) == breakNum  ) {
                return new String[0];
            }
        }catch (Exception ignored){
        }
        return strings;
    }
    //同时校验 字段数 邮箱  设置返回值
public  static String[] getStringInputByStringNumAndEmailAndReferExist (int num, int emailNum, int breakNum){
        String string = getStringInput();
        boolean bool = string != null;
        String [] strings = new String[0];
//        Manager manager = new Manager();
    //将整数转化字符串
    if (bool) strings = string.trim().split(" ");
    try{
        if (bool && Integer.parseInt(string.trim()) == breakNum  ) {
            return new String[0];
        }
    }catch (Exception ignored){
//        System.out.println("输入的字段不符合要求");
    }
    if (!bool || strings.length != num) {
        System.out.println("输入的字段不符合要求!");
        return null;
    }
    if ( !isValidEmail(strings[emailNum-1])){
        System.out.println("输入的邮箱格式有误!");
        return null;
    }

//    if (manager.getName().equals(strings[nameNum - 1]))
//    {
//        System.out.println("输入的姓名有误");
//        return null;
//    }

        return strings;
}

    // 校验 输入的字段数是否 符合要求正确, 对邮箱位置的字符串进行校验
    public static String[] getStringOrEmailInputByStringNum(int num,int emailNum){
        String[] strings = getStringInputByStringNum(num);
        if (strings == null ){
            return null;
        }
       if ( !isValidEmail(strings[emailNum-1])){
           System.out.println("输入的邮箱格式有误!");
            return null;
        }
        return strings;
    }
    //    获取终端 输入整数 的函数
    public static int getIntInput(){
        Scanner scanner = new Scanner(System.in);
        int num;
        try {
            num = scanner.nextInt();
        }catch (Exception e){
            System.out.println("输入的整数有误");
            return -1;
        }
        return num;
    }
}
