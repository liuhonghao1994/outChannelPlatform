package com.dxt.controller;

import com.dxt.common.AppConstant;
import com.dxt.common.LogHelper;
import com.dxt.message.ReponseMessage;
import com.dxt.service.CacheManager;
import com.dxt.service.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Scope("prototype")
@RequestMapping(value = "/api/v1", name = "commonInterface")
public class CommonController {

    @Autowired
    private CacheManager cacheManager;
    @Autowired
    private SessionManager sessionManager;

    private static final Logger logger = LoggerFactory.getLogger(CommonController.class);

    @RequestMapping(value = "/refreshCache/{verificationCode}")
    @ResponseBody
    public ResponseEntity<ReponseMessage> refreshCache(@PathVariable String verificationCode) {
        logger.debug(LogHelper._FUNC_START_());
        ReponseMessage message = new ReponseMessage();
        // 先判断verificationCode是否匹配，不匹配的认为不合法，直接返回错误
        if (null == verificationCode || !verificationCode.equals(cacheManager.getSysConfigByCode(
                AppConstant.SYS_CONFIG_KEY.KEY_REFRESH_CACHE_VERIFICATION_CODE))) {
            message.setMsg(AppConstant.REPONSE_CODE.SYS_ERROE, AppConstant.REPONSE_MSG.SYS_ERROR_MSG);
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        // 更新verificationCode
        cacheManager.updateSysConfigValue(AppConstant.SYS_CONFIG_KEY.KEY_REFRESH_CACHE_VERIFICATION_CODE, null);
        if (cacheManager.initCache()) {
            message.setMsg(AppConstant.REPONSE_CODE.OK, AppConstant.REPONSE_MSG.SYS_REQUEST_OK_MSG);
        } else {
            message.setMsg(AppConstant.REPONSE_CODE.SYS_ERROE, AppConstant.REPONSE_MSG.SYS_ERROR_MSG);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @RequestMapping(value = "/clearSessionCache/{verificationCode}")
    @ResponseBody
    public ResponseEntity<ReponseMessage> clearSessionCache(@PathVariable String verificationCode) {
        logger.debug(LogHelper._FUNC_START_());
        ReponseMessage message = new ReponseMessage();
        // 先判断verificationCode是否匹配，不匹配的认为不合法，直接返回错误
        if (null == verificationCode || !verificationCode.equals(cacheManager.getSysConfigByCode(
                AppConstant.SYS_CONFIG_KEY.KEY_CLEAR_SESSION_VERIFICATION_CODE))) {
            message.setMsg(AppConstant.REPONSE_CODE.SYS_ERROE, AppConstant.REPONSE_MSG.SYS_ERROR_MSG);
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        // 更新verificationCode
        cacheManager.updateSysConfigValue(AppConstant.SYS_CONFIG_KEY.KEY_CLEAR_SESSION_VERIFICATION_CODE, null);
        if (sessionManager.clearSessionMap()) {
            message.setMsg(AppConstant.REPONSE_CODE.OK, AppConstant.REPONSE_MSG.SYS_REQUEST_OK_MSG);
        } else {
            message.setMsg(AppConstant.REPONSE_CODE.SYS_ERROE, AppConstant.REPONSE_MSG.SYS_ERROR_MSG);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
