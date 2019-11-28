package com.dxt.boss.service;

import com.alibaba.fastjson.JSONObject;
import com.dxt.boss.common.BossConstant;
import com.dxt.boss.common.BossSocketClient;
import com.dxt.common.LogHelper;
import com.dxt.service.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("CommonService")
public class CommonServiceImpl implements CommonService {

    private Logger logger = LoggerFactory.getLogger(CommonServiceImpl.class);

    public static final String PARAM_IN_PHONE = "phone";

    public static final String PARAM_OUT_SERVICENUM = "ServiceNum";

    public static final String PARAM_UIP_CURRENTDATE = "CurrentDate";

    private static final String CONSTANT_STATUS_1 = "1";

    @Autowired
    private CacheManager cacheManager;

    @Override
    public String getSysDateTime(Map<String, Object> params) {
        Map<String, Object> reqParams = new HashMap<>();
        reqParams.put(BossConstant.UIP_COMMON_PARAM.STR_INTERFACE_NAME, BossConstant.UIP_API.OI_GET_SYS_DATE_TIME);
        Map<String, Object> busiParams = new HashMap<>();
        busiParams.put(PARAM_OUT_SERVICENUM, params.get(PARAM_IN_PHONE));
        reqParams.put(BossConstant.UIP_COMMON_PARAM.STR_BUSI_PARAMS, busiParams);
        String ret = BossSocketClient.execute(reqParams);
        try {
            JSONObject retJson = JSONObject.parseObject(ret);
            return retJson.getString(PARAM_UIP_CURRENTDATE);
        } catch (Exception e) {
            logger.error(LogHelper._FUNC_EXCEPTION_(), e);
        }
        return null;
    }

    @Override
    public String getRegionName(String regionId) {
        return cacheManager.getRegionInfoByCode(regionId);
    }

    @Override
    public String getOsStatusDesc(String osStatus) {
        String ret = "";
        if (CONSTANT_STATUS_1.equals(String.valueOf(osStatus.charAt(2)))) {
            ret += "停机保号";
        }
        if (CONSTANT_STATUS_1.equals(String.valueOf(osStatus.charAt(6)))) {
            ret += "营业停机";
        }
        if (CONSTANT_STATUS_1.equals(String.valueOf(osStatus.charAt(14)))) {
            ret += "呼叫限制";
        }
        if (CONSTANT_STATUS_1.equals(String.valueOf(osStatus.charAt(15)))) {
            ret += "账务停机";
        }
        if (CONSTANT_STATUS_1.equals(String.valueOf(osStatus.charAt(25)))) {
            ret += "管理停机";
        }
        if ("".equals(ret)) {
            ret = "正常";
        }
        return ret;
    }

    @Override
    public String getAccStatusDesc(String accStatus) {
        String ret = "未知";
        switch (accStatus) {
            case "0":
                ret = "历史";
                break;
            case "1":
                ret = "正常";
                break;
            case "2":
                ret = "预销";
                break;
            case "4":
                ret = "注销";
                break;
            default:
                break;
        }
        return ret;
    }

    @Override
    public String getStateDesc(String state) {
        String ret = "未知";
        switch (state) {
            case "1":
                ret = "在用";
                break;
            case "2":
                ret = "帐务预销";
                break;
            case "3":
                ret = "营业预销";
                break;
            case "4":
                ret = "预开户";
                break;
            case "5":
                ret = "营业销户";
                break;
            case "6":
                ret = "帐务销户";
                break;
            case "7":
                ret = "套卡销户";
                break;
            default:
                break;
        }
        return ret;
    }

}
