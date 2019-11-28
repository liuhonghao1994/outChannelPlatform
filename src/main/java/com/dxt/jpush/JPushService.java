package com.dxt.jpush;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.*;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.Notification;
import com.alibaba.fastjson.JSONObject;
import com.dxt.common.AppConstant;
import com.dxt.common.LogHelper;
import com.dxt.service.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JPushService {

    private static final Logger logger = LoggerFactory.getLogger(JPushService.class);

    private static JPushService jPushService;

    @Autowired
    private CacheManager cacheManager;

    @PostConstruct
    public void init() {
        jPushService = this;
        jPushService.cacheManager = this.cacheManager;
    }

    public static void execute(JPushParam param) {
        List<TargetPlatform> targetPlatformList = getCacheKeyList(param);
        for (TargetPlatform targetPlatform : targetPlatformList) {
            try {
                String key = targetPlatform.getTarget() + "_" + targetPlatform.getPlatform() + "_";
                logger.debug(LogHelper._FUNC_() + "key:" + key);
                String masterSecret = jPushService.cacheManager.getSysConfigByCode(key +
                        AppConstant.SYS_CONFIG_KEY.KEY_PUSH_MASTERSECRET);
                String appKey = jPushService.cacheManager.getSysConfigByCode(key +
                        AppConstant.SYS_CONFIG_KEY.KEY_PUSH_APPKEY);
                if (null == masterSecret || null == appKey) {
                    continue;
                }
                PushPayload pushPayload = buildPushObject(targetPlatform, param);
                dealPush(masterSecret, appKey, pushPayload);
            } catch (Exception e) {
                logger.error(LogHelper._FUNC_EXCEPTION_(), e);
                continue;
            }
        }
    }

    private static void dealPush(String masterSecret, String appKey, PushPayload pushPayload) {
        JPushClient jpushClient = new JPushClient(masterSecret, appKey,
                null, ClientConfig.getInstance());
        try {
            PushResult result = jpushClient.sendPush(pushPayload);
            logger.info("Got result - " + result);
        } catch (APIConnectionException e) {
            // Connection error, should retry later
            logger.error("Connection error, should retry later", e);
        } catch (APIRequestException e) {
            // Should review the error, and fix the request
            logger.error("Should review the error, and fix the request", e);
            logger.info("HTTP Status: " + e.getStatus());
            logger.info("Error Code: " + e.getErrorCode());
            logger.info("Error Message: " + e.getErrorMessage());
        }
    }

    /**
     * 通过参数获得缓存preKey的列表
     * @param param
     * @return
     */
    private static List<TargetPlatform> getCacheKeyList(JPushParam param) {
        List<TargetPlatform> list = new ArrayList<>();
        String target = param.getTarget().toUpperCase();
        String platform = param.getPlatform().toUpperCase();
        // 如果是all的情况
        if (target.equals(AppConstant.PUSH_CONSTANT.PARAM_ALL)) {
            for (AppConstant.REQUEST_SOURCE source : AppConstant.REQUEST_SOURCE.values()) {
                // 如果是all的情况
                if (platform.equals(AppConstant.PUSH_CONSTANT.PARAM_ALL)) {
                    for (AppConstant.APP_TYPE type : AppConstant.APP_TYPE.values()) {
                        TargetPlatform targetPlatform = new TargetPlatform(source.getValue().toUpperCase(),
                                type.getValue().toUpperCase());
                        list.add(targetPlatform);
                    }
                } else {
                    TargetPlatform targetPlatform = new TargetPlatform(source.getValue().toUpperCase(), platform);
                    list.add(targetPlatform);
                }
            }
        } else {
            // 如果是all的情况
            if (platform.equals(AppConstant.PUSH_CONSTANT.PARAM_ALL)) {
                for (AppConstant.APP_TYPE type : AppConstant.APP_TYPE.values()) {
                    TargetPlatform targetPlatform = new TargetPlatform(target, type.getValue().toUpperCase());
                    list.add(targetPlatform);
                }
            } else {
                TargetPlatform targetPlatform = new TargetPlatform(target, platform);
                list.add(targetPlatform);
            }
        }
        return  list;
    }

    private static PushPayload buildPushObject(TargetPlatform targetPlatform, JPushParam param) {
        logger.debug(LogHelper._FUNC_() + "targetPlatform:" + targetPlatform.getPlatform());
        PushPayload.Builder builder = PushPayload.newBuilder();

        if (targetPlatform.getPlatform().equals(AppConstant.APP_TYPE.ANDROID.getValue().toUpperCase())) {
            builder.setPlatform(Platform.android());
            if (null == param.getTitle() || "".equals(param.getTitle())) {
                builder.setMessage(Message.newBuilder().setMsgContent(param.getContent()).build());
//                builder.setNotification(Notification.alert(param.getContent()));
            } else {
//                Map<String,String> extras = new HashMap<>();
//                extras.put("title", param.getTitle());
//                extras.put("content", param.getContent());
//                extras.put("linkUrl", param.getLinkUrl());
//                builder.setNotification(Notification.android(param.getContent(), param.getTitle(), extras));

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("linkUrl", param.getLinkUrl());
                builder.setMessage(Message.newBuilder().setMsgContent(param.getContent())
                        .setTitle(param.getTitle()).addExtra("linkUrl", param.getLinkUrl())
                        .build());
            }
        } else if (targetPlatform.getPlatform().equals(AppConstant.APP_TYPE.IOS.getValue().toUpperCase())) {
            builder.setPlatform(Platform.ios());
            if (null == param.getTitle() || "".equals(param.getTitle())) {
                builder.setMessage(Message.newBuilder().setMsgContent(param.getContent()).build());
//                builder.setNotification(Notification.alert(param.getContent()));
            } else {
//                Map<String,String> extras = new HashMap<>();
//                extras.put("title", param.getTitle());
//                extras.put("content", param.getContent());
//                extras.put("linkUrl", param.getLinkUrl());
//                builder.setNotification(Notification.android(param.getContent(), param.getTitle(), extras));

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("linkUrl", param.getLinkUrl());
                builder.setMessage(Message.newBuilder().setMsgContent(param.getContent())
                        .setTitle(param.getTitle()).addExtra("linkUrl", param.getLinkUrl())
                        .build());
            }
        } else {
            // 此处为容错，也可以直接Exception
            builder.setPlatform(Platform.android_ios());
            builder.setNotification(Notification.alert(param.getContent()));
        }

        if (null != param.getTagList() && 0 != param.getTagList().size()
            && null != param.getAliasList() && 0 != param.getAliasList().size()) {
            builder.setAudience(Audience.newBuilder()
                    .addAudienceTarget(AudienceTarget.tag(param.getTagList()))
                    .addAudienceTarget(AudienceTarget.alias(param.getAliasList()))
                    .build());
        } else if (null != param.getTagList() && 0 != param.getTagList().size()) {
            builder.setAudience(Audience.tag(param.getTagList()));
        } else if (null != param.getAliasList() && 0 != param.getAliasList().size()) {
            builder.setAudience(Audience.alias(param.getAliasList()));
        } else {
            builder.setAudience(Audience.all());
        }
        return builder.build();
    }


}
