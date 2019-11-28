package com.dxt.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dxt.boss.model.PoiModle;
import com.dxt.common.AppConstant;
import com.dxt.common.MyBusiException;
import com.dxt.dao.InsertMesDao;
import com.dxt.message.ReponseMessage;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;



/**
 * @author Liuhh
 * @creat 2019-08-16 13:47
 */
@Service("InsertMesService")
public class InsertMesService extends IBaseBusiService {

    private  String str;
    private  int num=0;

    @Autowired
    private InsertMesDao insertMesDao;
    private static JSONObject jsonObject;
    private CloseableHttpClient httpClient;

    @Override
    public ReponseMessage execute(String reqInfo, String source) {
        ReponseMessage reponseMessage=new ReponseMessage();
        jsonObject = JSON.parseObject(reqInfo);

        try {
            httpClient = HttpClients.createDefault();
            get("http://api.map.baidu.com/place/v2/search");
            reponseMessage.setMsg(AppConstant.REPONSE_CODE.OK,AppConstant.REPONSE_MSG.SYS_REQUEST_OK_MSG);
        } catch (Exception e) {
            reponseMessage.setMsg(AppConstant.REPONSE_CODE.BUSI_ERROR,
                    e.getMessage());
            return  reponseMessage;
        }
        return reponseMessage;
    }




     public void  get(String url) throws ClientProtocolException, IOException {

        //创建一个默认连接

        List<NameValuePair> params = Lists.newArrayList();
        params.add(new BasicNameValuePair("query", "手机维修店"));
        params.add(new BasicNameValuePair("tag", "手机维修出售"));
        params.add(new BasicNameValuePair("region", "北京"));
        params.add(new BasicNameValuePair("output", "json"));
        params.add(new BasicNameValuePair("ak", "Ypxni8A78ItcqVsgHR5Du4TT1my0DAva"));
        params.add(new BasicNameValuePair("page_size",20+""));
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
            System.out.println(results.size()+"页数"+num+1);
            if((num)*20<=poiModle.getTotal()){
                for (int i = 0; i < results.size(); i++) {
                    //插入数据库
                    insertMesDao.insertMapInfo(results.get(i).getName(),results.get(i).getAddress(),results.get(i).getProvince(),results.get(i).getCity(),results.get(i).getArea(),results.get(i).getTelephone());
                }

                num++;

                get("http://api.map.baidu.com/place/v2/search");

            }else {
                System.out.println("本地市号码已经插入完成");

            }


        }
    }
}
