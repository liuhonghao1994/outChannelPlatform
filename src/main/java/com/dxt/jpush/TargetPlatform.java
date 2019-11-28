package com.dxt.jpush;

public class TargetPlatform {

    private String target;
    private String platform;

    public TargetPlatform(String target, String platform) {
        this.target = target;
        this.platform = platform;
    }

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
}
