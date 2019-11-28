package com.dxt.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dxt.boss.service.UserInfoService;
import com.dxt.common.AppConstant;
import com.dxt.common.FtpFileUtil;
import com.dxt.common.LogHelper;
import com.dxt.common.MyBusiException;
import com.dxt.message.ReponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;

import java.io.*;

@Service("AppUserUploadUserAvatarService")
public class AppUserUploadUserAvatarService extends IBaseBusiService {
    
    private static final Logger logger = LoggerFactory.getLogger(AppUserUploadUserAvatarService.class);

    private static final String PARAM_AVATARSTR = "avatarStr";
    private static final String PARAM_SUFFIX = "suffix";

    private static final String PREFIX_BASE64STR = "data:image";
    private static final String BASE64STR = "base64,";

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private CacheManager cacheManager;
    
    @Override
    public ReponseMessage execute(String reqInfo, String source) {
        logger.debug(LogHelper._FUNC_START_());
        ReponseMessage message = new ReponseMessage();
        JSONObject jsonObject = JSON.parseObject(reqInfo);
        try {
            if (AppConstant.REQUEST_SOURCE.APP.getValue().equals(source)) {
                checkParams(jsonObject, PARAM_AVATARSTR, PARAM_SUFFIX);
            } else {
                checkParams(jsonObject, PARAM_AVATARSTR, PARAM_SUFFIX,
                        AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_PHONE);
            }
        } catch (MyBusiException e) {
            message.setMsg(AppConstant.REPONSE_CODE.BUSI_ERROR, AppConstant.REPONSE_MSG.SYS_PARAM_ERROR_MSG,
                    e.getMessage());
            return message;
        }
        String phone;
        try {
            phone = getPhoneFromSessionOrParam(jsonObject, source);
        } catch (MyBusiException e) {
            message.setMsg(AppConstant.REPONSE_CODE.SYS_ERROE, AppConstant.REPONSE_MSG.SYS_LOGIN_FIRST_MSG);
            return message;
        }

        String imgStr = jsonObject.getString(PARAM_AVATARSTR);
        logger.debug(LogHelper._FUNC_() + imgStr);
        if (imgStr.startsWith(PREFIX_BASE64STR) || imgStr.contains(BASE64STR)) {
            imgStr = imgStr.substring(imgStr.indexOf(BASE64STR) + 7);
        }
//        imgStr = "data:image/" + jsonObject.getString(PARAM_SUFFIX) + ";base64," + imgStr;
        logger.debug(LogHelper._FUNC_() + imgStr);

        String imgName = phone + "_" + System.currentTimeMillis() + "." +
                jsonObject.getString(PARAM_SUFFIX);
        String filePath = System.getProperty("catalina.home") +
                cacheManager.getSysConfigByCode(AppConstant.SYS_CONFIG_KEY.KEY_UPLOAD_FILE_DIR) + imgName;
        logger.debug(LogHelper._FUNC_() + filePath);
        // 进行base64转码
        if (!generateImage(imgStr, filePath)) {
            message.setMsg(AppConstant.REPONSE_CODE.SYS_ERROE, AppConstant.REPONSE_MSG.SYS_ERROR_MSG);
            return message;
        }
        // 如果单独服务器存放，此处需要进行tfp推送
        if (Boolean.valueOf(cacheManager.getSysConfigByCode(AppConstant.SYS_CONFIG_KEY.KEY_UPLOAD_FILE_FTP))) {
            if (!uploadFtp(filePath, imgName)) {
                message.setMsg(AppConstant.REPONSE_CODE.SYS_ERROE, AppConstant.REPONSE_MSG.SYS_ERROR_MSG);
                return message;
            }
        }
        // 进行数据库记录
        String url = cacheManager.getSysConfigByCode(AppConstant.SYS_CONFIG_KEY.KEY_UPLOAD_FILE_URL) + imgName;
        logger.debug(LogHelper._FUNC_() + url);
        if (userInfoService.setUserAvatarUrl(phone, url)) {
            message.setMsg(AppConstant.REPONSE_CODE.OK, AppConstant.REPONSE_MSG.SYS_REQUEST_OK_MSG);
            return message;
        } else {
            message.setMsg(AppConstant.REPONSE_CODE.SYS_ERROE, AppConstant.REPONSE_MSG.SYS_ERROR_MSG);
            return message;
        }
    }

    private boolean uploadFtp(String filePath, String imgName) {
        try {
            InputStream inputStream = new FileInputStream(filePath);
            return FtpFileUtil.uploadFile(imgName, inputStream,
                    cacheManager.getSysConfigByCode(AppConstant.SYS_CONFIG_KEY.KEY_UPLOAD_FILE_FTP_IP),
                    Integer.parseInt(cacheManager.getSysConfigByCode(AppConstant.SYS_CONFIG_KEY.KEY_UPLOAD_FILE_FTP_PORT)),
                    cacheManager.getSysConfigByCode(AppConstant.SYS_CONFIG_KEY.KEY_UPLOAD_FILE_FTP_NAME),
                    cacheManager.getSysConfigByCode(AppConstant.SYS_CONFIG_KEY.KEY_UPLOAD_FILE_FTP_PWD),
                    cacheManager.getSysConfigByCode(AppConstant.SYS_CONFIG_KEY.KEY_UPLOAD_FILE_FTP_BASEPATH));
        } catch (FileNotFoundException e) {
            logger.error(LogHelper._FUNC_EXCEPTION_(), e);
        }
        return false;
    }

    private boolean generateImage(String imgStr, String path) {
        BASE64Decoder decoder = new BASE64Decoder();
        OutputStream out = null;
        try {
            // 解密
            byte[] b = decoder.decodeBuffer(imgStr);
            // 处理数据
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            out = new FileOutputStream(path);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            logger.error(LogHelper._FUNC_EXCEPTION_(), e);
            return false;
        } finally {
            try {
                if (null != out) {
                    out.close();
                }
            } catch (IOException e) {
                // do nothing
            }
        }
    }

}
