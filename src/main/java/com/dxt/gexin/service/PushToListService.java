package com.dxt.gexin.service;

import com.dxt.common.AppConstant;
import com.dxt.common.LogHelper;
import com.dxt.gexin.common.GexinConstant;
import com.dxt.service.CacheManager;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.style.Style0;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("PushToListService")
public class PushToListService {

    private Logger logger = LoggerFactory.getLogger(PushToListService.class);

    @Autowired
    private CacheManager cacheManager;

    public void dealPush(final HashMap<String, Object> map) throws Exception {
        String paramType = map.get(GexinConstant.REQUEST_PARAM.PARAM_TYPE).toString().toUpperCase();
        logger.debug(LogHelper._FUNC_() + paramType);

        IGtPush push = new IGtPush(
                cacheManager.getSysConfigByCode(paramType + AppConstant.SYS_CONFIG_KEY.KEY_PUSH_URL),
                cacheManager.getSysConfigByCode(paramType + AppConstant.SYS_CONFIG_KEY.KEY_PUSH_APPKEY),
                cacheManager.getSysConfigByCode(paramType + AppConstant.SYS_CONFIG_KEY.KEY_PUSH_MASTERSECRET));
        // 通知透传模板
        NotificationTemplate template = notificationTemplateDemo(map);
        ListMessage message = new ListMessage();
        message.setData(template);
        // 设置消息离线，并设置离线时间
        message.setOffline(true);
        // 离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(24 * 1000 * 3600);
        // 配置推送目标
        List<Target> targets = new ArrayList<>();
        List<Map<String, String>> clientList =
                (List<Map<String, String>>) map.get(GexinConstant.REQUEST_PARAM.CLIENT_IDS);
        for (Map<String, String> tmp : clientList) {
            Target target = new Target();
            target.setAppId(cacheManager.getSysConfigByCode(paramType + AppConstant.SYS_CONFIG_KEY.KEY_PUSH_APPID));
            target.setClientId(tmp.get(GexinConstant.REQUEST_PARAM.CLIENT_ID).toString());
            target.setAlias(tmp.get(GexinConstant.REQUEST_PARAM.ALIAS).toString());
            targets.add(target);
        }
        // taskId用于在推送时去查找对应的message
        String taskId = push.getContentId(message);
        IPushResult ret = push.pushMessageToList(taskId, targets);
        logger.debug(LogHelper._FUNC_() + ret.getResponse().toString());
    }

    public NotificationTemplate notificationTemplateDemo(final HashMap<String, Object> map) {
        String paramType = map.get(GexinConstant.REQUEST_PARAM.PARAM_TYPE).toString().toUpperCase();

        NotificationTemplate template = new NotificationTemplate();
        // 设置APPID与APPKEY
        template.setAppId(cacheManager.getSysConfigByCode(paramType + AppConstant.SYS_CONFIG_KEY.KEY_PUSH_APPID));
        template.setAppkey(cacheManager.getSysConfigByCode(paramType + AppConstant.SYS_CONFIG_KEY.KEY_PUSH_APPKEY));

        Style0 style = new Style0();
        // 设置通知栏标题与内容
        style.setTitle(map.get(GexinConstant.REQUEST_PARAM.TITLE).toString());
        style.setText(map.get(GexinConstant.REQUEST_PARAM.TEXT).toString());
        // 配置通知栏图标
        style.setLogo(map.get(GexinConstant.REQUEST_PARAM.LOGO).toString());
        // 配置通知栏网络图标
        style.setLogoUrl(map.get(GexinConstant.REQUEST_PARAM.LOGO_URL).toString());
        // 设置通知是否响铃，震动，或者可清除
        style.setRing(true);
        style.setVibrate(true);
        style.setClearable(true);
        template.setStyle(style);
        // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
        template.setTransmissionType(Integer.parseInt(map.get(GexinConstant.REQUEST_PARAM.TRANS_TYPE).toString()));
        template.setTransmissionContent(map.get(GexinConstant.REQUEST_PARAM.TRANS_CONTENT).toString());
        return template;
    }
}
