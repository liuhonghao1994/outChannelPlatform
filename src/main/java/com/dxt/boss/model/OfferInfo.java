package com.dxt.boss.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.dxt.common.AppConstant;
import com.dxt.common.MyDateUtil;
import com.dxt.common.MyObjectUtil;

public class OfferInfo {

    public static final String OFFERTYPE = "offerType";
    public static final String OFFERTYPE_OFFER_VAS_OTHER = "OFFER_VAS_OTHER";
    public static final String OFFERTYPE_OFFER_PLAN_GSM = "OFFER_PLAN_GSM";
    public static final String VASTYPE = "vasType";
    public static final String VASTYPE_All = "0";
    public static final String VASTYPE_GPRS_PACKAGE = "1";
    public static final String VASTYPE_SERVER = "2";
    public static final String VASTYPE_OTHER = "3";
    public static final String VASTYPE_EXCLUDE_GPRS = "4";

    @JSONField(name = "ExpireDate")
    private String expireDate;
    @JSONField(name = "OfferId")
    private String offerId;
    @JSONField(name = "ProductSpec")
    private String productSpec;
    @JSONField(name = "OfferDesc")
    private String offerDesc;
    @JSONField(name = "EffectiveDate")
    private String effectiveDate;
    @JSONField(name = "OfferName")
    private String offerName;
    @JSONField(name = "OfferType")
    private String offerType;

    private String vasType;
    private String offerPrice;

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = MyDateUtil.getDateStringFromOtherFormatDateStr(expireDate,
                MyDateUtil.FormatPattern.YYYY_MM_DD_HH_MM_SS.getFormatPattern());
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        if (null != offerId && !"".equals(offerId)) {
            this.offerId = String.valueOf(Long.parseLong(offerId) +
                    AppConstant.SYS_CONSTANT.PROD_INTERFERENCE_CODE);
        } else {
            this.offerId = offerId;
        }
    }

    public String getProductSpec() {
        return productSpec;
    }

    public void setProductSpec(String productSpec) {
        this.productSpec = productSpec;
    }

    public String getOfferDesc() {
        return offerDesc;
    }

    public void setOfferDesc(String offerDesc) {
        this.offerDesc = offerDesc;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = MyDateUtil.getDateStringFromOtherFormatDateStr(effectiveDate,
                MyDateUtil.FormatPattern.YYYY_MM_DD_HH_MM_SS.getFormatPattern());
    }

    public String getOfferName() {
        return offerName;
    }

    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }

    public String getOfferType() {
        return offerType;
    }

    public void setOfferType(String offerType) {
        this.offerType = offerType;
    }

    public String getVasType() {
        return vasType;
    }

    public void setVasType(String vasType) {
        this.vasType = vasType;
    }

    public String getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(String offerPrice) {
        this.offerPrice = offerPrice;
    }

    @Override
    public String toString() {
        return MyObjectUtil.show(this);
    }
}
