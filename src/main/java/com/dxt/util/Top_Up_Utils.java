package com.dxt.util;

import com.dxt.model.TopUpEntity;
import com.dxt.model.UserEntity;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * HttpUtil.java 3/28/2019
 * 充值平台接口
 * @author LiuHH
 */

public class Top_Up_Utils {
        String url = "https://124.202.131.74:8899/plat/HttpAPIService";
        public String getHttpsResponse(TopUpEntity topUpEntity) throws ParseException, IOException{
            String appKey = "DXTHNFGS001";
            String secretKey = "DXTHNFGS001";
            DateFormat df = new SimpleDateFormat("yyyyMMdd");
            String transactionId = appKey + df.format(new Date())
                    + RandomStringUtils.randomNumeric(10);
            String resStr = null;
            String result = "";
            CloseableHttpClient httpclient = null;
            try {
                httpclient = createHttpClient();
                // 以post方式提交XML
                HttpPost httppost = new HttpPost(url);
                //请求头头部设置
                TopUpRequest request = new TopUpRequest();
                request.getHead().setAppKey(appKey);
                request.getHead().setMethod("recharge.recharge");
                request.getHead().setReqTime(new Date());
                request.getHead().setVersion("V1.0");
                request.getHead().setTransactionId(transactionId);

                //请求体内容设置
                TopUpRequestBody body = new TopUpRequestBody();
                TopUpReq topUpReq = new TopUpReq();
                topUpReq.setReqSerial(topUpEntity.getReqSerial());
                topUpReq.setDestCode(topUpEntity.getDestCode());
                topUpReq.setFeeAmount(topUpEntity.getFeeAmount());
                body.setTopUpReq(topUpReq);
                request.setBody(body);
                String xml = JaxbMapper.toXml(request, "UTF-8");
                String sign = MD5.createSign(xml, transactionId, secretKey);
                request.getHead().setSign(sign);
                xml = JaxbMapper.toXml(request, "UTF-8");
                System.out
                        .println("请求内容 " + xml);
                StringEntity reqEntity = new StringEntity(xml, "UTF-8");
                reqEntity.setContentType("application/xml; charset=UTF-8");
                httppost.setEntity(reqEntity);
                System.out
                        .println("executing request " + httppost.getRequestLine());
                CloseableHttpResponse response = httpclient.execute(httppost);
                try {
                    System.out.println("----------------------------------------");
                    System.out.println(response.getStatusLine());
                    HttpEntity resEntity = response.getEntity();
                    if (resEntity != null) {
                        System.out.println("Response content length: "
                                + resEntity.getContentLength());
                    }
                    byte[] buf = new byte[1024];
                    int len = resEntity.getContent().read(buf);
                    resStr = new String(buf, 0, len);
                    EntityUtils.consume(resEntity);
                    System.out.println("resStr==="+resStr);
                    result = xml2Object(resStr);
                } finally {
                    response.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }finally{
                httpclient.close();
            }
            return result;
        }

        private CloseableHttpClient createHttpClient() {
            if (url.startsWith("https")) {
                try {
                    SSLContext sslContext = new SSLContextBuilder()
                            .loadTrustMaterial(null, new TrustStrategy() {
                                // 信任所有
                                @Override
                                public boolean isTrusted(
                                        java.security.cert.X509Certificate[] arg0,
                                        String arg1) throws CertificateException {
                                    return true;
                                }
                            }).build();
                    SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                            sslContext,
                            SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

                    CloseableHttpClient client = HttpClients.custom().setSSLSocketFactory(sslsf).build();
                    System.out.println("createHttpClient-over");
                    return client;
                } catch (KeyManagementException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (KeyStoreException e) {
                    e.printStackTrace();
                }
            }
            return HttpClients.createDefault();
        }

        @SuppressWarnings({ "unused", "unchecked" })
        private static String xml2Object(String xml) throws DocumentException {
            Document doc = null;
            String respCode = "";
//	       try {
            int index = xml.indexOf("<respCode>");
            respCode = xml.substring(index+10,index+14);

            System.out.println("respCode==="+respCode);
            // 下面的是通过解析xml字符串的
//	            doc = DocumentHelper.parseText(xml); // 将字符串转为XML
//	            Element rootElt = doc.getRootElement(); // 获取根节点
//	            System.out.println("根节点：" + rootElt.getName()); // 拿到根节点的名称
//	            Element headElt = rootElt.element("Head");
//	            Element responseElt = headElt.element("Response");
//	            respCode = responseElt.elementTextTrim("respCode").toString();
//	            Element bodyElt = rootElt.element("Body");
//	            Element idAuthRespElt = bodyElt.element("IdAuthResp");
//	            String name = idAuthRespElt.elementTextTrim("name").toString();
//	            String idCode = idAuthRespElt.elementTextTrim("idCode").toString();
//	            String mobile = idAuthRespElt.elementTextTrim("mobile").toString();
//	            String birthday = idAuthRespElt.elementTextTrim("birthday").toString();
//	        } catch (DocumentException e) {
//	            e.printStackTrace();
//
//	        } catch (Exception e) {
//	            e.printStackTrace();
//
//	        }
            return respCode;
        }
}
