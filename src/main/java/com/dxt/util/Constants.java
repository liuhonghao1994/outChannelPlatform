package com.dxt.util;

import java.text.SimpleDateFormat;

public class Constants {
	public final static Object lockObject = new Object();
	
	public final static Object lockObjectCallback = new Object();


	public final static SimpleDateFormat sdfYYYY_MM_DD = new SimpleDateFormat(
			"yyyy-MM-dd");
	public final static SimpleDateFormat sdfYYYYMMDD = new SimpleDateFormat(
			"yyyyMMdd");

	public final static String BODY_START = "<Body>";
	public final static String BODY_END = "</Body>";
	public final static String BODY_EMPTY = "<Body/>";

	public final static String SVC_CONT_START = "<SvcCont>";
	public final static String SVC_CONT_END = "</SvcCont>";
	public final static String SVC_CONT_EMPTY = "<SvcCont/>";

	// 接口名称
	public final static String METHOD_SYS_GETSECRETKEY = "sys.getSecretKey";

	public static boolean CALLING_IDTAG_SERVICE = false;
}
