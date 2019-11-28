package com.dxt.util;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * md5
 * 
 * @author liyanchao
 * 
 */
public class MD5 {

	private static final Logger logger = LoggerFactory.getLogger(MD5.class);

	public static String createSign(String xml, String transcationId,
			String secretKey) {
		int pos1 = xml.indexOf(Constants.BODY_START);
		int pos2 = xml.indexOf(Constants.BODY_END);
		String bizCont = "";
		if (pos1 == -1 || pos2 == -1) {
			pos1 = xml.indexOf(Constants.BODY_EMPTY);
			if (pos1 != -1) {
				bizCont = Constants.BODY_EMPTY;
			}
		} else {
			bizCont = xml.substring(pos1, pos2 + Constants.BODY_END.length());
		}
		String sign = transcationId + bizCont + secretKey;
		logger.debug(sign);
		String ret = null;
		try {
			ret = Encodes
					.encodeHex(Digests.md5(sign.getBytes("UTF-8"), null, 1));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		// System.out.println(ret);
		return ret;

	}

	public static String createEopSign(String xml, String transcationId,
			String secretKey) {
		int pos1 = xml.indexOf(Constants.SVC_CONT_START);
		int pos2 = xml.indexOf(Constants.SVC_CONT_END);
		String bizCont = "";
		if (pos1 == -1 || pos2 == -1) {
			pos1 = xml.indexOf(Constants.SVC_CONT_EMPTY);
			if (pos1 != -1) {
				bizCont = Constants.SVC_CONT_EMPTY;
			}
		} else {
			bizCont = xml.substring(pos1,
					pos2 + Constants.SVC_CONT_END.length());
		}
		String sign = transcationId + bizCont + secretKey;
		logger.debug(sign);
		String ret = null;
		try {
			ret = Encodes
					.encodeHex(Digests.md5(sign.getBytes("UTF-8"), null, 1));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static String encode(String input) {
		logger.debug(input);
		String ret = null;
		try {
			ret = Encodes.encodeHex(Digests.md5(input.getBytes("UTF-8"), null,
					1));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static void main(String[] args) {
		// String str =
		// "DXT201409167390028675<Body><RechargeQueryReq><qryType>1</qryType><destCode>17001013218</destCode><reqSerial>11111111111</reqSerial><destType>2</destType><startTime>20140916230608</startTime><endTime>20140916230608</endTime><status>成功</status></RechargeQueryReq></Body>DXT";
		//String str = "123456迪信通";
		String str = "DBOSS00001201504148519814294<Body><IdAuthReq><name>张三</name><idCode>12313131321</idCode><mobile>123123</mobile></IdAuthReq></Body>23456AbABa";
		System.out.println(encode(str));

	}
}
