package com.dxt.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dxt.boss.model.OfferInfo;
import com.dxt.boss.service.PhoneInfoService;
import com.dxt.common.AppConstant;
import com.dxt.common.LogHelper;
import com.dxt.common.MyBusiException;
import com.dxt.message.ReponseMessage;
import com.dxt.model.AppSessionInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("GetUserAvailVasOfferService")
public class GetUserAvailVasOfferService extends IBaseBusiService {
    
    private static final Logger logger = LoggerFactory.getLogger(GetUserAvailVasOfferService.class);

    private static final String PARAM_QUERYTYPE = "queryType";
    private static final String PARAM_NETWORK = "network";

    @Autowired
    private PhoneInfoService phoneInfoService;
    @Autowired
    private CacheManager cacheManager;
    @Autowired
    private SessionManager sessionManager;
    
    @Override
    public ReponseMessage execute(String reqInfo, String source) {
        logger.debug(LogHelper._FUNC_START_());
        ReponseMessage message = new ReponseMessage();
        JSONObject jsonObject = JSON.parseObject(reqInfo);
        try {
            if (AppConstant.REQUEST_SOURCE.APP.getValue().equals(source)) {
                checkParams(jsonObject, PARAM_QUERYTYPE);
            } else {
                checkParams(jsonObject, PARAM_QUERYTYPE,
                        AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_PHONE);
            }
        } catch (MyBusiException e) {
            message.setMsg(AppConstant.REPONSE_CODE.BUSI_ERROR, AppConstant.REPONSE_MSG.SYS_PARAM_ERROR_MSG,
                    e.getMessage());
            return message;
        }

        // 判断是否有session，如果有，获取对应网络的流量包，否则获取全部
        AppSessionInfo appSessionInfo = null;
        try {
            appSessionInfo = sessionManager.getSessionInfoFromHttpServletRequest(getRequest());
        } catch (MyBusiException e) {
        }
        String phone = null;
        String network = null;
        if (null == appSessionInfo) {
            phone = jsonObject.getString(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_PHONE);
        } else {
            phone = appSessionInfo.getPhone();
            network = appSessionInfo.getNetwork();
        }
        Map<String, Object> map = new HashMap<>(1);
        map.put(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_PHONE, phone);

        List<OfferInfo> list = phoneInfoService.getUserAvailVasOffer(map);
        if (null == list) {
            logger.error(LogHelper._FUNC_() + "OfferInfoList is null!");
            message.setMsg(AppConstant.REPONSE_CODE.SYS_ERROE, AppConstant.REPONSE_MSG.SYS_ERROR_MSG);
            return message;
        }
        if (null == network) {
            network = phoneInfoService.getCarrierOperator(map);
        }
        if (null == network || "".equals(network)) {
            logger.error(LogHelper._FUNC_() + "network is null!");
            message.setMsg(AppConstant.REPONSE_CODE.BUSI_WARNING,
                    "对不起，暂时没有可供您订购的增值产品！");
            return message;
        }
        // 此处进行配置过滤
        Map<String, String> allowedMap = new HashMap<>(3);
        allowedMap.put(OfferInfo.OFFERTYPE, OfferInfo.OFFERTYPE_OFFER_VAS_OTHER);
        allowedMap.put(OfferInfo.VASTYPE, jsonObject.getString(PARAM_QUERYTYPE));
        allowedMap.put(PARAM_NETWORK, network);
        List<OfferInfo> allowedList = cacheManager.getVasOfferInfoByMap(allowedMap);
        if (null == allowedList || 0 == allowedList.size()) {
            logger.error(LogHelper._FUNC_() + "增值产品列表未做配置！");
            message.setMsg(AppConstant.REPONSE_CODE.BUSI_WARNING,
                    "对不起，暂时没有可供您订购的增值产品！");
            return message;
        }
        // 对list进行过滤，排除主套餐
        List<String> vasOfferList = new ArrayList<>();
        for (OfferInfo offerInfo : list) {
            vasOfferList.add(offerInfo.getOfferId());
        }
        JSONArray jsonArray = new JSONArray();
        for (OfferInfo offerInfo : allowedList) {
            if (vasOfferList.contains(offerInfo.getOfferId())) {
                jsonArray.add(offerInfo);
            }
        }
        if (0 == jsonArray.size()) {
            message.setMsg(AppConstant.REPONSE_CODE.BUSI_WARNING,
                    "对不起，暂时没有可供您订购的增值产品！");
            return message;
        }
        // 返回值
        JSONObject retObject = new JSONObject();
        retObject.put(AppConstant.REQUEST_REPONSE_PARAM.PARAM_OUT_AVAILVASOFFERLIST, jsonArray);
        message.setMsg(AppConstant.REPONSE_CODE.OK, AppConstant.REPONSE_MSG.SYS_REQUEST_OK_MSG, retObject);
        return message;

    }

}
