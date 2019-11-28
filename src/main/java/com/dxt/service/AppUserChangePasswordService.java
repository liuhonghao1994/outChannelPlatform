package com.dxt.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dxt.boss.service.UserInfoService;
import com.dxt.common.AppConstant;
import com.dxt.common.LogHelper;
import com.dxt.common.MyBusiException;
import com.dxt.message.ReponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("AppUserChangePasswordService")
public class AppUserChangePasswordService extends IBaseBusiService {
    
    private static final Logger logger = LoggerFactory.getLogger(AppUserChangePasswordService.class);

    private static final String PARAM_NEWPWD = "newPwd";
    private static final String PARAM_OLDPWD = "oldPwd";
    private static final String PARAM_VERIFYTYPE = "verifyType";
    private static final String PARAM_VERIFICATIONCODE = "verificationCode";

    public static final String PARAM_OUT_PASSWORD = "password";

    @Autowired
    private CommonVerificationCodeService commonVerificationCodeService;
    @Autowired
    private CacheManager cacheManager;
    @Autowired
    private UserInfoService userInfoService;
    
    @Override
    public ReponseMessage execute(String reqInfo, String source) {
        logger.debug(LogHelper._FUNC_START_());
        ReponseMessage message = new ReponseMessage();
        JSONObject jsonObject = JSON.parseObject(reqInfo);
        try {
            checkParams(jsonObject, AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_PHONE, PARAM_NEWPWD, PARAM_VERIFYTYPE);
        } catch (MyBusiException e) {
            message.setMsg(AppConstant.REPONSE_CODE.BUSI_ERROR, AppConstant.REPONSE_MSG.SYS_PARAM_ERROR_MSG,
                    e.getMessage());
            return message;
        }

        boolean checkRet = false;
        if (AppConstant.USER_INFO.PWD_VERIFY_TYPE_OLDPWD.equals(jsonObject.getString(PARAM_VERIFYTYPE))) {
            try {
                checkRet = checkOldPwd(jsonObject);
            } catch (MyBusiException e) {
                message.setMsg(AppConstant.REPONSE_CODE.BUSI_ERROR, e.getMessage());
                return message;
            }
        } else if (AppConstant.USER_INFO.PWD_VERIFY_TYPE_VERIFICATIONCODE.equals(jsonObject.getString(PARAM_VERIFYTYPE))) {
            try {
                checkRet = checkVerificationCode(jsonObject);
            } catch (MyBusiException e) {
                message.setMsg(AppConstant.REPONSE_CODE.BUSI_ERROR, e.getMessage());
                return message;
            }
        } else {
            logger.debug(LogHelper._FUNC_() + "验证类型未定义！");
            message.setMsg(AppConstant.REPONSE_CODE.BUSI_ERROR, AppConstant.REPONSE_MSG.SYS_PARAM_ERROR_MSG);
            return message;
        }
        if (!checkRet) {
            logger.debug(LogHelper._FUNC_() + "验证未通过！");
            message.setMsg(AppConstant.REPONSE_CODE.BUSI_ERROR, AppConstant.REPONSE_MSG.SYS_VERIFIY_ERROR_MSG);
            return message;
        }
        // 调用uip接口修改密码
        Map<String, Object> map = new HashMap<>();
        map.put(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_PHONE,
                jsonObject.getString(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_PHONE));
        map.put(PARAM_NEWPWD, jsonObject.getString(PARAM_NEWPWD));
        if (userInfoService.updateUserPassword(map)) {
            message.setMsg(AppConstant.REPONSE_CODE.OK, AppConstant.REPONSE_MSG.SYS_REQUEST_OK_MSG);
            return message;
        } else {
            message.setMsg(AppConstant.REPONSE_CODE.SYS_ERROE, AppConstant.REPONSE_MSG.SYS_ERROR_MSG);
            return message;
        }
    }

    private boolean checkOldPwd(JSONObject jsonObject) throws MyBusiException {
        if (null == jsonObject.getString(PARAM_OLDPWD) ||
                "".equals(jsonObject.getString(PARAM_OLDPWD))) {
            logger.debug(LogHelper._FUNC_() + "参数不完整！");
            throw new MyBusiException(AppConstant.REPONSE_MSG.SYS_PARAM_ERROR_MSG);
        }
        // 校验旧密码
        Map<String, Object> map = new HashMap<>();
        map.put(PARAM_OUT_PASSWORD, jsonObject.getString(PARAM_OLDPWD));
        map.put(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_PHONE,
                jsonObject.getString(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_PHONE));
        if (userInfoService.verifyUserPassword(map)) {
            return true;
        }
        return false;
    }

    private boolean checkVerificationCode(JSONObject jsonObject) throws MyBusiException {
        if (null == jsonObject.getString(PARAM_VERIFICATIONCODE) ||
                "".equals(jsonObject.getString(PARAM_VERIFICATIONCODE))) {
            logger.debug(LogHelper._FUNC_() + "参数不完整！");
            throw new MyBusiException(AppConstant.REPONSE_MSG.SYS_PARAM_ERROR_MSG);
        }
        // 验证码校验
        if (commonVerificationCodeService.checkVerificationCode(
                jsonObject.getString(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_PHONE),
                jsonObject.getString(PARAM_VERIFICATIONCODE),
                cacheManager.getSysConfigByCode(AppConstant.SYS_CONFIG_KEY.KEY_VERIFICATION_CODE_TYPE_PASSWORD))) {
            return true;
        }
        return false;
    }

}
