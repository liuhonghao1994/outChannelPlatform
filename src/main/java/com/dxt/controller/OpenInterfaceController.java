package com.dxt.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.dxt.boss.model.SecretKeyModel;
import com.dxt.common.*;
import com.dxt.message.ReponseMessage;
import com.dxt.message.AppRequestMessage;
import com.dxt.model.AppVersionBean;
import com.dxt.model.BusiCodeBean;
import com.dxt.service.CacheManager;
import com.dxt.service.IBaseBusiService;
import com.dxt.service.SecretKeyService;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

@Controller
@Scope("prototype")
@RequestMapping(value = "/api/v1", name = "openInterface")
public class OpenInterfaceController implements ApplicationContextAware  {

    ApplicationContext applicationContext;

    @Autowired
    private CacheManager cacheManager;
    @Autowired
    private SecretKeyService secretKeyService;
    private static final Logger logger = LoggerFactory.getLogger(OpenInterfaceController.class);
    private List<SecretKeyModel> allKeys;

    /**
     * @desc 对外接口访问统一入口
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/openInterface")
    @ResponseBody
    public ResponseEntity<ReponseMessage> openInterface(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        logger.debug(LogHelper._FUNC_START_());
        ReponseMessage message = new ReponseMessage();
        String resultInfos = getReqStrFromRequest(request);
        String s = resultInfos.replaceAll(" ", "+");
        String req = null;
        try {
            JSONObject jsonObject = JSON.parseObject(s);
            req = jsonObject.getString("req");
        } catch (Exception e) {
            logger.error(LogHelper._FUNC_EXCEPTION_() + LogHelper._LINE_() + e);
        }
        String repReq;
        // 获取参数
        try {
            repReq = new String (MyBase64.decryptBASE64(req), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error(LogHelper._FUNC_EXCEPTION_() + LogHelper._LINE_() + e);
            message.setMsg(AppConstant.REPONSE_CODE.BUSI_ERROR, "请求参数有误，请检查参数是否正确！");
            return new ResponseEntity<ReponseMessage>(message, HttpStatus.OK);
        }
        repReq = repReq.replaceAll("\\\\", "");
        logger.info(LogHelper._FUNC_() + "repReq:" + URLDecoder.decode(repReq,"UTF-8"));

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
            message = iBaseBusiService.execute(reqestMessage.getReqInfo(), AppConstant.REQUEST_SOURCE.OPEN.getValue());
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
        StringBuffer sb = new StringBuffer() ;
        BufferedReader br = null;
        try {
            InputStream is = request.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
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
            resultInfos = URLDecoder.decode(str.substring(str.indexOf("?")+1, str.length()),"UTF-8");
            logger.debug(resultInfos);
        } catch (Exception e) {
            logger.error(LogHelper._FUNC_EXCEPTION_(), e);
        } finally {
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
    private void checkParam(AppRequestMessage msg) throws MyBusiException {
        logger.debug(LogHelper._FUNC_START_());
        // 判断参数是否完整
        if (!msg.isOpenParmsvalide()) {
            logger.debug("请求参数不完整");
            throw new MyBusiException(AppConstant.REPONSE_MSG.SYS_PARAM_ERROR_MSG);
        }
        //校验用户是否有权限

        if (!secretKeyService.getAllKeys().contains(msg.getSecretKey())){
            throw new MyBusiException(AppConstant.REPONSE_MSG.SYS_PARAM_ERROR_SECRET_KEY);
        }


        // 判断加密是否正确
        // appType + busiCode + reqInfo + uuId + (timestamp + clientCode)+secretKey
        Long signTimestamp =  Long.parseLong(msg.getTimestamp()) + Long.parseLong(msg.getClientCode());
        String signStr = msg.getAppType() + msg.getBusiCode() + msg.getReqInfo() + msg.getUuId() + signTimestamp+msg.getSecretKey();
        String sign = MyMD5.encryption(signStr);
        logger.debug(LogHelper._FUNC_() + "sign from app:" + msg.getSign());
        logger.debug(LogHelper._FUNC_() + "sign from server:" + sign);
        if (!sign.equals(msg.getSign())) {
            throw new MyBusiException(AppConstant.REPONSE_MSG.SYS_PARAM_ERROR_MSG);
        }

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
