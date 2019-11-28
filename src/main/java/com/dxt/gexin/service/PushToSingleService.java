package com.dxt.gexin.service;

import com.dxt.common.AppConstant;
import com.dxt.common.LogHelper;
import com.dxt.gexin.common.GexinConstant;
import com.dxt.service.CacheManager;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.style.Style0;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service("PushToSingleService")
public class PushToSingleService {

    private Logger logger = LoggerFactory.getLogger(PushToSingleService.class);

    @Autowired
    private CacheManager cacheManager;

    /**
     * 给指定用户推送消息
     * @param map
     * @throws Exception
     */
    public void dealPush(final HashMap<String, Object> map) throws Exception {
        String paramType = map.get(GexinConstant.REQUEST_PARAM.PARAM_TYPE).toString().toUpperCase();
        logger.debug(LogHelper._FUNC_() + paramType);
        IGtPush push = new IGtPush(
                cacheManager.getSysConfigByCode(paramType + AppConstant.SYS_CONFIG_KEY.KEY_PUSH_URL),
                cacheManager.getSysConfigByCode(paramType + AppConstant.SYS_CONFIG_KEY.KEY_PUSH_APPKEY),
                cacheManager.getSysConfigByCode(paramType + AppConstant.SYS_CONFIG_KEY.KEY_PUSH_MASTERSECRET));
        LinkTemplate template = linkTemplateDemo(map);
        SingleMessage message = new SingleMessage();
        message.setOffline(true);
        // 离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(24 * 3600 * 1000);
        message.setData(template);
        // 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
        message.setPushNetWorkType(0);
        Target target = new Target();
        target.setAppId(cacheManager.getSysConfigByCode(paramType + AppConstant.SYS_CONFIG_KEY.KEY_PUSH_APPID));
        target.setClientId(map.get(GexinConstant.REQUEST_PARAM.CLIENT_ID).toString());
        target.setAlias(map.get(GexinConstant.REQUEST_PARAM.ALIAS).toString());
        IPushResult ret;
        try {
            ret = push.pushMessageToSingle(message, target);
        } catch (RequestException e) {
            e.printStackTrace();
            ret = push.pushMessageToSingle(message, target, e.getRequestId());
        }
        if (ret != null) {
            logger.debug(LogHelper._FUNC_() + ret.getResponse().toString());
        } else {
            logger.error(LogHelper._FUNC_EXCEPTION_() + "个推服务器响应异常！");
        }
    }

    private LinkTemplate linkTemplateDemo(final HashMap<String, Object> map) {
        String paramType = map.get(GexinConstant.REQUEST_PARAM.PARAM_TYPE).toString().toUpperCase();
        LinkTemplate template = new LinkTemplate();
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
        // 设置打开的网址地址
        template.setUrl(map.get(GexinConstant.REQUEST_PARAM.LINK_URL).toString());
        return template;
    }
}
