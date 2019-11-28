package com.dxt.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dxt.boss.model.MainOfferInfo;
import com.dxt.boss.model.OfferInfo;
import com.dxt.common.*;
import com.dxt.message.ReponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("GetAppointedMainOfferInfoService")
public class GetAppointedMainOfferInfoService extends IBaseBusiService {
    
    private static final Logger logger = LoggerFactory.getLogger(GetAppointedMainOfferInfoService.class);

    private static final String PARAM_OFFERID = "offerId";

    @Autowired
    private CacheManager cacheManager;
    
    @Override
    public ReponseMessage execute(String reqInfo, String source) {
        logger.debug(LogHelper._FUNC_START_());
        ReponseMessage message = new ReponseMessage();
        JSONObject jsonObject = JSON.parseObject(reqInfo);
        try {
            checkParams(jsonObject, PARAM_OFFERID);
        } catch (MyBusiException e) {
            message.setMsg(AppConstant.REPONSE_CODE.BUSI_ERROR, AppConstant.REPONSE_MSG.SYS_PARAM_ERROR_MSG,
                    e.getMessage());
            return message;
        }

        OfferInfo offerInfo = cacheManager.getOfferInfoByOfferId(jsonObject.getString(PARAM_OFFERID));

        // 返回值
        JSONObject retObject = new JSONObject();
        retObject.put(AppConstant.REQUEST_REPONSE_PARAM.PARAM_OUT_OFFERINFO, offerInfo);
        message.setMsg(AppConstant.REPONSE_CODE.OK, AppConstant.REPONSE_MSG.SYS_REQUEST_OK_MSG, retObject);
        return message;
    }

}
