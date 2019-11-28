package com.dxt.util;

import org.jboss.logging.Logger;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class CommonUtils {
	private static Logger aLogger = Logger.getLogger(CommonUtils.class);
	public static boolean canDo(int intCurrentLimit,int intLimit){
		boolean boolRet=false;
		if(intCurrentLimit==(intCurrentLimit&intLimit)){
			boolRet=true;
		}
		return boolRet;
	}
	/*public static ManagerEntity getManagerEntity(HttpServletRequest request){
		ManagerEntity managerEntity=null;
		try{
			if(request==null)return null;
			UserInfoBean userEntity=(UserInfoBean)request.getSession().getAttribute("userInfoBean");
			if(userEntity==null)return null;
			managerEntity=userEntity.getManageEnty();
		}catch(Exception e){
			aLogger.error("Unexpect exception:", e);
		}
		return managerEntity;
	}*/
	/**
	 * 
	 * 功能描述：实体类转换为Map
	 *
	 * @param obj
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 *
	 * @author 陈超
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws InvocationTargetException 
	 *
	 * @since 2014-11-11
	 *
	 * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
	 */
	public static Map<String,Object> entity2Map(Object obj) throws IllegalArgumentException, IllegalAccessException, SecurityException, NoSuchMethodException, InvocationTargetException{
		Class clazz = obj.getClass();
		Map<String,Object> map = new HashMap<String,Object>();
		
		Field[] fields = clazz.getDeclaredFields();//包含私有
		for(Field f : fields){
			map.put(f.getName(), 
					 clazz.getMethod("get"+f.getName().substring(0,1).toUpperCase()+f.getName().substring(1))
						.invoke(obj)
						);
		}
		return map;
	}

	/**
	 * insert or update content into database character coding turn or special
	 * character function
	 * 
	 * @param
	 * @return String
	 */
	public static String insertDatabaseCheck(String strInsert) {
		if (null != strInsert) {
			strInsert = strInsert.replace("\"", "\"\"");
			strInsert = strInsert.replace("\'", "\'\'");
			strInsert = strInsert.replace("*", "%");
		} else {
			strInsert = "";
		}
		return strInsert;
	}
	/**
	 * select content from database character coding turn or special character
	 * function
	 * 
	 * @param String
	 * @return String
	 */
	public static String outDatabaseCheck(String strOut) {
		if (null != strOut) {
			strOut = strOut.replace("\"\"", "\"");
			strOut = strOut.replace("\'\'", "\'");
			strOut = strOut.replace("%", "*");
		} else {
			strOut = "";
		}
		return strOut;
	}



	

	public static String convertDateToString(Date dtConvert) {
		String strRet="";
		try {
			if (dtConvert != null) {
				SimpleDateFormat fdate=new SimpleDateFormat("yyyy-MM-dd");
				strRet = fdate.format(dtConvert);
			}
		} catch (Exception eE) {
			aLogger.error("Unexpect exception:", eE);
		}
		return strRet;
	}
	public static String getStringForNowDate() {
		String strRet="";
		try {
			SimpleDateFormat fdate=new SimpleDateFormat("yyyy-MM-dd");
			strRet = fdate.format(new Date());
		} catch (Exception eE) {
			aLogger.error("Unexpect exception:", eE);
		}
		return strRet;
	}
	
	public static String format(Date date,String pattern){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}
	
	public static Timestamp convertStartDateToTimestamp(Date dtConvert) {
		try {
			if (dtConvert != null) {
				GregorianCalendar calDateTime = new GregorianCalendar();
				calDateTime.setTimeInMillis(dtConvert.getTime());
				calDateTime.set(GregorianCalendar.HOUR_OF_DAY, 0);
				calDateTime.set(GregorianCalendar.MINUTE, 0);
				calDateTime.set(GregorianCalendar.SECOND, 0);
				calDateTime.set(GregorianCalendar.MILLISECOND, 0);
				return new Timestamp(calDateTime.getTimeInMillis());
			}
		} catch (Exception eE) {
			aLogger.error("Unexpect exception:", eE);
		}
		return null;
	}
	
	public static Timestamp convertEndDateToTimestamp(Date dtConvert) {
		try {
			if (dtConvert != null) {
				GregorianCalendar calDateTime = new GregorianCalendar();
				calDateTime.setTimeInMillis(dtConvert.getTime());
				calDateTime.set(GregorianCalendar.HOUR_OF_DAY, 23);
				calDateTime.set(GregorianCalendar.MINUTE, 59);
				calDateTime.set(GregorianCalendar.SECOND, 59);
				calDateTime.set(GregorianCalendar.MILLISECOND, 999);
				return new Timestamp(calDateTime.getTimeInMillis());
			}
		} catch (Exception eE) {
			aLogger.error("Unexpect exception:", eE);
		}
		return null;
	}






	public static String getParamterValue(final HttpServletRequest request,
			final String strKey) {
		try {
			if (null == strKey || null == request) {
				aLogger
						.error("Invalid parameter: null == strKey || null == reques");
				return null;
			}
			String cookies = request.getParameter("cookies");
			if (cookies != null) {
				String[] cookiesArray = cookies.split(";");
				if (cookiesArray != null) {
					for (int i = 0; i < cookiesArray.length; i++) {
						String[] strAttr = cookiesArray[i].split("=");
						if (2 == strAttr.length
								&& strKey.equalsIgnoreCase(strAttr[0])) {
							return strAttr[1];
						}
					}
				}
			}
		} catch (Exception eE) {
			aLogger.error("Unexpect exception", eE);
		}
		return null;
	}

	public static String clearCookie(final HttpServletRequest request,
			final HttpServletResponse response, final String strKey) {
		String strVal = null;
		try {
			Cookie aCookies[] = request.getCookies();
			Cookie aCookie = null;
			for (int iIndex = 0; iIndex < aCookies.length; iIndex++) {
				if (strKey.compareTo(aCookies[iIndex].getName()) == 0) {
					aCookie = aCookies[iIndex];
					break;
				}
			}
			if (aCookie != null) {
				aCookie.setMaxAge(0);
				response.addCookie(aCookie);
			}
		} catch (Exception eE) {
			aLogger.error("Unexpect exception", eE);
		}
		return strVal;
	}

	
	

	/**
	 * function convert 10 to 16
	 * 
	 * @param int
	 *            iId
	 * @return String
	 */



	

	
	/**
	 * Return the context path, if the last charactor is "/", will return ""
	 * 
	 * @param request
	 * @return
	 */
	public static String changeNull(String str) {
		String strRet=null;
		try {
			if(str==null)strRet="";
			else strRet=str;
		} catch (Exception e) {
			aLogger.error("Exception occur in EscapeString(): ", e);
		}
		return strRet;
	}
	/**
	 * Return the context path, if the last charactor is "/", will return ""
	 * 
	 * @param request
	 * @return
	 */
	public static String getContextPath(HttpServletRequest request) {
		try {
			String strPath = request.getContextPath();
			if (strPath != null && (!strPath.equals(""))) {
				int iLength = strPath.length();
				if (strPath.charAt(iLength - 1) == '/') {
					strPath = strPath.substring(0, iLength - 1);
				}
				return strPath;
			}
		} catch (Exception e) {
			aLogger.error("Exception occur in EscapeString(): ", e);
		}
		return "";
	}

	/**
	 * change the '&quot;' to '"' and '\''
	 * 
	 * @param strText
	 * @return
	 */
	public static String EscapeString2(String strText) {
		try {
			StringBuffer strBuffer = new StringBuffer("");
			int n = 0;
			char ch = 0;
			int nCode = 0;
			if (strText == null || strText.length() == 0)
				return strText;

			for (n = 0; n < strText.length(); n++) {
				ch = strText.charAt(n);
				nCode = ch;
				if (nCode == '&') {
					if (n + 6 <= strText.length()
							&& strText.substring(n, n + 6).equals("&quot;")) {
						strBuffer.append('"');
						n += 5;
					} else {
						strBuffer.append(ch);

					}

				} else if (nCode == '\'')
					strBuffer.append("\\'");
				else
					strBuffer.append(ch);
			}
			return strBuffer.toString();
		} catch (Exception e) {
			aLogger.error("Exception occur in EscapeString2(): ", e);
		}
		return null;
	}
	
	public static String searchReplace(String strVersion) {
		if (null == strVersion) {
			return null;
		}
		StringBuffer sbVersion = new StringBuffer();
		strVersion = strVersion.replaceAll("/", "//");
		strVersion = strVersion.replace("\\", "\\\\");
		strVersion = strVersion.replaceAll("%", "/%");
		strVersion = strVersion.replaceAll("_", "/_");
		strVersion = strVersion.replaceAll("'", "''");
		strVersion = strVersion.replaceAll("--", "/-/-");
		sbVersion.append("'%").append(strVersion).append("%' ESCAPE '/' ");
		return sbVersion.toString();
	}

	/**
	 * when search '_' or '%' will get wrong result.Should first change to '/_'
	 * and '/%'
	 * 
	 * @param aString
	 * @return
	 */
	public static String changeForSearch(String aString) {
		String result = null;
		if(aString != null){
			result = aString.replaceAll("/", "//");
			result = result.replaceAll("%", "/%");
			result = result.replaceAll("'", "''");
			result = result.replace("\\", "\\\\\\\\");
			result = result.replaceAll("_", "/_");
			result.trim();
		}
		return result;
	}

	public static String encodeString(String strUrl) {
		String strReturnUrl = "";
		if (strUrl == null || "".equals(strUrl)) {
			return null;
		}
		try {
			// strReturnUrl=java.net.URLEncoder.encode(strUrl,"GBK").replaceAll("%3A",":").replaceAll("%2F","/");
			strReturnUrl = java.net.URLEncoder.encode(strUrl, "UTF-8");
			aLogger.debug(URLDecoder.decode(strReturnUrl, "UTF-8"));
		} catch (Exception E) {
			aLogger.error("checkUrl exception : ", E);
		}
		return strReturnUrl;
	}
	
	public static long freemem() {
		System.gc();
		
		return Runtime.getRuntime().freeMemory();
	}


	
//	public static void readExcel(String filepath,TelecomContractDao contraDao,UserInfoBean user){
//		Workbook book = null;
//		try {
//			book = Workbook.getWorkbook(new File(filepath));
//			 Sheet sheet = book.getSheet(0);
//			int row = sheet.getRows();
//			for(int r = 1;r < row; r++){
//				TelecomContractEntity model = new TelecomContractEntity();
//				model.setContractcode(sheet.getCell(0,r).getContents().trim());
//				model.setContractname(sheet.getCell(1,r).getContents().trim());
//				model.setContractclass(Integer.parseInt(sheet.getCell(2,r).getContents().trim()));
//				model.setContracttype(Integer.parseInt(sheet.getCell(3,r).getContents().trim()));
//				if(!"".equals(sheet.getCell(4,r).getContents())){
//					model.setContractmoney(Double.parseDouble(sheet.getCell(4,r).getContents().trim()));
//				}
//				if(!"".equals(sheet.getCell(5,r).getContents())){
//					model.setSubsidies(Double.parseDouble(sheet.getCell(5,r).getContents().trim()));
//				}
//				if(!"".equals(sheet.getCell(6,r).getContents())){
//					model.setBillamount(Double.parseDouble(sheet.getCell(6,r).getContents().trim()));
//				}
//				model.setPackagecode(sheet.getCell(7,r).getContents().trim());
//				model.setHyq(Integer.parseInt(sheet.getCell(8,r).getContents().trim()));
//				model.setSuitablemodel(sheet.getCell(9,r).getContents().trim());
//				contraDao.saveOrUpdate(model, user.getManageEnty());
//				
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		  
//	}
	

	public static String replaceWrongPath(String str){
		return str.replaceAll("http://123.196.123.76", "http://www.dixintong.com");
	}
	 /**
     * 
     * 功能描述：返回订单类型  0裸机 1手机号订单，2合约机单，4移动订单，8联通订单，16电信订单
     *
     * @param cont
     * @param operator  1中国移动,2中国联通,3中国电信 
     * @return
     *
     * @author 胡婷
     *
     * @since Mar 12, 2013
     *
     * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    public static Integer orderContract(Integer cont,Integer operator){
    	Integer returnCont=0;
    	switch (operator) {
		case 1:
			returnCont=cont+4;
			break;
		case 2:
			returnCont=cont+8;
			break;
		case 3:
			returnCont=cont+16;
			break;
		default:
			returnCont=0;
			break;
		}
    	return returnCont;
    }
    public static String getReqHost(HttpServletRequest request){
		String host = null;
		
		host = request.getRemoteHost();
//		System.out.println("host..."+host);
//		host = request.getRequestURI();
//		System.out.println("host..."+host);
//		host = request.getRemoteAddr();
//		System.out.println("host..."+host);
		
		return host;
	}
	/**
	 * 检查是否包含破坏sql字符串
	 * @param str
	 * @return true: 包含破坏sql字符串
	 * @author 陈超
	 * @since Nov 16, 2012 4:16:46 PM
	 * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
	 */
	public static boolean isSqlIll(String str){
		if(null==str||"".equals(str)){
			return false;
		}
		String regex = "=|<|>|,|\\.|;|'|\"|%|/|:|and|or|exec|insert|select|delete|update|count|chr|mid|master|truncate|char|declare|\\|";
		Pattern pattern = Pattern.compile(regex);   
        Matcher matcher = pattern.matcher(str);   
        return matcher.matches(); 
	}
	public static String getJssionPara(HttpServletRequest request){
		return "jsessionid="+request.getSession().getId();
	}
    
    
    /**
	 * 返回输出流到客户端
	 * @param request
	 * @param response
	 * @param msg
	 * @throws IOException
	 * @author 陈超
	 * @since Mar 18, 2013 4:54:10 PM
	 * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
	 */
	public static void writeStream(HttpServletRequest request,HttpServletResponse response,String msg) throws IOException{
		request.setCharacterEncoding("utf-8");
	    response.setContentType("application/text;charset=utf-8");
        response.setHeader("cache-control","no-cache");
        
        PrintWriter out = response.getWriter();
    	out.print(msg);
    	out.flush();
    	out.close();
	}
//	public static String produceImgP(ImageEntity imageEntity) {
//		Date date = new Date();
//		return (0!=imageEntity.getIssue_no()?imageEntity.getIssue_no()+"":CommonUtils.format(date, "yyyy-MM-dd"));
//	}
	/**
	 * 
	 * 功能描述：校验是否为空
	 *
	 * @param str
	 * @return
	 *
	 * @author 陈超
	 *
	 * @since 2013-9-4
	 *
	 * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
	 */
	public static boolean isNull(String str){
		return null==str||"".equals(str)||"null".equalsIgnoreCase(str) ? true : false;
	}/**
	 * 
	 * 功能描述：校验是否为空
	 *
	 * @param str
	 * @return
	 *
	 * @author 陈超
	 *
	 * @since 2013-9-4
	 *
	 * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
	 */
	public static boolean isNull(Date str){
		return null==str||"".equals(str) ? true : false;
	}
	/**
	 * 
	 * 功能描述：校验是否为空
	 *
	 * @param i
	 * @return
	 *
	 * @author 陈超
	 *
	 * @since 2013-9-4
	 *
	 * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
	 */
	public static boolean isNull(Integer i){
		return null==i? true : false;
	}

//	/**
//	 * 记录日志，参数没有的传null
//	 * @param userid 系统用户id
//	 * @param gameid 游戏id
//	 * @param packsid 礼包id
//	 * @param info 日志内容
//	 * @param clientuserid 玩家id
//	 * @return
//	 */
//	public static boolean saveLog(String userid,String gameid,String packsid,String info,String clientuserid){
//		LogEntity log = new LogEntity();
//		log.setClientuserid(CommonUtils.changeNull(clientuserid));
//		log.setGameid(CommonUtils.changeNull(gameid));
//		log.setInfo(CommonUtils.changeNull(info));
//		log.setPacksid(CommonUtils.changeNull(packsid));
//		log.setUserid(CommonUtils.changeNull(userid));
//		LogDao logdao = new LogDaoImpl();
//		return logdao.saveLog(log);
//	}
	/**
	 * 
	 * 功能描述：返回图片url
	 *
	 * @param src
	 * @return
	 *
	 * @author 陈超
	 *
	 * @since 2013-12-25
	 *
	 * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
	 */

	public static String qzbu0Str(String value,Integer number0){
		int count=number0-(value.length());
		String retstr="";
		for(int i=1;i<=count;i++){
			retstr+="0";
		}
		retstr+=value;
		return retstr;
	}
	
	
	/**
	* 提供精确的乘法运算。
	* @param v1 被乘数
	* @param v2 乘数
	* @return 两个参数的积
	*/
	public static double mul(double v1, double v2) {
	   BigDecimal b1 = new BigDecimal(Double.toString(v1));
	   BigDecimal b2 = new BigDecimal(Double.toString(v2));
	   return b1.multiply(b2).doubleValue();
	}

	public static boolean isNull(Double v) {
		if (null == v) {
			return true;
		} else {
			return false;
		}
	}
	
	
	/**
	 * 功能描述：返回项目访问地址
	 * @return
	 * @author 徐江
	 * @since 2014-6-3
	 * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
	 */
	public static String getPath(){
		return "http://123.196.115.86:4567/DxtDZBKAdmin/file/";
	}
	/**
	 * 
	 * 功能描述：验证空值
	 *
	 * @param arr
	 * @return
	 *
	 * @author 陈超
	 *
	 * @since 2014-11-21
	 *
	 * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
	 */
	public static boolean isNull(String[] arr) {
		return (null==arr? true : false);
	}
	
	
	
	/**
     * 删除单个文件
     * @param   sPath    被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }
    
    public static boolean checkAge(String birthday,int minAge,int maxAge) { 
		int iage = 0; 
		if (birthday != "" || birthday != null) { 
			int year = Integer.parseInt(birthday.substring(0, 4)); 
			int month = Integer.parseInt(birthday.substring(4, 6)); 
			int day = Integer.parseInt(birthday.substring(6, 8)); 
			Calendar birthDate = new GregorianCalendar(year, month, day); 
			Calendar today = Calendar.getInstance(); 

			if (today.get(Calendar.YEAR) > birthDate.get(Calendar.YEAR)) { 
				iage = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR) - 1; 
				if (today.get(Calendar.MONTH) + 1 > birthDate .get(Calendar.MONTH)) { 
					iage++; 
				} else if (today.get(Calendar.MONTH) + 1 == birthDate .get(Calendar.MONTH)) { 

					if (today.get(Calendar.DAY_OF_MONTH) >= birthDate .get(Calendar.DAY_OF_MONTH)) { 
						iage++; 
					} 
				} 
			} 
			return minAge <= iage && iage <= maxAge;
		} 
		return false; 
	}
	
	
		//验证日期格式是否为yyyymmdd
	public static boolean isValidDate(String date){
		String timeRegex4 = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})(((0[13578]|1[02])(0[1-9]|[12][0-9]|3[01]))|"+
				"((0[469]|11)(0[1-9]|[12][0-9]|30))|(02(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|"+
				"((0[48]|[2468][048]|[3579][26])00))0229)$";
		if (Pattern.matches(timeRegex4, date)){
			return true;
		}else {
			return  false;
		}

	}



	//号码格式校验
	public static boolean inValid(String number) {
		if (number.length() != 11) {
			return false;
		}
		return true;
	}


	//通过base64流判断图片大小
	/**
	 *通过图片base64流判断图片等于多少字节
	 *image 图片流
	 */
	public static Integer imageSize(String image){
		String str=image.substring(22); // 1.需要计算文件流大小，首先把头部的data:image/png;base64,（注意有逗号）去掉。
		Integer equalIndex= str.indexOf("=");//2.找到等号，把等号也去掉
		if(str.indexOf("=")>0) {
			str=str.substring(0, equalIndex);
		}
		Integer strLength=str.length();//3.原来的字符流大小，单位为字节
		Integer size=strLength-(strLength/8)*2;//4.计算后得到的文件流大小，单位为字节
		return size;
	}

}
