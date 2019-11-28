package com.dxt.service;

import com.dxt.common.AppConstant;
import com.dxt.common.LogHelper;
import com.dxt.message.ReponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SysUploadSuggestionInfoService")
public class SysUploadSuggestionInfoService extends IBaseBusiService {
    
    private static final Logger logger = LoggerFactory.getLogger(SysUploadSuggestionInfoService.class);

    @Autowired
    private SysCommonUploadInfoService sysCommonUploadInfoService;
    
    @Override
    public ReponseMessage execute(String reqInfo, String source) {
        logger.debug(LogHelper._FUNC_START_());
        return sysCommonUploadInfoService.execute(reqInfo, getMsg(), AppConstant.APP_CONSTANT.UPLOAD_TYPE_SUGGESTION);
    }


}
