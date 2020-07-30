package com.hanshu.utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;


public class MD5Utils {

    private static final char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static final char[] hexDigitsLower = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};


    private static String MD5Lower(String plainText) {
        try {

            MessageDigest md = MessageDigest.getInstance("MD5");

            md.update(plainText.getBytes());

            return new BigInteger(1, md.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }


    private static String MD5Upper(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            md.update(plainText.getBytes());

            byte[] mdResult = md.digest();
            int j = mdResult.length;
            char[] str = new char[j * 2];
            int k = 0;
            for (byte byte0 : mdResult) {
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    private static String MD5Lower(String plainText, String saltValue) {
        try {

            MessageDigest md = MessageDigest.getInstance("MD5");

            md.update(plainText.getBytes());
            md.update(saltValue.getBytes());

            return new BigInteger(1, md.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }


    private static String MD5Upper(String plainText, String saltValue) {
        try {

            MessageDigest md = MessageDigest.getInstance("MD5");


            md.update(plainText.getBytes());
            md.update(saltValue.getBytes());


            byte[] mdResult = md.digest();

            int j = mdResult.length;
            char[] str = new char[j * 2];
            int k = 0;
            for (byte byte0 : mdResult) {
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    public static String MD5(String plainText) {
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");

            mdTemp.update(plainText.getBytes(StandardCharsets.UTF_8));

            byte[] md = mdTemp.digest();
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;
            for (byte byte0 : md) {
                str[k++] = hexDigitsLower[byte0 >>> 4 & 0xf];
                str[k++] = hexDigitsLower[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }


    public static boolean valid(String text, String md5) {
        return md5.equals(MD5(text)) || md5.equals(Objects.requireNonNull(MD5(text)).toUpperCase());
    }


    public static void main(String[] args) {
        String plainText = "admin";
        String saltValue = "admin123";

        System.out.println(MD5Lower(plainText));
        System.out.println(MD5Upper(plainText));
        System.out.println(MD5Lower(plainText, saltValue));
        System.out.println(MD5Upper(plainText, saltValue));
        System.out.println(MD5(plainText));
        System.out.println("=====result======");
        System.out.println(valid(plainText, Objects.requireNonNull(MD5(plainText))));

    }
}
