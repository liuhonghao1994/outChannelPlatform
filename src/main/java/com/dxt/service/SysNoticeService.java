package com.dxt.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dxt.common.AppConstant;
import com.dxt.common.LogHelper;
import com.dxt.common.MyPascalNameFilter;
import com.dxt.dao.NoticeBeanDao;
import com.dxt.message.ReponseMessage;
import com.dxt.message.AppRequestMessage;
import com.dxt.model.NoticeBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("SysNoticeService")
public class SysNoticeService extends IBaseBusiService {
    
    private static final Logger logger = LoggerFactory.getLogger(SysNoticeService.class);

    private static final String PARAM_VERSIONCODE = "versionCode";
    private static final String PARAM_NOTICELIST = "noticeList";

    @Autowired
    private NoticeBeanDao noticeBeanDao;
    
    @Override
    public ReponseMessage execute(String reqInfo, String source) {
        logger.debug(LogHelper._FUNC_START_());
        ReponseMessage message = new ReponseMessage();
        JSONObject jsonObject = JSON.parseObject(reqInfo);
        AppRequestMessage msg = getMsg();
        String versionCode = msg.getVersion();
        if (null != jsonObject.getString(PARAM_VERSIONCODE) && !"".equals(jsonObject.getString(PARAM_VERSIONCODE))) {
            versionCode = jsonObject.getString(PARAM_VERSIONCODE);
        }
        List<NoticeBean> list = noticeBeanDao.getEffectiveNoticeBeanList(msg.getAppType(), versionCode);
        JSONObject retObject = new JSONObject();
        retObject.put(PARAM_VERSIONCODE, versionCode);
        retObject.put(PARAM_NOTICELIST, JSONArray.parseArray(JSON.toJSONString(list, new MyPascalNameFilter())));

        message.setMsg(AppConstant.REPONSE_CODE.OK, AppConstant.REPONSE_MSG.SYS_REQUEST_OK_MSG, retObject);
        return message;
    }

}
