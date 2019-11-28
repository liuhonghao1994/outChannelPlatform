package com.dxt.boss.common;

import com.alibaba.fastjson.JSONObject;
import com.dxt.common.LogHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class BossSocketClient {

    private static final Logger logger = LoggerFactory.getLogger(BossSocketClient.class);

    /**
     * @desc:通过socket调用UIP
     * @return
     * @throws Exception
     */
    public static String execute(Map<String, Object> busiParams) {
        String requestJson = buildRequestParam(busiParams);
        if (null == requestJson || "".equals(requestJson)) {
            return null;
        }
        logger.info(LogHelper._FUNC_() + "requestJson:" + requestJson);
        try {
            String retJsonData = SocketClient.send(requestJson);
            if (null == retJsonData || "".equals(retJsonData)) {
                logger.error(LogHelper._FUNC_() + LogHelper._LINE_() + "调用UIP接口失败！");
                return null;
            }
            logger.info(LogHelper._FUNC_() + "retJsonData:" + retJsonData);
            JSONObject ret = JSONObject.parseObject(retJsonData);
            if (null == ret || null == ret.get(BossConstant.UIP_COMMON_PARAM.STR_UIP_RET_RESPONSE)) {
                logger.error(LogHelper._FUNC_() + LogHelper._LINE_() + "调用UIP接口失败！");
                return null;
            }
            JSONObject response = (JSONObject) ret.get(BossConstant.UIP_COMMON_PARAM.STR_UIP_RET_RESPONSE);
            if (null == response || null == response.get(BossConstant.UIP_COMMON_PARAM.STR_UIP_RET_ERRORINFO)) {
                logger.error(LogHelper._FUNC_() + LogHelper._LINE_() + "调用UIP接口失败！");
                return null;
            }
            JSONObject errorInfo = (JSONObject) response.get(BossConstant.UIP_COMMON_PARAM.STR_UIP_RET_ERRORINFO);
            if (null == errorInfo) {
                logger.error(LogHelper._FUNC_() + LogHelper._LINE_() + "调用UIP接口失败！");
                return null;
            }
            // 查询业务里，查询无结果的code
            if (BossConstant.UIP_COMMON_PARAM.STR_UIP_RET_CODE_1045.equals(
                    errorInfo.get(BossConstant.UIP_COMMON_PARAM.STR_UIP_RET_CODE))) {
                return BossConstant.UIP_COMMON_PARAM.STR_UIP_RET_CODE_1045;
            // 业务办理，业务规则不通过的code
            } else if (BossConstant.UIP_COMMON_PARAM.STR_UIP_RET_CODE_3004.equals(
                    errorInfo.get(BossConstant.UIP_COMMON_PARAM.STR_UIP_RET_CODE))) {
                return BossConstant.UIP_COMMON_PARAM.STR_UIP_RET_CODE_3004;
            } else if (!BossConstant.UIP_COMMON_PARAM.STR_UIP_RET_CODE_0000.equals(
                    errorInfo.get(BossConstant.UIP_COMMON_PARAM.STR_UIP_RET_CODE))) {
                return null;
            }
            JSONObject retInfo = (JSONObject) response.get(BossConstant.UIP_COMMON_PARAM.STR_UIP_RET_RETINFO);
            if (null == retInfo) {
                logger.error(LogHelper._FUNC_() + LogHelper._LINE_() + "调用UIP接口失败！");
                return null;
            }
            return retInfo.toJSONString();
        } catch (Exception e) {
            logger.error(LogHelper._FUNC_EXCEPTION_(), e);
            return null;
        }
    }

    /**
     * 根据业务参数组建请求json字符串
     * @param busiParams
     * @return
     */
    private static String buildRequestParam(Map<String, Object> busiParams) {
        //接口名称
        if (null == busiParams.get(BossConstant.UIP_COMMON_PARAM.STR_INTERFACE_NAME)) {
            logger.error(LogHelper._FUNC_() + "没有提供UIP接口名称，无法进行参数组建！");
            return null;
        }
        if (null == busiParams.get(BossConstant.UIP_COMMON_PARAM.STR_BUSI_PARAMS)) {
            logger.error(LogHelper._FUNC_() + "没有提供业务参数，无法进行参数组建！");
            return null;
        }
        try {
            JSONObject pubInfo = new JSONObject();
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
            String transactionTime = format.format(date);
            pubInfo.put(BossConstant.UIP_COMMON_PARAM.STR_TRANSACTION_ID, getTransactionId(transactionTime));
            pubInfo.put(BossConstant.UIP_COMMON_PARAM.STR_TRANSACTION_TIME, transactionTime);
            pubInfo.put(BossConstant.UIP_COMMON_PARAM.STR_INTERFACE_ID, BossConstant.UIP_COMMON_PARAM.PARAM_INTERFACE_ID);
            pubInfo.put(BossConstant.UIP_COMMON_PARAM.STR_INTERFACE_TYPE, BossConstant.UIP_COMMON_PARAM.PARAM_INTERFACE_TYPE);
            pubInfo.put(BossConstant.UIP_COMMON_PARAM.STR_OP_ID, BossConstant.UIP_COMMON_PARAM.PARAM_OP_ID);
            pubInfo.put(BossConstant.UIP_COMMON_PARAM.STR_ORG_ID, BossConstant.UIP_COMMON_PARAM.PARAM_ORG_ID);
            pubInfo.put(BossConstant.UIP_COMMON_PARAM.STR_REGION_CODE, BossConstant.UIP_COMMON_PARAM.PARAM_REGION_CODE);
            pubInfo.put(BossConstant.UIP_COMMON_PARAM.STR_COUNTY_CODE, BossConstant.UIP_COMMON_PARAM.PARAM_COUNTY_CODE);
            pubInfo.put(BossConstant.UIP_COMMON_PARAM.STR_CLIENT_IP, BossConstant.UIP_COMMON_PARAM.PARAM_CLIENT_IP);
            JSONObject request = new JSONObject();
            request.put(BossConstant.UIP_COMMON_PARAM.STR_BUSI_CODE, busiParams.get(BossConstant.UIP_COMMON_PARAM.STR_INTERFACE_NAME));
            request.put(BossConstant.UIP_COMMON_PARAM.STR_BUSI_PARAMS, busiParams.get(BossConstant.UIP_COMMON_PARAM.STR_BUSI_PARAMS));
            JSONObject input = new JSONObject();
            input.put(BossConstant.UIP_COMMON_PARAM.STR_REQUEST, request);
            input.put(BossConstant.UIP_COMMON_PARAM.STR_PUB_INFO, pubInfo);
            return input.toJSONString();
        } catch (Exception e) {
            logger.error(LogHelper._FUNC_EXCEPTION_(), e);
            return null;
        }
    }

    private static String getTransactionId(String transactionTime) {
        return BossConstant.UIP_COMMON_PARAM.TRANSACTION_ID_PREFIX + transactionTime +
                IDHelper.getTimestampId(BossConstant.ID_CONSTANTS.TYPE_SHORT);
    }


}
