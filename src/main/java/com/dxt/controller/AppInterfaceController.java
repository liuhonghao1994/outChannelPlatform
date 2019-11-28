package com.dxt.controller;

import java.io.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.dxt.service.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.dxt.common.AppConstant;
import com.dxt.service.IBaseBusiService;
import com.dxt.common.LogHelper;
import com.dxt.common.MyBase64;
import com.dxt.common.MyBusiException;
import com.dxt.common.MyMD5;
import com.dxt.message.ReponseMessage;
import com.dxt.message.AppRequestMessage;
import com.dxt.model.BusiCodeBean;
import com.dxt.model.AppVersionBean;
import com.dxt.service.CacheManager;

@Controller
@Scope("prototype")
@RequestMapping(value = "/api/v1", name = "appInterface")
public class AppInterfaceController  implements ApplicationContextAware  {

    ApplicationContext applicationContext;

    @Autowired
    private CacheManager cacheManager;
    @Autowired
    private SessionManager sessionManager;
    
    private static final Logger logger = LoggerFactory.getLogger(AppInterfaceController.class);
    
    /**
     * @desc APP访问统一入口.
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/appInterface")
    @ResponseBody
    public ResponseEntity<ReponseMessage> appInterface(HttpServletRequest request, HttpServletResponse response) {
        logger.debug(LogHelper._FUNC_START_());
        //创建消息返回实体
        ReponseMessage message = new ReponseMessage();
        //输入流包括请求头和请求体
        //获取输入的body
        String resultInfos = getReqStrFromRequest(request);
        //获取到req key值后面的
        String req = null;
        try {
            JSONObject jsonObject = JSON.parseObject(resultInfos);
            req = jsonObject.getString("req");
        } catch (Exception e) {
            logger.error(LogHelper._FUNC_EXCEPTION_() + LogHelper._LINE_() + e);
        }
        String repReq;
        // 获取参数
        try {
            //解码base64
            repReq = new String (MyBase64.decryptBASE64(req), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error(LogHelper._FUNC_EXCEPTION_() + LogHelper._LINE_() + e);
            message.setMsg(AppConstant.REPONSE_CODE.BUSI_ERROR, "请求参数有误，请检查参数是否正确！");
            return new ResponseEntity<ReponseMessage>(message, HttpStatus.OK);
        }
//        repReq = repReq.replaceAll("\\\\", "");
        logger.info(LogHelper._FUNC_() + "repReq:" + repReq);

        // 校验参数
        AppRequestMessage reqestMessage = null;
        BusiCodeBean busiCodeBean = null;
        try {
            reqestMessage = JSON.parseObject(repReq, new TypeReference<AppRequestMessage>() {}, Feature.OrderedField);
            busiCodeBean = getCodeBeanByCode(reqestMessage.getBusiCode());
            // 校验参数
            checkParam(reqestMessage);
        } catch (MyBusiException e) {
            logger.error(LogHelper._FUNC_EXCEPTION_() + e.getMessage());
            message.setMsg(AppConstant.REPONSE_CODE.BUSI_ERROR, e.getMessage());
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(LogHelper._FUNC_EXCEPTION_() + LogHelper._LINE_() + e);
            message.setMsg(AppConstant.REPONSE_CODE.SYS_ERROE, AppConstant.REPONSE_MSG.SYS_ERROR_MSG);
            return new ResponseEntity<>(message, HttpStatus.OK);
        }

        try {
            // 校验用户权限，此处由于返回code不同，需单独处理
            checkUser(request, busiCodeBean);
        } catch (MyBusiException e) {
            logger.error(LogHelper._FUNC_EXCEPTION_() + e.getMessage());
            message.setMsg(AppConstant.REPONSE_CODE.LOGIN_WARNING, e.getMessage());
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(LogHelper._FUNC_EXCEPTION_() + LogHelper._LINE_() + e);
            message.setMsg(AppConstant.REPONSE_CODE.SYS_ERROE, AppConstant.REPONSE_MSG.SYS_ERROR_MSG);
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        
        // 定位业务处理服务
        IBaseBusiService iBaseBusiService = null;
        try {
            iBaseBusiService = (IBaseBusiService) applicationContext.getBean(busiCodeBean.getBeanName());
            iBaseBusiService.setRequest(request);
            iBaseBusiService.setResponse(response);
            iBaseBusiService.setMsg(reqestMessage);
        } catch (Exception e) {
            logger.error(LogHelper._FUNC_EXCEPTION_() + e);
            message.setMsg(AppConstant.REPONSE_CODE.SYS_ERROE, AppConstant.REPONSE_MSG.SYS_ERROR_MSG);
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        // 进行业务处理
        try {
            message = iBaseBusiService.execute(reqestMessage.getReqInfo(), AppConstant.REQUEST_SOURCE.APP.getValue());
        } catch (Exception e) {
            logger.error(LogHelper._FUNC_EXCEPTION_(), e);
            message.setMsg(AppConstant.REPONSE_CODE.SYS_ERROE, AppConstant.REPONSE_MSG.SYS_ERROR_MSG);
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        logger.info(LogHelper._FUNC_() + message.toString());
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /**
     * @desc 获取入参
     * @param request
     * @return
     */
    private String getReqStrFromRequest(HttpServletRequest request){
        logger.debug(LogHelper._FUNC_START_());
        String resultInfos = null;
        StringBuffer sb = new StringBuffer();
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            is = request.getInputStream();
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            String s;
            while((s=br.readLine())!=null){ 
                sb.append(s) ; 
            }
            String str = sb.toString();
            logger.debug(str);
            // 防止用get传递参数
            if("".equals(str)) {
                if(request.getQueryString() != null) { 
                    str = request.getRequestURL() + "?" + request.getQueryString(); 
                } else { 
                    str = request.getRequestURL().toString(); 
                } 
            }
            resultInfos = java.net.URLDecoder.decode(str.substring(str.indexOf("?")+1, str.length()),"UTF-8");
            logger.debug(resultInfos);
        } catch (Exception e) {
            logger.error(LogHelper._FUNC_EXCEPTION_(), e);
        } finally {
            try {
                if (null != is) {
                    is.close();
                }
            } catch (IOException e) {// do nothing
            }
            try {
                if (null != isr) {
                    isr.close();
                }
            } catch (IOException e) {// do nothing
            }
            try {
                if (null != br) {
                    br.close();
                }
            } catch (IOException e) {// do nothing
            }
        }
        return resultInfos;
    }
    
    /**
     * @desc 获得BusiCodeBean
     * @param busiCode
     * @return
     */
    private BusiCodeBean getCodeBeanByCode(String busiCode) throws MyBusiException {
        logger.debug(LogHelper._FUNC_START_());
        //根据请求的busicode获取得到busicode实体对象
        BusiCodeBean busiCodeBean = cacheManager.getBusiByCode(busiCode);
        if (null == busiCodeBean) {
            logger.error("根据【" + busiCode + "】没有获取到对应的bean！");
            throw new MyBusiException("请求的busiCode有误，请检查参数是否正确！");
        }
        return busiCodeBean;
    }
    
    /**
     * @desc 校验参数合法性
     * @param msg
     * @return
     * @throws MyBusiException
     */
    private boolean checkParam(AppRequestMessage msg) throws MyBusiException {
        logger.debug(LogHelper._FUNC_START_());
        // 判断参数是否完整
        if (!msg.isAllInfoValid()) {
            logger.debug("请求参数不完整");
            throw new MyBusiException(AppConstant.REPONSE_MSG.SYS_PARAM_ERROR_MSG);
        }
        // 判断时间戳有效性
        // 测试模式不进行此项校验
        if (!Boolean.valueOf(cacheManager.getSysConfigByCode(AppConstant.SYS_CONFIG_KEY.KEY_TEST_MODE))) {
            Long requestTimestamp =  Long.valueOf(msg.getTimestamp());
            Long serverTimestamp = System.currentTimeMillis();
            if (Math.abs(serverTimestamp - requestTimestamp) >
                    Long.parseLong(cacheManager.getSysConfigByCode(AppConstant.SYS_CONFIG_KEY.KEY_TIMESTAMP_MAX_DIFF))) {
                logger.debug("时间有效性不符");
                throw new MyBusiException(AppConstant.REPONSE_MSG.SYS_PARAM_ERROR_MSG);
            }
        }
        // 获取版本信息接口不需要校验
        if (cacheManager.getSysConfigByCode(AppConstant.SYS_CONFIG_KEY.KEY_BUSICODE_GETVERSION).equals(msg.getBusiCode())) {
            logger.debug("获取版本信息接口不需要校验");
            return true;
        }
        // 判断版本是否需要升级(双层保险)
        AppVersionBean lastVersion = cacheManager.getLastVersionBean(msg.getAppType());
        if (AppConstant.APP_CONSTANT.VERSION_FORCE_UPDATE.equals(lastVersion.getUpdateType())) {
            if (null == msg.getVersion() || Integer.parseInt(lastVersion.getVersionCode()) > Integer.parseInt(msg.getVersion())) {
                logger.debug("当前版本太旧，需要升级！");
                throw new MyBusiException("当前版本太旧，已经无法使用，请升级最新版本[" + lastVersion.getVersionName() + "]!");
            }
        }
        AppVersionBean forceVersion = cacheManager.getForceVersionBean(msg.getAppType());
        if (null == msg.getVersion() || Integer.parseInt(forceVersion.getVersionCode()) > Integer.parseInt(msg.getVersion())) {
            logger.debug("当前版本太旧，需要升级！");
            throw new MyBusiException("当前版本太旧，已经无法使用，请升级最新版本[" + lastVersion.getVersionName() + "]!");
        }

        // 上传头像跳过校验
        if (msg.getBusiCode().equals("10104")) {
            return true;
        }

        // 判断加密是否正确
        // appType + busiCode + reqInfo + uuId + (timestamp + clientCode)
        Long signTimestamp =  Long.parseLong(msg.getTimestamp()) + Long.parseLong(msg.getClientCode());
        String signStr = msg.getAppType() + msg.getBusiCode() + msg.getReqInfo() + msg.getUuId() + signTimestamp;
        String sign = MyMD5.encryption(signStr);
        logger.debug(LogHelper._FUNC_() + "sign from app:" + msg.getSign());
        logger.debug(LogHelper._FUNC_() + "sign from server:" + sign);
        if (!sign.equals(msg.getSign())) {
            throw new MyBusiException(AppConstant.REPONSE_MSG.SYS_PARAM_ERROR_MSG);
        }
        return true;
    }
    
    /**
     * @desc 校验用户权限
     * @param request
     * @return
     * @throws MyBusiException
     */
    private boolean checkUser(HttpServletRequest request, BusiCodeBean busiCodeBean)
            throws MyBusiException {
        logger.debug(LogHelper._FUNC_START_());
        try {
            return sessionManager.checkAppUserAuthorization(request, busiCodeBean);
        } catch (MyBusiException e) {
            throw new MyBusiException(e.getMessage());
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
    
}
