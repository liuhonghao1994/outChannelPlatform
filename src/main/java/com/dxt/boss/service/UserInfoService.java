package com.dxt.boss.service;

import com.dxt.boss.model.BaseCustInfo;
import com.dxt.boss.model.BaseUserInfo;
import com.dxt.model.AppUserBasicInfoBean;

import java.util.List;
import java.util.Map;

public interface UserInfoService {

    boolean verifyUserPassword(Map<String, Object> params);

    AppUserBasicInfoBean getUserBasicInfo(Map<String, Object> params);

    BaseUserInfo getBaseUserInfo(Map<String, Object> params);

    String getUserAvatarUrl(String phone);

    boolean setUserAvatarUrl(String phone, String url);

    boolean updateUserPassword(Map<String, Object> params);

    List<String> getCustomerPhoneNumber(String custCertCode);

    BaseCustInfo getBaseCustInfo(Map<String, Object> params);

}
