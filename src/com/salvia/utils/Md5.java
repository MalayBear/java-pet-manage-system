package com.salvia.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5 {
    public static String encodeMd5(String input) {
        try {
            // 创建 MessageDigest 对象，指定使用 MD5 算法
            MessageDigest md = MessageDigest.getInstance("MD5");

            // 将输入字符串转换为字节数组
            byte[] inputBytes = input.getBytes();

            // 使用指定的字节数组更新摘要
            md.update(inputBytes);

            // 获取摘要的字节数组
            byte[] digestBytes = md.digest();

            // 将字节数组转换为十六进制字符串
            StringBuilder sb = new StringBuilder();
            for (byte b : digestBytes) {
                sb.append(String.format("%02x", b));
            }

            // 返回加密后的字符串
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }
}

