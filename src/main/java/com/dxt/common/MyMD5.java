package com.dxt.common;

import java.security.MessageDigest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

public class MyMD5 {
    
    private static final Logger logger = LoggerFactory.getLogger(MyMD5.class);
    
    /**
     * 小写32位加密
     * @param plain
     * @return
     */
    public static String encryption(String plain) {
        String re_md5 = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plain.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            re_md5 = buf.toString();
        } catch (Exception e) {
            logger.error(LogHelper._FUNC_EXCEPTION_(), e);
        }
        return re_md5;
    }
    
    public static void main(String[] args) {  
        JSONObject object = new JSONObject();
        object.put("appType", "android");
        object.put("busiCode", "10101");
        object.put("sign", "56210323d335960e64f78ca1d2cfb2bd");
        object.put("timestamp", "1544597533030");
        object.put("uuId", "EYVCTSO7PFG6Z9ON5929c691d9660700");
        object.put("version", "1");
        JSONObject reqInfo = new JSONObject();
        reqInfo.put("phone", "17090815752");
        reqInfo.put("password", "900122");
        reqInfo.put("imei", "EYVCTSO7PFG6Z9ON5929c691d9660700");
        reqInfo.put("pushId", "EYVCTSO7PFG6Z9ON5929c691d9660700");
        reqInfo.put("keepLogin", "1");
        object.put("reqInfo", reqInfo);
        object.put("clientCode", 300);

        Long signTimeStamp = Long.parseLong(object.getString("timestamp")) + Long.parseLong(object.getString("clientCode"));
        String signStr = object.getString("appType") + object.getString("busiCode") + reqInfo.toString() +
                object.getString("uuId") + signTimeStamp.toString();
        System.out.println("加密前：" + signStr);     
        System.out.println("加密后：" + encryption(signStr));
        
    } 
}
