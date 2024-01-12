package com.salvia.utils;

import com.salvia.data.SuppliesTypeTable;


public class Print {
    static SuppliesTypeTable suppliesTypeTable = new SuppliesTypeTable();

    // 终端打印格式化 工具
    public static void printUtil(String string) {
        String[] headers = string.trim().split(",");
        int maxLength = 0;
        for (String header : headers) {
            if (header.length() > maxLength) {
                maxLength = header.length();
            }
        }
        System.out.print("+");
        for (int i = 0; i < headers.length; i++) {
            System.out.print("-".repeat(maxLength + 2) + "+");
        }
        System.out.println();
        for (String header : headers) {
            System.out.printf("| %-" + maxLength + "s ", centerAlign(header, maxLength));
        }
        System.out.println("|");

        System.out.print("+");
        for (int i = 0; i < headers.length; i++) {
            System.out.print("-".repeat(maxLength + 2) + "+");
        }
        System.out.println();
    }

    private static String centerAlign(String text, int maxLength) {
        int padding = (maxLength - text.length()) / 2;
        return " ".repeat(padding) + text + " ".repeat(padding);
    }

    //打印商品表


    }




