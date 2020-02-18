package com.edu.zzti.common.util;

import java.security.MessageDigest;

public class MD5Auth {

    public static void main(String[] args) {
        //客户端所需要传输的数据加上key 一起传给MD5Encode 生成MD5
        //客户端传输数据的时候不传key
        //服务端用服务端手上的key和接收到的数据再生成一个MD5来对比 以此保证数据正确
        String str= "1";
        String clientkey = "sw23klkj3nm23";
        String clientstr = str + clientkey;
        //生成MD5
        String result = MD5Auth.MD5Encode(clientstr, "UTF-8").toUpperCase();
        System.out.println(result);

        //用接收到的数据生成一个新的MD5 与原来的MD5对比
        String checkstr = "1";
        String serverkey = "sw23klkj3nm23";
        String serverstr = checkstr + serverkey;
        System.out.print("验证密码是否正确:");    
        if(validatePassword(result, serverstr, "UTF-8")) {    
            System.out.println("正确");    
        }    
        else {    
            System.out.println("错误");    
        } 

    }

    //通过获取参数并且生成新的MD5校验码 然后和 获取到的MD5校验码 对比 以此验证传过来的数据是否被修改
    public static boolean validatePassword(String origin, String inputString, String charsetname){    
        if(origin.equals(MD5Encode(inputString,charsetname).toUpperCase())){    
            return true;    
        } else{    
            return false;    
        }    
    } 

    //通过获取数据来生成MD5验证码   charsetname为"UTF-8"编码比较好
    public static String MD5Encode(String origin, String charsetname) {
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestString = md.digest(resultString.getBytes(charsetname));
            if (charsetname == null || "".equals(charsetname))
                resultString = byteArrayToHexString(digestString);
            else
                resultString = byteArrayToHexString(digestString);
        } catch (Exception exception) {
        }
        return resultString;
    }

    //生成MD5序列的逻辑
    private static String byteArrayToHexString(byte b[]) {
        StringBuffer resultSb = new StringBuffer();
        String byteToHex = null;
        for (int i = 0; i < b.length; i++){
            byteToHex = byteToHexString(b[i]);
            resultSb.append(byteToHex);
        }
        return resultSb.toString();
    }

    //逻辑中每一位数据 在这里对应转换成两个字符
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n += 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    //转换字符的集合 这里可以根据自己喜好改一下顺序 达到改变生成的MD5的结果的作用 慢慢试 估计可能可以清楚顺序的
    private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

}