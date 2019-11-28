package com.dxt.model;

public class AppHomeAdvertisement {

    private Integer adSort;
    private String adImageUrl;
    private String adArticleUrl;

    public AppHomeAdvertisement(AppHomeInfoBean appHomeInfoBean) {
        this.adSort = Integer.valueOf(appHomeInfoBean.getSort());
        this.adImageUrl = appHomeInfoBean.getImageUrl();
        this.adArticleUrl = appHomeInfoBean.getLinkUrl();
    }

    public Integer getAdSort() {
        return adSort;
    }

    public void setAdSort(Integer adSort) {
        this.adSort = adSort;
    }

    public String getAdImageUrl() {
        return adImageUrl;
    }

    public void setAdImageUrl(String adImageUrl) {
        this.adImageUrl = adImageUrl;
    }

    public String getAdArticleUrl() {
        return adArticleUrl;
    }

    public void setAdArticleUrl(String adArticleUrl) {
        this.adArticleUrl = adArticleUrl;
    }

}
