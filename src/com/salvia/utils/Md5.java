package com.salvia.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5 {
    public static String encodeMd5(String input) {
        try {
            // ���� MessageDigest ����ָ��ʹ�� MD5 �㷨
            MessageDigest md = MessageDigest.getInstance("MD5");

            // �������ַ���ת��Ϊ�ֽ�����
            byte[] inputBytes = input.getBytes();

            // ʹ��ָ�����ֽ��������ժҪ
            md.update(inputBytes);

            // ��ȡժҪ���ֽ�����
            byte[] digestBytes = md.digest();

            // ���ֽ�����ת��Ϊʮ�������ַ���
            StringBuilder sb = new StringBuilder();
            for (byte b : digestBytes) {
                sb.append(String.format("%02x", b));
            }

            // ���ؼ��ܺ���ַ���
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }
}

