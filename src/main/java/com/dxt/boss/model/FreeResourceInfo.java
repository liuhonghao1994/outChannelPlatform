package com.dxt.boss.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.dxt.common.MyDateUtil;

public class FreeResourceInfo {

    private String last;
    @JSONField(name = "ExpireDate")
    private String expireDate;
    private String itemCode;
    private String used;
    private String type;
    @JSONField(name = "UsedRate")
    private String usedRate;
    private String total;
    private String unit;
    @JSONField(name = "ValidDate")
    private String validDate;
    @JSONField(name = "UserId")
    private String userId;
    private String name;
    private String resProp;

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = MyDateUtil.getDateStringFromOtherFormatDateStr(expireDate,
                MyDateUtil.FormatPattern.YYYY_MM_DD_HH_MM_SS.getFormatPattern());
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getUsed() {
        return used;
    }

    public void setUsed(String used) {
        this.used = used;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsedRate() {
        return usedRate;
    }

    public void setUsedRate(String usedRate) {
        this.usedRate = usedRate;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getValidDate() {
        return validDate;
    }

    public void setValidDate(String validDate) {
        this.validDate = MyDateUtil.getDateStringFromOtherFormatDateStr(validDate,
                MyDateUtil.FormatPattern.YYYY_MM_DD_HH_MM_SS.getFormatPattern());
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResProp() {
        return resProp;
    }

    public void setResProp(String resProp) {
        this.resProp = resProp;
    }
}
