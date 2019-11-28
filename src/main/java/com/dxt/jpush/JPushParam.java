package com.dxt.jpush;

import java.util.List;

public class JPushParam {

    private String target;
    private String platform;
    private List<String> aliasList;
    private List<String> tagList;
    private String title;
    private String content;
    private String linkUrl;

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public List<String> getAliasList() {
        return aliasList;
    }

    public void setAliasList(List<String> aliasList) {
        this.aliasList = aliasList;
    }

    public List<String> getTagList() {
        return tagList;
    }

    public void setTagList(List<String> tagList) {
        this.tagList = tagList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }
}
