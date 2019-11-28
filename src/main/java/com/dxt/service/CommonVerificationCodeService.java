package com.dxt.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dxt.boss.model.BaseUserInfo;
import com.dxt.boss.service.UserInfoService;
import com.dxt.common.AppConstant;
import com.dxt.common.LogHelper;
import com.dxt.common.MyBusiException;
import com.dxt.dao.CommonDao;
import com.dxt.message.ReponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service("CommonVerificationCodeService")
public class CommonVerificationCodeService extends IBaseBusiService {
    
    private static final Logger logger = LoggerFactory.getLogger(CommonVerificationCodeService.class);

    private static final String CONFIG_KEY_PREFIX = "CONTENT_VERIFICATIONCODE_TYPE_";
    private static final String CONTENT_VERIFICATIONCODE = "VERIFICATIONCODE";

    private static final String PARAM_TYPE = "type";

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private CacheManager cacheManager;
    @Autowired
    private CommonDao commonDao;

    /**
     * 发送验证码
     * @param reqInfo
     * @return
     */
    @Override
    public ReponseMessage execute(String reqInfo, String source) {
        logger.debug(LogHelper._FUNC_START_());
        ReponseMessage message = new ReponseMessage();
        JSONObject jsonObject = JSON.parseObject(reqInfo);

        try {
            checkParams(jsonObject, AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_PHONE, PARAM_TYPE);
        } catch (MyBusiException e) {
            message.setMsg(AppConstant.REPONSE_CODE.BUSI_ERROR, AppConstant.REPONSE_MSG.SYS_PARAM_ERROR_MSG,
                    e.getMessage());
            return message;
        }

        // 对type枚举值进行校验（是否有对应的模板内容）
        String msgContent = cacheManager.getSysConfigByCode(CONFIG_KEY_PREFIX + jsonObject.getString(PARAM_TYPE));
        if (null == msgContent) {
            logger.debug(LogHelper._FUNC_() + "参数不正确！找不到对应的短信模板！");
            message.setMsg(AppConstant.REPONSE_CODE.BUSI_ERROR, AppConstant.REPONSE_MSG.SYS_PARAM_ERROR_MSG);
            return message;
        }

        Map<String, Object> map = new HashMap<>();
        map.put(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_PHONE, jsonObject.getString(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_PHONE));
        // 查询号码是否正常，只有正常情况下才进行发送
        BaseUserInfo baseUserInfo = userInfoService.getBaseUserInfo(map);
        if (null == baseUserInfo) {
            logger.error(LogHelper._FUNC_() + "号码查询不到！");
            message.setMsg(AppConstant.REPONSE_CODE.BUSI_ERROR, AppConstant.REPONSE_MSG.SYS_PARAM_ERROR_MSG);
            return message;
        }
        if (!cacheManager.getSysConfigByCode(AppConstant.SYS_CONFIG_KEY.KEY_USER_STATE_OK).equals(baseUserInfo.getState())
         || !cacheManager.getSysConfigByCode(AppConstant.SYS_CONFIG_KEY.KEY_USER_OSSTATUS_OK).equals(baseUserInfo.getOsStatus())) {
            logger.info(LogHelper._FUNC_() + "号码状态不正常，无法发送！");
            message.setMsg(AppConstant.REPONSE_CODE.BUSI_ERROR, AppConstant.REPONSE_MSG.SYS_PHONE_STATE_ERROR_MSG);
            return message;
        }
        try {
            // 生成验证码
            String code = String.valueOf(new Random().nextInt(899999) + 100000);
            commonDao.insertVerificationCode(jsonObject.getString(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_PHONE), code, jsonObject.getString(PARAM_TYPE));
            // 进行验证码发送
            msgContent = msgContent.replaceAll(CONTENT_VERIFICATIONCODE, code);
            logger.debug(LogHelper._FUNC_EXCEPTION_() + "msgContent:" + msgContent);
            commonDao.insertVerificationCodeMsg(jsonObject.getString(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_PHONE), msgContent);
            message.setMsg(AppConstant.REPONSE_CODE.OK, AppConstant.REPONSE_MSG.SYS_REQUEST_OK_MSG);
            return message;
        } catch (Exception e) {
            logger.error(LogHelper._FUNC_EXCEPTION_(), e);
            message.setMsg(AppConstant.REPONSE_CODE.SYS_ERROE, AppConstant.REPONSE_MSG.SYS_ERROR_MSG);
            return message;
        }
    }

    public boolean checkVerificationCode(String phone, String code, String type) {
        try {
            long effectiveTime = Long.parseLong(cacheManager.getSysConfigByCode(
                    AppConstant.SYS_CONFIG_KEY.KEY_VERIFICATION_CODE_EFFECTIVE_TIME));
            String retPhone = commonDao.checkVerificationCode(phone, code, type, effectiveTime);
            if (null != retPhone) {
                return true;
            }
        } catch (Exception e) {
            logger.error(LogHelper._FUNC_EXCEPTION_(), e);
        }
        return false;
    }



}
