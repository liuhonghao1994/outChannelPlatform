package com.dxt.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxt.common.AppConstant;
import com.dxt.common.LogHelper;
import com.dxt.message.ReponseMessage;

@Service("AppUserLogoutService")
public class AppUserLogoutService extends IBaseBusiService {
    
    private static final Logger logger = LoggerFactory.getLogger(AppUserLogoutService.class);

    @Autowired
    private SessionManager sessionManager;
    
    @Override
    public ReponseMessage execute(String reqInfo, String source) {
        logger.debug(LogHelper._FUNC_START_());
        ReponseMessage message = new ReponseMessage();
        
        String sessionId = sessionManager.getSessionIdFromHttpServletRequest(getRequest());
        if (null == sessionId) {
            logger.error(LogHelper._FUNC_() + "session is null!");
        } else {
            sessionManager.removeSession(sessionId);
        }
        message.setMsg(AppConstant.REPONSE_CODE.OK, AppConstant.REPONSE_MSG.SYS_LOGOUT_OK_MSG);
        return message;
    }

}
