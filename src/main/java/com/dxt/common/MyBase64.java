package com.dxt.common;

import java.io.IOException;

import com.alibaba.fastjson.JSONObject;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


@SuppressWarnings("restriction")
public class MyBase64 {

    /**
     * BASE64解密
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptBASE64(String key) {  
        try {
            return (new BASE64Decoder()).decodeBuffer(key);
        } catch (IOException e) {
            e.printStackTrace();
        }  
        return null;
    }

    /**
     * BASE64加密
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptBASE64(byte[] key) {
        return (new BASE64Encoder()).encodeBuffer(key);  
    }

    public static void main(String[] args) {
       /* JSONObject object = new JSONObject();*/
       /* object.put("appType", "android");
        object.put("busiCode", "10208");
        object.put("sign", "56210323d335960e64f78ca1d2cfb2bd");
        object.put("timestamp", "1540546033044");
        object.put("uuId", "EYVCTSO7PFG6Z9ON5929c691d9660700");
        object.put("version", "1");
        JSONObject reqInfo = new JSONObject();
        reqInfo.put("strStartDate", "20180601");
        reqInfo.put("strEndDate", "20180901");
        object.put("reqInfo", reqInfo);
        object.put("clientCode", "300");
        System.out.println();
        System.out.println();
        String data = encryptBASE64(object.toString().getBytes());     
        System.out.println("{\"req\":\""+data.replaceAll("\r|\n", "") + "\"}");
        System.out.println();
        System.out.println();
        byte[] byteArray = decryptBASE64(data);
        System.out.println("解密后："+new String(byteArray));

        System.out.println("http://localhost:8080/api/v1/appInterface?req=" + data.replaceAll("\r|\n", ""));

        String str = "eyJhcHBUeXBlIjoiYW5kcm9pZCIsImNsaWVudENvZGUiOjMwMCwiYnVzaUNvZGUiOiIxMDAwMiIsInJlcUluZm8iOiJ7XCJ0eXBlXCI6XCIwXCIsXCJ2ZXJzaW9uQ29kZVwiOlwiXCJ9Iiwic2lnbiI6IjQyYTc1MDk1NGE4NDFiMzk0MjY0MDlhMWE5M2QzY2JiIiwidXVJZCI6IkVZVkNUU083UEZHNlo5T041OTI5YzY5MWQ5NjYwNzAwIiwidmVyc2lvbiI6IjEiLCJ0aW1lc3RhbXAiOjE1NDA1NDUzMTE2Mzd9";
*/

        System.out.println(new String(encryptBASE64("Asiainfoopeninterface10026dixintong".getBytes())));
       /* System.out.println(new String(decryptBASE64(str)));*/
    }

}
