/**
 * HttpUtil.java 3/21/2016
 * 国政通检验接口类
 * @author liych
 */
package com.dxt.util;
import com.dxt.model.UserEntity;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.net.ssl.SSLContext;
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

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
import org.dom4j.Document;
import org.dom4j.DocumentException;

/**
 * @author Administrator
 *
 *2015-4-28
 */
public class HttpUtils {
	 private static int SocketTimeout = 30000;//3秒  
	 private static int ConnectTimeout = 30000;//3秒  
	 private static Boolean SetTimeOut = true;  
	// 目标地址
//	String url = "https://124.202.131.85:9443/oip/HttpAPIService";
	String url = "https://10.10.10.55:9443/oip/HttpAPIService";
	//String url = "https://10.10.10.83:85/oip/HttpAPIService";
	 /**
	 * 功能描述： http的post 请求，请求内容为json或xml
	 * @param url 请求url
	 * @param type content-type 类型 枚举值为xml/json
	 * @param content 请求内容
	 * @throws ParseException
	 * @throws IOException
	 * @author liyanchao
	 * @since 2016-12-16
	 */
	public String getHttpsResponse(UserEntity userEntity) throws ParseException, IOException{
		String appKey = "WXGZT00001";
		String secretKey = "WXGZT00001";
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		String transactionId = appKey + df.format(new Date())
		+ RandomStringUtils.randomNumeric(10);
		System.out.println("进入Https接口1");
		String resStr = null;
		String result = "";
		CloseableHttpClient httpclient = null;
		try {
			System.out.println("进入Https接口2");
			httpclient = createHttpClient();
			System.out.println("进入Https接口3");
			// 以post方式提交XML
			HttpPost httppost = new HttpPost(url);
			IdAuthRequestDTO request = new IdAuthRequestDTO();
			request.getHead().setAppKey(appKey);
			request.getHead().setMethod("idtag.idAuth");
			request.getHead().setReqTime(new Date());
			request.getHead().setTransactionId(transactionId);
			request.getHead().setVersion("V1.0");
			IdAuthRequestBodyDTO body = new IdAuthRequestBodyDTO();
			IdAuthReq idAuthReq = new IdAuthReq();
			idAuthReq.setMobile(CommonUtils.changeNull("17150085918"));
			idAuthReq.setName(CommonUtils.changeNull(userEntity.getIdcardname()));
			idAuthReq.setIdCode(CommonUtils.changeNull(userEntity.getIdcardnumber()));
			body.setIdAuthReq(idAuthReq);
			request.setBody(body);
			String xml = JaxbMapper.toXml(request, "UTF-8");
//			String xml = "<Root><Head><appKey>DXTTEST</appKey><method>idtag.idAuth</method><transactionId>DXTTEST201612153148064950</transactionId><reqTime>20161215174058</reqTime><version>V1.0</version></Head><Body><IdAuthReq><name>宋文慧</name><idCode>142322199501211016</idCode><mobile>17093098692</mobile></IdAuthReq></Body></Root>";
			String sign = MD5.createSign(xml, transactionId, secretKey);
			request.getHead().setSign(sign);
			xml = JaxbMapper.toXml(request, "UTF-8");

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
	private static String xml2Object(String xml) throws DocumentException{
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
	
	public static void main(String[] args) throws Exception {
		xml2Object("<Root><Head>"+
            "<actionCode>1</actionCode><transactionId>DXTTEST201612168231566684</transactionId><respTime>20161216165626</respTime><Response>"+
			"<respType>0</respType><respCode>0000</respCode>"+
			"<respDesc>成功</respDesc></Response></Head><Body><IdAuthResp>"+
		    "<name>dawei</name><idCode>410122198708134834</idCode><mobile>17090819001</mobile><birthday>19880612</birthday><age>28</age><gender>男</gender><idTagNo>20000198066</idTagNo></IdAuthResp></Body></Root>");
	}
	
}
