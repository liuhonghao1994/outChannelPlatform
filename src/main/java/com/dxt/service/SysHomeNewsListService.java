package com.dxt.service;

import com.dxt.common.AppConstant;
import com.dxt.common.LogHelper;
import com.dxt.message.ReponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SysHomeNewsListService")
public class SysHomeNewsListService extends IBaseBusiService {
    
    private static final Logger logger = LoggerFactory.getLogger(SysHomeNewsListService.class);

    @Autowired
    private SysCommonHomeListService sysCommonHomeListService;
    
    @Override
    public ReponseMessage execute(String reqInfo, String source) {
        logger.debug(LogHelper._FUNC_START_());
        return sysCommonHomeListService.execute(reqInfo, getMsg(), AppConstant.APP_CONSTANT.HOME_TYPE_NEWS,
                AppConstant.APP_CONSTANT.HOME_STR_LIST_NEWS);
    }

}
