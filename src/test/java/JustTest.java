import com.alibaba.fastjson.JSONObject;
import com.dxt.common.MyBase64;
import com.dxt.common.MyMD5;

public class JustTest {
    public static void main(String[] args) {
        JSONObject reqInfo = getReqInfo20004();
        JSONObject object = new JSONObject();
        object.put("appType", "web");
        object.put("busiCode", "10026");
        object.put("timestamp", System.currentTimeMillis());
        object.put("uuId", "999");
        object.put("version", "1");
        object.put("clientCode", "5053");
        object.put("reqInfo", reqInfo);
        object.put("secretKey","QXNpYWluZm9vcGVuaW50ZXJmYWNlMTAwMjZkaXhpbnRvbmc=");
        object.put("channelId","dajiayewuzhushou");
        object.put("operatorId","beijing");
        Long signTimeStamp = Long.parseLong(object.getString("timestamp")) + Long.parseLong(object.getString("clientCode"));
        String signStr = object.getString("appType") + object.getString("busiCode") + reqInfo.toString() +
                object.getString("uuId") + signTimeStamp.toString()+object.getString("secretKey");
        System.out.println(signStr);
        System.out.println(MyMD5.encryption(signStr));
        object.put("sign", MyMD5.encryption(signStr));
        String data = MyBase64.encryptBASE64(object.toString().getBytes());
        System.out.println("{\"req\":\""+data.replaceAll("\r|\n", "") + "\"}");
        System.out.println();
        byte[] byteArray = MyBase64.decryptBASE64(data);
        System.out.println(new String(byteArray));

    }


    public static JSONObject getReqInfo20004(){
        JSONObject reqInfo = new JSONObject();
        reqInfo.put("Idcardname","刘红豪");
        reqInfo.put("Idcardnumber","610202199410252816");
        reqInfo.put("Idcardaddress","陕西省铜川市王益区王家河镇南雷村149号");
        reqInfo.put("Idcardvalidity","20220803");


        return reqInfo;
    }

    public static JSONObject getReqInfo10601(){
        JSONObject reqInfo = new JSONObject();
        reqInfo.put("name","李艳超");
        reqInfo.put("certCode","410522198806124714");
        return reqInfo;
    }

    public static JSONObject getReqInfo10602(){
        JSONObject reqInfo = new JSONObject();
        reqInfo.put("imageBase64","1234123");
        reqInfo.put("cardSide","FRONT");
        reqInfo.put("plat","zz");
        return reqInfo;
    }

    public static JSONObject getReqInfo10603(){
        JSONObject reqInfo = new JSONObject();
        reqInfo.put("name","李艳超");
        reqInfo.put("certCode","lov23");
        reqInfo.put("videoBase64","123456");
        reqInfo.put("plat","zz");
        return reqInfo;
    }


}
