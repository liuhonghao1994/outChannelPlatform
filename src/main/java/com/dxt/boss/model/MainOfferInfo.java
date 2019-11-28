package com.dxt.boss.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.dxt.common.AppConstant;
import com.dxt.common.MyDateUtil;

public class MainOfferInfo {

    @JSONField(name = "NextOfferName")
    private String nextOfferName;
    @JSONField(name = "NextEffectiveDate")
    private String nextEffectiveDate;
    @JSONField(name = "NextBrandId")
    private String nextBrandId;
    @JSONField(name = "OfferDesc")
    private String offerDesc;
    @JSONField(name = "NextExpireDate")
    private String nextExpireDate;
    @JSONField(name = "EffectiveDate")
    private String effectiveDate;
    @JSONField(name = "NextPayMode")
    private String nextPayMode;
    @JSONField(name = "NextBrandName")
    private String nextBrandName;
    @JSONField(name = "OfferName")
    private String offerName;
    @JSONField(name = "InsOfferId")
    private String insOfferId;
    @JSONField(name = "NextOfferId")
    private String nextOfferId;
    @JSONField(name = "NextInsOfferId")
    private String nextInsOfferId;
    @JSONField(name = "BrandId")
    private String brandId;
    @JSONField(name = "NextOfferDesc")
    private String nextOfferDesc;
    @JSONField(name = "OfferId")
    private String offerId;
    @JSONField(name = "ExpireDate")
    private String expireDate;
    @JSONField(name = "PayMode")
    private String payMode;
    @JSONField(name = "NextDoneDate")
    private String nextDoneDate;
    @JSONField(name = "BrandName")
    private String brandName;
    @JSONField(name = "DoneDate")
    private String doneDate;

    public String getNextOfferName() {
        return nextOfferName;
    }

    public void setNextOfferName(String nextOfferName) {
        this.nextOfferName = nextOfferName;
    }

    public String getNextEffectiveDate() {
        return nextEffectiveDate;
    }

    public void setNextEffectiveDate(String nextEffectiveDate) {
        this.nextEffectiveDate = MyDateUtil.getDateStringFromOtherFormatDateStr(nextEffectiveDate,
                MyDateUtil.FormatPattern.YYYY_MM_DD_HH_MM_SS.getFormatPattern());
    }

    public String getNextBrandId() {
        return nextBrandId;
    }

    public void setNextBrandId(String nextBrandId) {
        if (null != nextBrandId && !"".equals(nextBrandId)) {
            this.nextBrandId = String.valueOf(Long.parseLong(nextBrandId) +
                    AppConstant.SYS_CONSTANT.PROD_INTERFERENCE_CODE);
        } else {
            this.nextBrandId = nextBrandId;
        }
    }

    public String getOfferDesc() {
        return offerDesc;
    }

    public void setOfferDesc(String offerDesc) {
        this.offerDesc = offerDesc;
    }

    public String getNextExpireDate() {
        return nextExpireDate;
    }

    public void setNextExpireDate(String nextExpireDate) {
        this.nextExpireDate = MyDateUtil.getDateStringFromOtherFormatDateStr(nextExpireDate,
                MyDateUtil.FormatPattern.YYYY_MM_DD_HH_MM_SS.getFormatPattern());
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = MyDateUtil.getDateStringFromOtherFormatDateStr(effectiveDate,
                MyDateUtil.FormatPattern.YYYY_MM_DD_HH_MM_SS.getFormatPattern());
    }

    public String getNextPayMode() {
        return nextPayMode;
    }

    public void setNextPayMode(String nextPayMode) {
        this.nextPayMode = nextPayMode;
    }

    public String getNextBrandName() {
        return nextBrandName;
    }

    public void setNextBrandName(String nextBrandName) {
        this.nextBrandName = nextBrandName;
    }

    public String getOfferName() {
        return offerName;
    }

    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }

    public String getInsOfferId() {
        return insOfferId;
    }

    public void setInsOfferId(String insOfferId) {
        if (null != insOfferId && !"".equals(insOfferId)) {
            this.insOfferId = String.valueOf(Long.parseLong(insOfferId) +
                    AppConstant.SYS_CONSTANT.PROD_INTERFERENCE_CODE);
        } else {
            this.insOfferId = insOfferId;
        }
    }

    public String getNextOfferId() {
        return nextOfferId;
    }

    public void setNextOfferId(String nextOfferId) {
        if (null != nextOfferId && !"".equals(nextOfferId)) {
            this.nextOfferId = String.valueOf(Long.parseLong(nextOfferId) +
                    AppConstant.SYS_CONSTANT.PROD_INTERFERENCE_CODE);
        } else {
            this.nextOfferId = nextOfferId;
        }
    }

    public String getNextInsOfferId() {
        return nextInsOfferId;
    }

    public void setNextInsOfferId(String nextInsOfferId) {
        if (null != nextInsOfferId && !"".equals(nextInsOfferId)) {
            this.nextInsOfferId = String.valueOf(Long.parseLong(nextInsOfferId) +
                    AppConstant.SYS_CONSTANT.PROD_INTERFERENCE_CODE);
        } else {
            this.nextInsOfferId = nextInsOfferId;
        }
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        if (null != brandId && !"".equals(brandId)) {
            this.brandId = String.valueOf(Long.parseLong(brandId) +
                    AppConstant.SYS_CONSTANT.PROD_INTERFERENCE_CODE);
        } else {
            this.brandId = brandId;
        }
    }

    public String getNextOfferDesc() {
        return nextOfferDesc;
    }

    public void setNextOfferDesc(String nextOfferDesc) {
        this.nextOfferDesc = nextOfferDesc;
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

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = MyDateUtil.getDateStringFromOtherFormatDateStr(expireDate,
                MyDateUtil.FormatPattern.YYYY_MM_DD_HH_MM_SS.getFormatPattern());
    }

    public String getPayMode() {
        return payMode;
    }

    public void setPayMode(String payMode) {
        this.payMode = payMode;
    }

    public String getNextDoneDate() {
        return nextDoneDate;
    }

    public void setNextDoneDate(String nextDoneDate) {
        this.nextDoneDate = MyDateUtil.getDateStringFromOtherFormatDateStr(nextDoneDate,
                MyDateUtil.FormatPattern.YYYY_MM_DD_HH_MM_SS.getFormatPattern());
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getDoneDate() {
        return doneDate;
    }

    public void setDoneDate(String doneDate) {
        this.doneDate = MyDateUtil.getDateStringFromOtherFormatDateStr(doneDate,
                MyDateUtil.FormatPattern.YYYY_MM_DD_HH_MM_SS.getFormatPattern());
    }
}