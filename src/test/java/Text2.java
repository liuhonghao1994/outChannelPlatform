import com.dxt.model.UserEntity;
import com.dxt.util.HttpUtils;

import java.io.IOException;

public class Text2 {
    public static void main(String[] args) {
        HttpUtils httpUtils=new HttpUtils();
        UserEntity userEntity=new UserEntity();
        userEntity.setIdcardaddress("北京市天通苑西二区二号楼6单元601");
        userEntity.setIdcardname("刘红豪");
        userEntity.setIdcardnumber("610202199410252816");
        userEntity.setIdcardvalidity("20221025");
        try {
            String httpsResponse = httpUtils.getHttpsResponse(userEntity);
            System.out.println(httpsResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
