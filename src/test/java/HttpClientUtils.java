import com.alibaba.fastjson.JSON;
import com.dxt.boss.model.PoiModle;
import com.google.common.collect.Lists;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;

//ak Ypxni8A78ItcqVsgHR5Du4TT1my0DAva
public class HttpClientUtils  {

    private static String str;
    private static int num=1;

    public static void main(String[] args) throws ClientProtocolException, IOException {
        get("http://api.map.baidu.com/place/v2/search");
       // get("http://api.map.baidu.com/place/v2/detail?uid=fa33dfb8114540eea8f729fb&output=json&scope=2&ak=Ypxni8A78ItcqVsgHR5Du4TT1my0DAva");
    }

    static public void  get(String url) throws ClientProtocolException, IOException{

        //创建一个默认连接
        CloseableHttpClient httpClient = HttpClients.createDefault();
        List<NameValuePair> params = Lists.newArrayList();
        params.add(new BasicNameValuePair("query", "手机维修店"));
        params.add(new BasicNameValuePair("tag", "手机维修出售"));
        params.add(new BasicNameValuePair("region", "北京"));
        params.add(new BasicNameValuePair("output", "json"));
        params.add(new BasicNameValuePair("ak", "Ypxni8A78ItcqVsgHR5Du4TT1my0DAva"));
        params.add(new BasicNameValuePair("page_num", num +""));
//转换为键值对
        str = EntityUtils.toString(new UrlEncodedFormEntity(params, Consts.UTF_8));

        HttpGet httpGet = new HttpGet(url+"?"+str);
        CloseableHttpResponse response = httpClient.execute(httpGet);

        int statusCode = response.getStatusLine().getStatusCode();
        if(statusCode==200){
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity,"UTF-8");
            PoiModle poiModle = JSON.parseObject(result, PoiModle.class);
            List<PoiModle.ResultsBean> results = poiModle.getResults();

           /* if(num*10<poiModle.getTotal()){
                //插入数据库
                //----
                num++;
                get("http://api.map.baidu.com/place/v2/search");

            }else {
                System.out.println("本地市号码已经插入完成");
            }*/

            System.out.println(result);
        }
    }
}
