package com.dxt.boss.service;

import java.util.Map;

public interface CommonService {

    String getSysDateTime(Map<String, Object> params);

    String getRegionName(String regionId);

    String getOsStatusDesc(String osStatus);

    String getAccStatusDesc(String accStatus);

    String getStateDesc(String state);

}
