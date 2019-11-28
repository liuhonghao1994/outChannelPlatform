package com.dxt.model;

public class AppPromotion {

    private Integer promSort;
    private String promImageUrl;
    private String articleUrl;
    private String title;
    private String summary;

    public AppPromotion(AppHomeInfoBean appHomeInfoBean) {
        this.promSort = Integer.valueOf(appHomeInfoBean.getSort());
        this.promImageUrl = appHomeInfoBean.getImageUrl();
        this.articleUrl = appHomeInfoBean.getLinkUrl();
        this.title = appHomeInfoBean.getName();
        this.summary = appHomeInfoBean.getSummary();
    }

    public Integer getPromSort() {
        return promSort;
    }

    public void setPromSort(Integer promSort) {
        this.promSort = promSort;
    }

    public String getPromImageUrl() {
        return promImageUrl;
    }

    public void setPromImageUrl(String promImageUrl) {
        this.promImageUrl = promImageUrl;
    }

    public String getArticleUrl() {
        return articleUrl;
    }

    public void setArticleUrl(String articleUrl) {
        this.articleUrl = articleUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
