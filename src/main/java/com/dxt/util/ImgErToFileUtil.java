package com.dxt.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 图片转换
 * @author liyanchao
 * 20170613
 */

public class ImgErToFileUtil {

	/**
	 * 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
	 * @param imgFile
	 * @return
	 */
	public static String getImageStr(String imgFile) { 
//		imgFile = "F:\\uploadImg\\1708011\\1470910851161.jpg";// 待处理的图片
		InputStream in = null;
		byte[] data = null;
		// 读取图片字节数组
		try {
			in = new FileInputStream(imgFile);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 对字节数组Base64编码
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);// 返回Base64编码过的字节数组字符串
	}
	
	/**
	 * 将字符串转为图片
	 * 对字节数组字符串进行Base64解码并生成图片
	 * @param imgStr
	 * @return
	 */
	public static boolean generateImage(String imgStr,String imgFile)throws Exception {
		if (imgStr == null) // 图像数据为空
			return false;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// Base64解码
			byte[] b = decoder.decodeBuffer(imgStr);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}
			// 生成jpeg图片
			String imgFilePath = imgFile;// 新生成的图片
			OutputStream out = new FileOutputStream(imgFilePath);
			out.write(b);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static void main(String[] args){
//		String imgStr = getImageStr("F:\\uploadImg\\1709909\\1493522536896.jpg");
//		System.out.print("imgStr===="+imgStr);
		String imgStr = "17090819081";
		boolean flag = false;
		try {
			flag = generateImage(imgStr,"F:\\uploadImg\\1709909\\NewImg.jpg");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			flag = false;
		}
		System.out.print("flag===="+flag);
//		String date = String.valueOf(System.currentTimeMillis());
//		String flag = date.substring(date.length()-10, date.length()-2);
//		System.out.print("date="+date+"\nflag===="+flag);
	}

}

