package com.dxt.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dxt.common.AppConstant;
import com.dxt.common.LogHelper;
import com.dxt.dao.UploadInfoBeanDao;
import com.dxt.message.ReponseMessage;
import com.dxt.message.AppRequestMessage;
import com.dxt.model.UploadInfoBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SysCommonUploadInfoService")
public class SysCommonUploadInfoService {
    
    private static final Logger logger = LoggerFactory.getLogger(SysCommonUploadInfoService.class);

    private static final String PARAM_VERSIONCODE = "versionCode";
    private static final String PARAM_INFO = "info";

    @Autowired
    private UploadInfoBeanDao uploadInfoBeanDao;
    
    public ReponseMessage execute(String reqInfo, AppRequestMessage msg, String uploadType) {
        logger.debug(LogHelper._FUNC_START_());
        ReponseMessage message = new ReponseMessage();
        JSONObject jsonObject = JSON.parseObject(reqInfo);
        String versionCode = msg.getVersion();
        if (null != jsonObject.getString(PARAM_VERSIONCODE) && !"".equals(jsonObject.getString(PARAM_VERSIONCODE))) {
            versionCode = jsonObject.getString(PARAM_VERSIONCODE);
        }
        if (null == jsonObject.getString(PARAM_INFO) || "".equals(jsonObject.getString(PARAM_INFO))) {
            message.setMsg(AppConstant.REPONSE_CODE.BUSI_ERROR, AppConstant.REPONSE_MSG.SYS_PARAM_ERROR_MSG);
            return message;
        }
        try {
            UploadInfoBean info = new UploadInfoBean();
            info.setAppType(msg.getAppType());
            info.setInfoType(uploadType);
            info.setVersionCode(versionCode);
            info.setContent(jsonObject.getString(PARAM_INFO));
            uploadInfoBeanDao.insert(info);
        } catch (Exception e) {
            logger.error(LogHelper._FUNC_EXCEPTION_(), e);
        }
        message.setMsg(AppConstant.REPONSE_CODE.OK, AppConstant.REPONSE_MSG.SYS_REQUEST_OK_MSG);
        return message;
    }


}
