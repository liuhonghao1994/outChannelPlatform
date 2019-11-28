package com.dxt.controller;

import com.dxt.boss.common.BossConstant;
import com.dxt.boss.common.IDHelper;
import com.dxt.common.AppConstant;
import com.dxt.common.LogHelper;
import com.dxt.gexin.common.GexinConstant;
import com.dxt.gexin.service.PushToListService;
import com.dxt.gexin.service.PushToSingleService;
import com.dxt.jpush.JPushParam;
import com.dxt.jpush.JPushService;
import com.dxt.message.ReponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Scope("prototype")
@RequestMapping(value = "/api/v1", name = "testInterface")
public class TestController {

    @Autowired
    private PushToSingleService pushToSingleService;
    @Autowired
    private PushToListService pushToListService;


    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @RequestMapping(value = "/test/pushToSingle/{phone}")
    @ResponseBody
    public ResponseEntity<ReponseMessage> pushToSingle(@PathVariable String phone) {
        logger.debug(LogHelper._FUNC_START_());
        ReponseMessage message = new ReponseMessage();
        JPushParam jPushParam = new JPushParam();
        jPushParam.setTarget("all");
        jPushParam.setPlatform("all");
        jPushParam.setContent(phone + " 测试消息内容！");
        jPushParam.setTitle("这是一条消息！");
        jPushParam.setLinkUrl("https://www.baidu.com/");
        List<String> aliasList = new ArrayList<>();
        aliasList.add(phone);
        jPushParam.setAliasList(aliasList);
        JPushService.execute(jPushParam);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @RequestMapping(value = "/test/pushJPush")
    @ResponseBody
    public ResponseEntity<ReponseMessage> pushJPush() {
        logger.debug(LogHelper._FUNC_START_());
        ReponseMessage message = new ReponseMessage();
        JPushParam jPushParam = new JPushParam();
        jPushParam.setTarget("all");
        jPushParam.setPlatform("all");
        jPushParam.setContent("测试消息内容！啦啦啦啦啦啦啦，哈哈哈哈哈哈哈~~~");
        jPushParam.setTitle("这是一条消息！");
        jPushParam.setLinkUrl("https://www.baidu.com/");
        JPushService.execute(jPushParam);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @RequestMapping(value = "/test/testIDHelper")
    @ResponseBody
    public ResponseEntity<ReponseMessage> testIDHelper() {
        logger.debug(LogHelper._FUNC_START_());
        logger.debug(IDHelper.getTimestampId(BossConstant.ID_CONSTANTS.TYPE_LONG));
        ReponseMessage message = new ReponseMessage();
        return new ResponseEntity<>(message, HttpStatus.OK);
    }


}
