package com.dxt.common;

import java.util.Random;

public class MyRandomUtil {

    private static final String BASE_STR = "zxcvbnmlkjhgfdsaqwertyuiopQWERTYUIOPASDFGHJKLZXCVBNM1234567890";

    public static String getRandomString(int length){
        //由Random生成随机数
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        //长度为几就循环几次
        for(int i=0; i<length; ++i){
            //产生0-61的数字
            int number=random.nextInt(62);
            //将产生的数字通过length次承载到sb中
            sb.append(BASE_STR.charAt(number));
        }
        //将承载的字符转换成字符串
        return sb.toString();
    }


}
