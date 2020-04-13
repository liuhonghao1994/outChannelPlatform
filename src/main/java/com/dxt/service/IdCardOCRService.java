package com.dxt.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dxt.common.AppConstant;
import com.dxt.common.LogHelper;
import com.dxt.common.MyBusiException;
import com.dxt.dao.TxyOcrAnalyseCardLogDao;
import com.dxt.message.ReponseMessage;
import com.dxt.tengxun.IDCardOCR;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("IdCardOCRService")
public class IdCardOCRService extends IBaseBusiService {

    private static final Logger logger = LoggerFactory.getLogger(IdCardOCRService.class);

    @Autowired
    private IDCardOCR idCardOCR;
    @Autowired
    TxyOcrAnalyseCardLogDao txyOcrAnalyseCardLogDao;
    @Autowired
    CacheManager cacheManager;
    @Override
    public ReponseMessage execute(String reqInfo, String source) {
        logger.debug(LogHelper._FUNC_START_());
        ReponseMessage message = new ReponseMessage();
        JSONObject jsonObject = JSON.parseObject(reqInfo);
        JSONObject retObject = new JSONObject();
        try {
            checkParams(jsonObject,AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_IMAGEBASE64,
                    AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_CARDSIDE,
                    AppConstant.REQUEST_REPONSE_PARAM.PAEAM_IN_PLAT);
        } catch (MyBusiException e) {
            message.setMsg(AppConstant.REPONSE_CODE.SYS_ERROE, AppConstant.REPONSE_MSG.SYS_PARAM_ERROR_MSG);
            return message;
        }
        String platID = cacheManager.getSysConfigByCode(AppConstant.SYS_CONFIG_KEY.KEY_IDCARD_CHECK_PLAT_INFO);
        if(platID != null && AppConstant.IDCARD_CHECK_PLAT_INFO.VALUE_TXY.equals(platID)){
            //走腾讯OCR
            return idCardOCR.idCardOCR(jsonObject.getString(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_IMAGEBASE64),
                    jsonObject.getString(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_CARDSIDE),
                    jsonObject.getString(AppConstant.REQUEST_REPONSE_PARAM.PAEAM_IN_PLAT));
        }else{
            //不走校验
            retObject.put("resultCode","0");
            retObject.put("resultMsg","配置原因，未走检验程序");
            message.setMsg(AppConstant.REPONSE_CODE.OK,AppConstant.REPONSE_MSG.SYS_REQUEST_OK_MSG);
            return message;
        }

    }



}
