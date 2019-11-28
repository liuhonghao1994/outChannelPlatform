package com.dxt.model;

public class AppHomeNews {

    private Integer newsSort;
    private String newsImageUrl;
    private String newsArticleUrl;
    private String needOper;

    public AppHomeNews(AppHomeInfoBean appHomeInfoBean) {
        this.newsSort = Integer.valueOf(appHomeInfoBean.getSort());
        this.newsImageUrl = appHomeInfoBean.getImageUrl();
        this.newsArticleUrl = appHomeInfoBean.getLinkUrl();
        this.needOper = appHomeInfoBean.getExt1();
    }

    public Integer getNewsSort() {
        return newsSort;
    }

    public void setNewsSort(Integer newsSort) {
        this.newsSort = newsSort;
    }

    public String getNewsImageUrl() {
        return newsImageUrl;
    }

    public void setNewsImageUrl(String newsImageUrl) {
        this.newsImageUrl = newsImageUrl;
    }

    public String getNewsArticleUrl() {
        return newsArticleUrl;
    }

    public void setNewsArticleUrl(String newsArticleUrl) {
        this.newsArticleUrl = newsArticleUrl;
    }

    public String getNeedOper() {
        return needOper;
    }

    public void setNeedOper(String needOper) {
        this.needOper = needOper;
    }
}
