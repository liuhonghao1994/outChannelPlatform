package com.dxt.boss.service;

import com.dxt.boss.model.*;

import java.util.List;
import java.util.Map;

public interface PhoneInfoService {

    BalanceInfo getAccountBalance(Map<String, Object> params);

    RealTimeBillInfo getUserRealTimeBill(Map<String, Object> params);

    UserBillDetail getUserUserBillDetail(Map<String, Object> params);

    CallCDR getCallCDR(Map<String, Object> params);

    NetCDR getNetCDR(Map<String, Object> params);




    SmsMmsMainCDR getSmsAndMMSCDR(Map<String, Object> params);

    List<PayAndWriteoffRec> getPayAndWriteoffRec(Map<String, Object> params);

    List<BusiRecInfo> getUserBusinessRecord(Map<String, Object> params);

    List<FreeResourceInfo> getGetUserFreeRes(Map<String, Object> params);

    List<FreeResourceInfo> getGetUserFreeResCom(Map<String, Object> params);

    UsedResInfo getUserUsedRes(Map<String, Object> params);

    List<OfferInfo> getUserSpecOffer(Map<String, Object> params);

    MainOfferInfo getMainOfferInfo(Map<String, Object> params);

    List<OfferInfo> getUserAvailMainOffer(Map<String, Object> params);

    List<OfferInfo> getUserAvailVasOffer(Map<String, Object> params);

    String getCarrierOperator(Map<String, Object> params);

    String getUserHisOwedBill(Map<String, Object> params);

}
